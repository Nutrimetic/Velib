package fr.baptiste.main.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StationStatus implements Serializable {
    private final Data data;
    private final int lastUpdatedOther; //date de dernière mis-à jour des informations de la ville
    private final int ttl; // « time to live » , durée de vie de l’information au-delà de laquelle elle doit être considérée comme obsolète

    public StationStatus(@JsonProperty("data") Data data,
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
        StationStatus that = (StationStatus) o;
        return lastUpdatedOther == that.lastUpdatedOther && ttl == that.ttl && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, lastUpdatedOther, ttl);
    }

    @Override
    public String toString() {
        return "StationStatus{" +
                "data=" + data +
                ", lastUpdateOther=" + lastUpdatedOther +
                ", ttl=" + ttl +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data implements Serializable {
        private final List<Station> stations;


        public Data(@JsonProperty("stations") List<Station> stations) {
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
            private final long stationId; //numéro unique d’identification de la station. Ce numéro identifie la station au sein du service Vélib’ Métropole
            private final int isInstalled; //variable binaire indiquant si la station est. La station a déjà été déployée (1) ou est encore en cours de déploiement (0)
            private final int isRenting; //variable binaire indiquant si la station peut louer des vélos (is_renting=1 si le statut de la station est Operative)
            private final int isReturning; //variable binaire indiquant si la station peut recevoir des vélos (is_renting=1 si le statut de la station est Operative)
            private final int lastReported; // date de la dernière mise-à-jour
            private final int numBikesAvailable; //nombre de vélos disponibles
            private final int numDocksAvailable; //nombre de bornettes disponibles
            private final int numBikesAvailable2; //nombre de vélos disponibles
            private final List<NumBikesAvailableTypes> numBikesAvailableTypes; //nombre de vélos disponibles avec distinctions entre Vélib’ mécanique et électrique

            public Station(@JsonProperty("station_id") long stationId,
                           @JsonProperty("is_installed") int isInstalled,
                           @JsonProperty("is_renting") int isRenting,
                           @JsonProperty("is_returning") int isReturning,
                           @JsonProperty("last_reported") int lastReported,
                           @JsonProperty("numBikesAvailable") int numBikesAvailable,
                           @JsonProperty("numDocksAvailable") int numDocksAvailable,
                           @JsonProperty("num_bikes_available") int numBikesAvailable2,
                           @JsonProperty("num_bikes_available_types") List<NumBikesAvailableTypes> numBikesAvailableTypes) {
                this.stationId = stationId;
                this.isInstalled = isInstalled;
                this.isRenting = isRenting;
                this.isReturning = isReturning;
                this.lastReported = lastReported;
                this.numBikesAvailable = numBikesAvailable;
                this.numDocksAvailable = numDocksAvailable;
                this.numBikesAvailable2 = numBikesAvailable2;
                this.numBikesAvailableTypes = numBikesAvailableTypes;
            }

            public long getStationId() {
                return stationId;
            }

            public int getIsInstalled() {
                return isInstalled;
            }

            public int getIsRenting() {
                return isRenting;
            }

            public int getIsReturning() {
                return isReturning;
            }

            public int getLastReported() {
                return lastReported;
            }

            public int getNumBikesAvailable() {
                return numBikesAvailable;
            }

            public int getNumDocksAvailable() {
                return numDocksAvailable;
            }

            public int getNumBikesAvailable2() {
                return numBikesAvailable2;
            }

            public List<NumBikesAvailableTypes> getNumBikesAvailableTypes() {
                return numBikesAvailableTypes;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Station station = (Station) o;
                return stationId == station.stationId && isInstalled == station.isInstalled && isRenting == station.isRenting && isReturning == station.isReturning && lastReported == station.lastReported && numBikesAvailable == station.numBikesAvailable && numDocksAvailable == station.numDocksAvailable && numBikesAvailable2 == station.numBikesAvailable2 && Objects.equals(numBikesAvailableTypes, station.numBikesAvailableTypes);
            }

            @Override
            public int hashCode() {
                return Objects.hash(stationId, isInstalled, isRenting, isReturning, lastReported, numBikesAvailable, numDocksAvailable, numBikesAvailable2, numBikesAvailableTypes);
            }

            @Override
            public String toString() {
                return "Station{" +
                        "stationId=" + stationId +
                        ", isInstalled=" + isInstalled +
                        ", isRenting=" + isRenting +
                        ", isReturning=" + isReturning +
                        ", lastReported=" + lastReported +
                        ", numBikesAvailable=" + numBikesAvailable +
                        ", numDocksAvailable=" + numDocksAvailable +
                        ", numBikesAvailable2=" + numBikesAvailable2 +
                        ", numBikesAvailableTypes=" + numBikesAvailableTypes +
                        '}';
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class NumBikesAvailableTypes implements Serializable {
                private final int ebike;
                private final int mechanical;

                public NumBikesAvailableTypes(@JsonProperty("ebike") int ebike,
                                              @JsonProperty("mechanical") int mechanical) {
                    this.ebike = ebike;
                    this.mechanical = mechanical;
                }

                public int getEbike() {
                    return ebike;
                }

                public int getMechanical() {
                    return mechanical;
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;
                    NumBikesAvailableTypes that = (NumBikesAvailableTypes) o;
                    return ebike == that.ebike && mechanical == that.mechanical;
                }

                @Override
                public int hashCode() {
                    return Objects.hash(ebike, mechanical);
                }

                @Override
                public String toString() {
                    return "NumBikesAvailableTypes{" +
                            "ebike=" + ebike +
                            ", mechanical=" + mechanical +
                            '}';
                }
            }
        }
    }
}
