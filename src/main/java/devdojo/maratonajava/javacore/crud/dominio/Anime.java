package devdojo.maratonajava.javacore.crud.dominio;

import lombok.Builder;


public class Anime {
    Integer id;
    String name;
    int episodes;
    Producer producer;


    public static final class AnimeBuilder {
        private Integer id;
        private String name;
        private int episodes;
        private Producer producer;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getEpisodes() {
            return episodes;
        }

        public Producer getProducer() {
            return producer;
        }

        private AnimeBuilder() {
        }

        public static AnimeBuilder anAnime() {
            return new AnimeBuilder();
        }

        public AnimeBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public AnimeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AnimeBuilder episodes(int episodes) {
            this.episodes = episodes;
            return this;
        }

        public AnimeBuilder producer(Producer producer) {
            this.producer = producer;
            return this;
        }

        public Anime build() {
            Anime anime = new Anime();
            anime.id = this.id;
            anime.name = this.name;
            anime.episodes = this.episodes;
            anime.producer = this.producer;
            return anime;
        }
    }
}


