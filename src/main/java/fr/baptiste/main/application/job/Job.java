package fr.baptiste.main.application.job;

import fr.baptiste.main.application.port.ReaderStreamingPort;
import fr.baptiste.main.domain.StationInformation;
import fr.baptiste.main.domain.StationStatus;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.round;

/**
 * This class has the responsibility to receive velib informations and display the closed and the number of available bikes
 */
public class Job implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Job.class);
    private static final double SPLIO_LON = 2.3353503;
    private static final double SPLIO_LAT = 48.8709807;
    public static final int MAXIMUM_STATION_DISPLAYED = 10;

    private final ReaderStreamingPort clientStreamReader;

    public Job(ReaderStreamingPort clientStreamReader) {
        this.clientStreamReader = clientStreamReader;
    }

    public void execute() {
        //on lis les informations du site
        final JavaRDD<StationInformation> stationInformationJavaRDD = clientStreamReader.getStationInformation();
        final JavaRDD<StationStatus> stationStatusJavaRDD = clientStreamReader.getStationStatus();

        //on trie les stations par clé/valeur (clé correspond aux informations des stations proches
        final JavaPairRDD<StationInformation.Data.Station, StationStatus.Data.Station> data = stationInformationJavaRDD
                .flatMap(stationInformation -> stationInformation.getData().getStations().iterator())
                .zip(stationStatusJavaRDD.flatMap(stationStatus -> stationStatus.getData().getStations().iterator()));

        final Comparator<StationInformation.Data.Station> comp = (Comparator<StationInformation.Data.Station> & Serializable) (o1, o2) -> {
            return Double.compare(
                    calculFlightDistanceBetweenTwoPoint(SPLIO_LAT, o1.getLat(), SPLIO_LON, o1.getLon()),
                    calculFlightDistanceBetweenTwoPoint(SPLIO_LAT, o2.getLat(), SPLIO_LON, o2.getLon()));
        };
        //on récupère les stations proche en étant de marche et qui contiennent encore des vélos à dispositions
        final List<Tuple2<StationInformation.Data.Station, StationStatus.Data.Station>> result = data
                .filter(stationStationTuple2 -> stationStationTuple2._2.getIsInstalled() == 1 && stationStationTuple2._2.getIsRenting() == 1) //on filtre sur les stations ne fonctionnant pas
                .filter(stationStationTuple2 -> stationStationTuple2._2.getNumBikesAvailable() > 0) //on filtre sur les stations ayant des vélos à disposition
                .sortByKey(comp) //on trie les stations par distance
                .take(MAXIMUM_STATION_DISPLAYED); //on reécupère uniquement les MAXIMUM_STATION_DISPLAYED plus proche stations à afficher

        result.forEach(stationStationTuple2 -> {
            System.out.printf(
                    "Dans la station %s se situant à %f kilomètres il y a %d vélos disponibles%n",
                    stationStationTuple2._1.getName(),
                    calculFlightDistanceBetweenTwoPoint(SPLIO_LAT, stationStationTuple2._1.getLat(), SPLIO_LON, stationStationTuple2._1.getLon()),
                    stationStationTuple2._2.getNumBikesAvailable()
            );
        });
    }

    public double calculFlightDistanceBetweenTwoPoint(double latPointA, double latPointB, double lonPointA, double lonPointB) {
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(latPointA - latPointB);
        double lonDistance = Math.toRadians(lonPointA - lonPointB);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latPointB)) * Math.cos(Math.toRadians(latPointA))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = round(R * c * 100.0) / 100.0;
        if (distance < 0) {
            return -distance;
        }
        return distance;
    }
}
