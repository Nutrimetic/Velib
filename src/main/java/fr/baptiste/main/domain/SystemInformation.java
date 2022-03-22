package fr.baptiste.main.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemInformation implements Serializable {
    private final Data data;
    private final int lastUpdatedOther; //date de dernière mis-à jour des informations de la ville
    private final int ttl; // « time to live » , durée de vie de l’information au-delà de laquelle elle doit être considérée comme obsolète

    @JsonCreator
    public SystemInformation(@JsonProperty("data") Data data,
                             @JsonProperty("lastUpdatedOther") int lastUpdatedOther,
                             @JsonProperty("ttl") int ttl) {
        this.data = data;
        this.lastUpdatedOther = lastUpdatedOther;
        this.ttl = ttl;
    }

    public Data getData() {
        return data;
    }

    public int getLastUpdateOther() {
        return lastUpdatedOther;
    }

    public int getTtl() {
        return ttl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemInformation that = (SystemInformation) o;
        return lastUpdatedOther == that.lastUpdatedOther && ttl == that.ttl && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, lastUpdatedOther, ttl);
    }

    @Override
    public String toString() {
        return "SystemInformation{" +
                "data=" + data +
                ", lastUpdateOther=" + lastUpdatedOther +
                ", ttl=" + ttl +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data implements Serializable {
        private final String language; //langue utilisée dans l’ensemble des fichiers
        private final String name; //nom du système
        private final String systemId; //identifiant unique du système
        private final String timezone; //zone horaire associée au système
        private final String url; //adresse associée au système

        @JsonCreator
        Data(@JsonProperty("language") String language,
             @JsonProperty("name") String name,
             @JsonProperty("system_id") String systemId,
             @JsonProperty("timezone") String timezone,
             @JsonProperty("url") String url) {
            this.language = language;
            this.name = name;
            this.systemId = systemId;
            this.timezone = timezone;
            this.url = url;
        }

        public String getLanguage() {
            return language;
        }

        public String getName() {
            return name;
        }

        public String getSystemId() {
            return systemId;
        }

        public String getTimezone() {
            return timezone;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Objects.equals(language, data.language) && Objects.equals(name, data.name) && Objects.equals(systemId, data.systemId) && Objects.equals(timezone, data.timezone) && Objects.equals(url, data.url);
        }

        @Override
        public int hashCode() {
            return Objects.hash(language, name, systemId, timezone, url);
        }

        @Override
        public String toString() {
            return "Data{" +
                    "language='" + language + '\'' +
                    ", name='" + name + '\'' +
                    ", systemId='" + systemId + '\'' +
                    ", timezone='" + timezone + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
