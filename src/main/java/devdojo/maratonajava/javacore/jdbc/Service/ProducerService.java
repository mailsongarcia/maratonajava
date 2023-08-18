package devdojo.maratonajava.javacore.jdbc.Service;

import devdojo.maratonajava.javacore.jdbc.dominio.Producer;
import devdojo.maratonajava.javacore.jdbc.repository.ProducerRepository;

import java.util.List;

public class ProducerService {
    public static void save(Producer producer) {
        ProducerRepository.save(producer);
    }
    public static void update( Producer producer) {
        requireValidId(producer.getId());
        ProducerRepository.update(producer);
    }
    public static void delete(int id) {
        requireValidId(id);
        ProducerRepository.delete( id);
    }

    private static void  requireValidId(Integer id){
        if(id == null && id <=0){
            throw new IllegalArgumentException("Invalid value for id");
        }
    }

    public static List<Producer>  findAll() {

        return ProducerRepository.findAll();
    }

    public static List<Producer>  findByName(String name) {
        return ProducerRepository.findByName(name);

    }

   
}
