package fr.baptiste.main;

import fr.baptiste.main.application.job.Job;
import fr.baptiste.main.event.deserialization.GbfsDeserializer;
import fr.baptiste.main.event.deserialization.StationInformationDeserializer;
import fr.baptiste.main.event.deserialization.StationStatusDeserializer;
import fr.baptiste.main.event.deserialization.SystemInformationDeserializer;
import fr.baptiste.main.event.driving.KafkaReaderStreamingAdapter;
import fr.baptiste.main.utility.spark.SparkConfiguration;
import fr.baptiste.main.utility.spark.SparkHelper;
import fr.baptiste.main.utility.url.DataFromUrl;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        //Spark
        final SparkConfiguration sparkConfiguration = new SparkConfiguration("velib", "local[*]", Collections.emptyMap());
        final Duration seconds = Durations.seconds(1L);

        //instanciation of Spark objects
        final SparkHelper sparkHelper = new SparkHelper(sparkConfiguration, seconds);

        //instanciation of Kafka objects
        KafkaReaderStreamingAdapter kafkaReaderStreamingAdapter = new KafkaReaderStreamingAdapter(sparkHelper,
                new DataFromUrl(),
                new SystemInformationDeserializer(),
                new GbfsDeserializer(),
                new StationInformationDeserializer(),
                new StationStatusDeserializer()
        );

        new Job(kafkaReaderStreamingAdapter).execute();
    }
}
