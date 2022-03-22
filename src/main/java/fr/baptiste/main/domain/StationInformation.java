package fr.baptiste.main.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StationInformation implements Serializable {
    private final Data data;
    private final int lastUpdatedOther; //date de dernière mis-à jour des informations de la ville
    private final int ttl; // « time to live » , durée de vie de l’information au-delà de laquelle elle doit être considérée comme obsolète

    public StationInformation(@JsonProperty("data") Data data,
                              @JsonProperty("lastUpdatedOther") int lastUpdatedOther,
                              @JsonProperty("ttl") int ttl) {
        this.data = data;
        this.lastUpdatedOther = lastUpdatedOther;
        this.ttl = ttl;
    }

    public Data getData() {
        return data;
    }

    public int getLastUpdatedOther() {
        return lastUpdatedOther;
    }

    public int getTtl() {
        return ttl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationInformation that = (StationInformation) o;
        return lastUpdatedOther == that.lastUpdatedOther && ttl == that.ttl && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, lastUpdatedOther, ttl);
    }

    @Override
    public String toString() {
        return "StationInformation{" +
                "data=" + data +
                ", lastUpdateOther=" + lastUpdatedOther +
                ", ttl=" + ttl +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data implements Serializable {
        private final List<Station> stations;

        Data(@JsonProperty("stations") List<Station> stations) {
            this.stations = stations;
        }

        public List<Station> getStations() {
            return stations;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Objects.equals(stations, data.stations);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stations);
        }

        @Override
        public String toString() {
            return "Data{" +
                    "stations=" + stations +
                    '}';
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Station implements Serializable {
            private final int capacity; // nombre de bornettes dans la station
            private final double lat; //latitude de la station (au format WGS84)
            private final double lon; //longitude de la station (au format WGS84)
            private final String name; //nom de la station
            private final long stationId; //identifiant unique associé à la station. Ce numéro identifie la station au sein du service Vélib’ Métropole

            public Station(@JsonProperty("capacity") int capacity,
                           @JsonProperty("lat") double lat,
                           @JsonProperty("lon") double lon,
                           @JsonProperty("name") String name,
                           @JsonProperty("station_id") long stationId) {
                this.capacity = capacity;
                this.lat = lat;
                this.lon = lon;
                this.name = name;
                this.stationId = stationId;
            }

            public int getCapacity() {
                return capacity;
            }

            public double getLat() {
                return lat;
            }

            public double getLon() {
                return lon;
            }

            public String getName() {
                return name;
            }

            public long getStationId() {
                return stationId;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Station station = (Station) o;
                return capacity == station.capacity && lat == station.lat && lon == station.lon && stationId == station.stationId && Objects.equals(name, station.name);
            }

            @Override
            public int hashCode() {
                return Objects.hash(capacity, lat, lon, name, stationId);
            }

            @Override
            public String toString() {
                return "Station{" +
                        "capacity=" + capacity +
                        ", lat=" + lat +
                        ", lon=" + lon +
                        ", name='" + name + '\'' +
                        ", stationId=" + stationId +
                        '}';
            }
        }
    }
}
