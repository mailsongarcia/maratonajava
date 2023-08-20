package devdojo.maratonajava.javacore.crud.service;

import devdojo.maratonajava.javacore.crud.Repository.ProducerRepository;
import devdojo.maratonajava.javacore.crud.dominio.Producer;


import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProducerService {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void buildMenu(int op){
        switch (op) {
            case 1 -> findByName();
            case 2 -> delete();
            case 3 -> save();
            case 4 -> update();
            default -> throw new IllegalArgumentException("Not a valid option");
        }
    }



    private static void findByName (){
        System.out.println("Type the name or empty to all");
        String name = SCANNER.nextLine();
        List<Producer> producers = ProducerRepository.findByName(name);
        producers.forEach(p -> System.out.printf("[%d] -%s%n",  p.getId(), p.getName()));

    }
    private static void delete() {
        System.out.println("Type the id of the producer you want to delete");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Are you sure? S/N");
        String choice = SCANNER.nextLine();
        if("s".equalsIgnoreCase(choice)){
            ProducerRepository.delete(id);
        };
    }

    private static void save() {

        System.out.println("Type name the of the producer");
        String name = SCANNER.nextLine();
        Producer producer = Producer.ProducerBuilder.builder().name(name).build();
        ProducerRepository.save(producer);

    }

    private static void update() {
        System.out.println("Type the id of the objet you want to update?");
        Optional<Producer> producerOptional = ProducerRepository.findById(Integer.parseInt(SCANNER.nextLine()));
        if (producerOptional.isEmpty()){
            System.out.println("Producer not found");
            return;
        }
        Producer producerFromDB = producerOptional.get();
        System.out.println("Producer found " + producerFromDB);
        System.out.println("Type the new name or enter to keep the same");
        String name = SCANNER.nextLine();
        name = name.isEmpty() ? producerFromDB.getName() : name;
        Producer producerToUpdate = Producer.ProducerBuilder
                .builder()
                .id(producerFromDB.getId())
                .name(name)
                .build();
        System.out.println(producerToUpdate);
        ProducerRepository.update(producerToUpdate);


    }

}
