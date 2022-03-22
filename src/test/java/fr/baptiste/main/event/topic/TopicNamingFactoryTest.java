package fr.baptiste.main.event.topic;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TopicNamingFactoryTest {
    @Test
    public void itShouldBuildTopicName() {
        //GIVEN
        TopicNamingFactory topicNamingFactory = new TopicNamingFactory("tableNameTest", TopicNamingFactory.treatement.STREAMING);

        //WHEN
        String result = topicNamingFactory.buildTopicName();

        //THEN
        Assertions.assertThat(result).isEqualTo("tablenametest.streaming");
    }
}