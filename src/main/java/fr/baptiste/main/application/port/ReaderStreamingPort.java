package fr.baptiste.main.application.port;

import fr.baptiste.main.domain.Gbfs;
import fr.baptiste.main.domain.StationInformation;
import fr.baptiste.main.domain.StationStatus;
import fr.baptiste.main.domain.SystemInformation;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.rdd.RDD;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;

import java.io.Serializable;

/**
 * This class has the responsiblity to provide a JavaInputDStream, which is the spark object to read data in streaming.
 * Respect the Port design pattern in an Hexagonal architecture.
 */
public interface ReaderStreamingPort extends Serializable {
    /**
     * This method provide a JavaInputDStream
     * @return JavaInputDStream
     */
    JavaRDD<SystemInformation> getSystemInformation();
    JavaRDD<Gbfs> getGbfs();
    JavaRDD<StationStatus> getStationStatus();
    JavaRDD<StationInformation> getStationInformation();
}
