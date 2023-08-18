package devdojo.maratonajava.javacore.crud.test;

import devdojo.maratonajava.javacore.crud.service.ProducerService;

import java.util.Scanner;

public class CrudTest01 {
    private static  final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        int op;
        while (true){
            producerMenu();
            op = Integer.parseInt(SCANNER.nextLine());
            if (op == 0) break;
            ProducerService.buildMenu(op);


        }
    }

    private static void producerMenu(){
        System.out.println("Type the number of you operation");
        System.out.println("1. Search for producer");
        System.out.println("2. Delete producer");
        System.out.println("3. Save producer");
        System.out.println("0. Exit");
    }
}
