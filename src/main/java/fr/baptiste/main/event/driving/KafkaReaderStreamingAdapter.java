package fr.baptiste.main.event.driving;

import fr.baptiste.main.application.port.ReaderStreamingPort;
import fr.baptiste.main.domain.Gbfs;
import fr.baptiste.main.domain.StationInformation;
import fr.baptiste.main.domain.StationStatus;
import fr.baptiste.main.domain.SystemInformation;
import fr.baptiste.main.event.deserialization.GbfsDeserializer;
import fr.baptiste.main.event.deserialization.StationInformationDeserializer;
import fr.baptiste.main.event.deserialization.StationStatusDeserializer;
import fr.baptiste.main.event.deserialization.SystemInformationDeserializer;
import fr.baptiste.main.utility.spark.SparkHelper;
import fr.baptiste.main.utility.url.DataFromUrl;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class has the reponsibility to provide JavaDStream who will consume Data from a Url.
 * This class is a driving adaptor in an Hexagonal architecture.
 */
public class KafkaReaderStreamingAdapter implements ReaderStreamingPort {
    public static final String URL_SYSTEM_INFORMATION = "https://velib-metropole-opendata.smoove.pro/opendata/Velib_Metropole/system_information.json";
    public static final String URL_GBFS = "https://velib-metropole-opendata.smoove.pro/opendata/Velib_Metropole/gbfs.json";
    public static final String URL_STATION_STATUS = "https://velib-metropole-opendata.smoove.pro/opendata/Velib_Metropole/station_status.json";
    public static final String URL_STATION_INFORMATION = "https://velib-metropole-opendata.smoove.pro/opendata/Velib_Metropole/station_information.json";
    private final SparkHelper sparkHelper;
    private final DataFromUrl dataFromUrl;

    private final SystemInformationDeserializer systemInformationDeserializer;
    private final GbfsDeserializer gbfsDeserializer;
    private final StationInformationDeserializer stationInformationDeserializer;
    private final StationStatusDeserializer stationStatusDeserializer;

    public KafkaReaderStreamingAdapter(SparkHelper sparkHelper,
                                       DataFromUrl dataFromUrl,
                                       SystemInformationDeserializer systemInformationDeserializer,
                                       GbfsDeserializer gbfsDeserializer,
                                       StationInformationDeserializer stationInformationDeserializer,
                                       StationStatusDeserializer stationStatusDeserializer) {
        this.sparkHelper = sparkHelper;
        this.dataFromUrl = dataFromUrl;
        this.systemInformationDeserializer = systemInformationDeserializer;
        this.gbfsDeserializer = gbfsDeserializer;
        this.stationInformationDeserializer = stationInformationDeserializer;
        this.stationStatusDeserializer = stationStatusDeserializer;
    }

    @Override
    public JavaRDD<SystemInformation> getSystemInformation() {
        List<String> data = dataFromUrl.get(URL_SYSTEM_INFORMATION);
        List<SystemInformation> systemInformations = data.stream().map(s -> systemInformationDeserializer.deserialize(null, s.getBytes())).collect(Collectors.toList());

        return new JavaSparkContext(sparkHelper.getSparkSession().sparkContext()).parallelize(systemInformations);
    }

    @Override
    public JavaRDD<Gbfs> getGbfs() {
        List<String> data = dataFromUrl.get(URL_GBFS);
        List<Gbfs> gbfs = data.stream().map(s -> gbfsDeserializer.deserialize(null, s.getBytes())).collect(Collectors.toList());

        return new JavaSparkContext(sparkHelper.getSparkSession().sparkContext()).parallelize(gbfs);
    }

    @Override
    public JavaRDD<StationStatus> getStationStatus() {
        List<String> data = dataFromUrl.get(URL_STATION_STATUS);
        List<StationStatus> stationStatuses = data.stream().map(s -> stationStatusDeserializer.deserialize(null, s.getBytes())).collect(Collectors.toList());

        return new JavaSparkContext(sparkHelper.getSparkSession().sparkContext()).parallelize(stationStatuses);
    }

    @Override
    public JavaRDD<StationInformation> getStationInformation() {
        List<String> data = dataFromUrl.get(URL_STATION_INFORMATION);
        List<StationInformation> stationInformations = data.stream().map(s -> stationInformationDeserializer.deserialize(null, s.getBytes())).collect(Collectors.toList());

        return new JavaSparkContext(sparkHelper.getSparkSession().sparkContext()).parallelize(stationInformations);
    }
}
