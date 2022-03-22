package fr.baptiste.main.application.job;

import fr.baptiste.main.SparkHelperForTest;
import fr.baptiste.main.utility.spark.SparkHelper;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class JobTest {

    private static SparkHelper sparkHelper;

    @BeforeClass
    public static void setUp() {
        sparkHelper = SparkHelperForTest.getSparkHelper();
    }

    @Test
    public void itShouldCalculateTheDistanceBetweenTwoPoint() {
        //GIVEN
        final double splioLon = 2.3353503;
        final double splioLat = 48.8709807;
        final double marceauChaillotLat = 48.86883394989261;
        final double marceauChaillotLon = 2.2989967837929726;

        //WHEN
        double result = new Job(null).calculFlightDistanceBetweenTwoPoint(splioLat, marceauChaillotLat, splioLon, marceauChaillotLon);

        //THEN
        Assertions.assertThat(result).isEqualTo(2.67);
    }
}