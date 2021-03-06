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
        Assertions.assertThat("Dans la station Louis le Grand - Italiens se situant ?? 0,110000 kilom??tres il y a 29 v??los disponibles" + System.lineSeparator() +
                "Dans la station Choiseul - Quatre Septembre  se situant ?? 0,130000 kilom??tres il y a 34 v??los disponibles" + System.lineSeparator() +
                "Dans la station Laffitte - Italiens se situant ?? 0,160000 kilom??tres il y a 29 v??los disponibles" + System.lineSeparator() +
                "Dans la station Chauss??e d'Antin - Haussmann se situant ?? 0,210000 kilom??tres il y a 27 v??los disponibles" + System.lineSeparator() +
                "Dans la station Favart - Italiens se situant ?? 0,220000 kilom??tres il y a 12 v??los disponibles" + System.lineSeparator() +
                "Dans la station Daunou - Louis le Grand se situant ?? 0,290000 kilom??tres il y a 9 v??los disponibles" + System.lineSeparator() +
                "Dans la station Rossini - Laffitte se situant ?? 0,330000 kilom??tres il y a 27 v??los disponibles" + System.lineSeparator() +
                "Dans la station Biblioth??que Nationale - Richelieu se situant ?? 0,360000 kilom??tres il y a 8 v??los disponibles" + System.lineSeparator() +
                "Dans la station Filles Saint-Thomas - Place de la Bourse se situant ?? 0,400000 kilom??tres il y a 49 v??los disponibles" + System.lineSeparator() +
                "Dans la station Danielle Casanova - Op??ra se situant ?? 0,400000 kilom??tres il y a 15 v??los disponibles" + System.lineSeparator() +
                "").isEqualTo(outContent.toString());
    }
}
