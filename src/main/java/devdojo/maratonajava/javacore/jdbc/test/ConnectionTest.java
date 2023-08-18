package devdojo.maratonajava.javacore.jdbc.test;

import devdojo.maratonajava.javacore.jdbc.Service.ProducerService;
import devdojo.maratonajava.javacore.jdbc.dominio.Producer;
import devdojo.maratonajava.javacore.jdbc.repository.ProducerRepository;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ConnectionTest {

    public static void main(String[] args) {
        Producer producer = Producer.ProducerBuilder.builder()
                        .name("NHK").build();

        Producer producerToUpdate = Producer.ProducerBuilder.builder().id(4)
                .name("Casa anime").build();

        //ProducerService.save(producer);

        //ProducerService.delete(3);
        //ProducerService.update(producerToUpdate);

        //List<Producer> producers = ProducerService.findAll();

        List<Producer> producers = ProducerService.findByName("NHK");
        log.info(producers);

    }
}
