package fr.baptiste.main.event.deserialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.baptiste.main.domain.Gbfs;
import fr.baptiste.main.event.exception.DeserializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

public class GbfsDeserializer implements Deserializer<Gbfs>, Serializable {
    private static final Logger log = LoggerFactory.getLogger(GbfsDeserializer.class);

    //used to implement configuration details - no need here
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Gbfs deserialize(String s, byte[] bytes) {
        try {
            if (bytes == null) {
                log.info("trying to deserialize null value");
                return null;
            }
            return new ObjectMapper().readValue(bytes, Gbfs.class);
        } catch (Exception e) {
            throw new DeserializationException("Error during the deserialization of an Gbfs object", e);
        }
    }

    //use to dispose of some resources - no need here
    @Override
    public void close() {

    }
}
