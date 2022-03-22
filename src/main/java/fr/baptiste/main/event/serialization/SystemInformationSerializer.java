package fr.baptiste.main.event.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.baptiste.main.domain.SystemInformation;
import fr.baptiste.main.event.exception.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

public class SystemInformationSerializer implements Serializer<SystemInformation>, Serializable {
    private static final Logger log = LoggerFactory.getLogger(SystemInformationSerializer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    //used to implement configuration details - no need here
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, SystemInformation systemInformation) {
        if (systemInformation == null) {
            log.info("trying to serialize null value");
            return null;
        }
        try {
            return objectMapper.writeValueAsString(systemInformation).getBytes();
        } catch (JsonProcessingException e) {
            throw new SerializationException(String.format("Error during the serialization of the following systemInformation object : %s", systemInformation), e);
        }
    }

    //use to dispose of some resources - no need here
    @Override
    public void close() {

    }
}
