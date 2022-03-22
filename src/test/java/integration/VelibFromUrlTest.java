package integration;


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
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Collections;

public class VelibFromUrlTest implements Serializable {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void itShouldFindTheClosestStation() throws IOException {
        //GIVEN
        final DataFromUrl dataFromUrl = Mockito.mock(DataFromUrl.class, Mockito.withSettings().serializable());
        final SparkConfiguration sparkConfiguration = new SparkConfiguration("velib", "local[*]", Collections.emptyMap());
        final Duration seconds = Durations.seconds(1L);

        //instanciation of Spark objects
        final SparkHelper sparkHelper = new SparkHelper(sparkConfiguration, seconds);

        //instanciation of Kafka objects
        KafkaReaderStreamingAdapter kafkaReaderStreamingAdapter = new KafkaReaderStreamingAdapter(sparkHelper,
                dataFromUrl,
                new SystemInformationDeserializer(),
                new GbfsDeserializer(),
                new StationInformationDeserializer(),
                new StationStatusDeserializer()
        );

        //WHEN
        Mockito.when(dataFromUrl.get(KafkaReaderStreamingAdapter.URL_STATION_INFORMATION)).thenReturn(Files.readAllLines(new File("src/test/resources/station_information.json").toPath(), Charset.defaultCharset()));
        Mockito.when(dataFromUrl.get(KafkaReaderStreamingAdapter.URL_STATION_STATUS)).thenReturn(Files.readAllLines(new File("src/test/resources/station_status.json").toPath(), Charset.defaultCharset()));
        new Job(kafkaReaderStreamingAdapter).execute();

        //THEN
        Assertions.assertThat("Dans la station Louis le Grand - Italiens se situant à 0,110000 kilomètres il y a 29 vélos disponibles" + System.lineSeparator() +
                "Dans la station Choiseul - Quatre Septembre  se situant à 0,130000 kilomètres il y a 34 vélos disponibles" + System.lineSeparator() +
                "Dans la station Laffitte - Italiens se situant à 0,160000 kilomètres il y a 29 vélos disponibles" + System.lineSeparator() +
                "Dans la station Chaussée d'Antin - Haussmann se situant à 0,210000 kilomètres il y a 27 vélos disponibles" + System.lineSeparator() +
                "Dans la station Favart - Italiens se situant à 0,220000 kilomètres il y a 12 vélos disponibles" + System.lineSeparator() +
                "Dans la station Daunou - Louis le Grand se situant à 0,290000 kilomètres il y a 9 vélos disponibles" + System.lineSeparator() +
                "Dans la station Rossini - Laffitte se situant à 0,330000 kilomètres il y a 27 vélos disponibles" + System.lineSeparator() +
                "Dans la station Bibliothèque Nationale - Richelieu se situant à 0,360000 kilomètres il y a 8 vélos disponibles" + System.lineSeparator() +
                "Dans la station Filles Saint-Thomas - Place de la Bourse se situant à 0,400000 kilomètres il y a 49 vélos disponibles" + System.lineSeparator() +
                "Dans la station Danielle Casanova - Opéra se situant à 0,400000 kilomètres il y a 15 vélos disponibles" + System.lineSeparator() +
                "").isEqualTo(outContent.toString());
    }
}
