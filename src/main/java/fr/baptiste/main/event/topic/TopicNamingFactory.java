package fr.baptiste.main.event.topic;

/**
 * This class has the responsibility of creating the topic name through a certain number of parameters like
 * tableName, treatement, etc. All topics created should respect a norm defined in this class.
 *
 * Exemple : stationInformation.streaming
 */
public class TopicNamingFactory {
    private final String tableName;
    private final treatement treatement;

    public TopicNamingFactory(String tableName, treatement treatement) {
        this.tableName = tableName;
        this.treatement = treatement;
    }

    public String buildTopicName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tableName.toLowerCase());
        stringBuilder.append(".");
        stringBuilder.append(treatement.name().toLowerCase());

        return stringBuilder.toString();
    }

    public enum treatement {
       STREAMING,
       BATCH;
   }
}
