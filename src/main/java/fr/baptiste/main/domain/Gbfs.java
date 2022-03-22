package fr.baptiste.main.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Gbfs implements Serializable {
    private final Data data;
    private final int lastUpdatedOther; //date de dernière mis-à jour des informations de la ville
    private final int ttl; // « time to live » , durée de vie de l’information au-delà de laquelle elle doit être considérée comme obsolète

    @JsonCreator
    public Gbfs(@JsonProperty("data") Data data,
                @JsonProperty("lastUpdateOther") int lastUpdatedOther,
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
        Gbfs gbfs = (Gbfs) o;
        return lastUpdatedOther == gbfs.lastUpdatedOther && ttl == gbfs.ttl && Objects.equals(data, gbfs.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, lastUpdatedOther, ttl);
    }

    @Override
    public String toString() {
        return "Gbfs{" +
                "data=" + data +
                ", lastUpdateOther=" + lastUpdatedOther +
                ", ttl=" + ttl +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data implements Serializable {
        private final En en;

        @JsonCreator
        public Data(@JsonProperty("en") En en) {
            this.en = en;
        }

        public En getEn() {
            return en;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Objects.equals(en, data.en);
        }

        @Override
        public int hashCode() {
            return Objects.hash(en);
        }

        @Override
        public String toString() {
            return "Data{" +
                    "en=" + en +
                    '}';
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class En implements Serializable {
            private final List<Feed> feeds;

            @JsonCreator
            En(@JsonProperty("feeds") List<Feed> feeds) {
                this.feeds = feeds;
            }

            public List<Feed> getFeeds() {
                return feeds;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                En en = (En) o;
                return Objects.equals(feeds, en.feeds);
            }

            @Override
            public int hashCode() {
                return Objects.hash(feeds);
            }

            @Override
            public String toString() {
                return "En{" +
                        "feeds=" + feeds +
                        '}';
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Feed implements Serializable {
                private final String name;
                private final String url;

                @JsonCreator
                Feed(@JsonProperty("name") String name,
                     @JsonProperty("url") String url) {
                    this.name = name;
                    this.url = url;
                }

                public String getName() {
                    return name;
                }

                public String getUrl() {
                    return url;
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;
                    Feed feed = (Feed) o;
                    return Objects.equals(name, feed.name) && Objects.equals(url, feed.url);
                }

                @Override
                public int hashCode() {
                    return Objects.hash(name, url);
                }

                @Override
                public String toString() {
                    return "Feed{" +
                            "name='" + name + '\'' +
                            ", url='" + url + '\'' +
                            '}';
                }
            }
        }
    }
}
