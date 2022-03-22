package fr.baptiste.main.event.deserialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.baptiste.main.domain.StationStatus;
import fr.baptiste.main.event.exception.DeserializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

public class StationStatusDeserializer implements Deserializer<StationStatus>, Serializable {
    private static final Logger log = LoggerFactory.getLogger(StationStatusDeserializer.class);

    //used to implement configuration details - no need here
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public StationStatus deserialize(String s, byte[] bytes) {
        try {
            if (bytes == null) {
                log.info("trying to deserialize null value");
                return null;
            }
            return new ObjectMapper().readValue(bytes, StationStatus.class);
        } catch (Exception e) {
            throw new DeserializationException("Error during the deserialization of an StationStatus object", e);
        }
    }

    //use to dispose of some resources - no need here
    @Override
    public void close() {

    }
}
