package devdojo.maratonajava.javacore.crud.dominio;

public class Producer {
    private Integer id;
    private String name;


    public static final class ProducerBuilder {
        private Integer id;
        private String name;

        private ProducerBuilder() {
        }

        public static ProducerBuilder builder() {
            return new ProducerBuilder();
        }

        public ProducerBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ProducerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProducerBuilder(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Producer build() {
            Producer producer = new Producer();
            producer.name = this.name;
            producer.id = this.id;
            return producer;
        }
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
