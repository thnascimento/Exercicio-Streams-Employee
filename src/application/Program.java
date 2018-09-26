package application;

import entities.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*Programa que, através da leitura de um arquivo, preenche uma lista de empregados.
* em seguida pede um valor ao usuário e utilza streams para gerar uma lista com o
* email em ordem alfabética dos empregados com salario maior que valor informado.
* em seguida faz a soma de todos os salários dos empregados cujo nome começa com "M"
* */

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the full file path: ");
        String path = sc.nextLine();

        try(BufferedReader br = new BufferedReader(new FileReader(path))){

            List<Employee> list = new ArrayList<>();

            String line = br.readLine();

            while(line != null){
                String[] lines = line.split(",");
                list.add(new Employee(lines[0], lines[1], Double.parseDouble(lines[2])));
                line = br.readLine();
            }

            System.out.print("Enter salary: ");
            Double salary = sc.nextDouble();
            System.out.printf("Email of people whose salary is more than %.2f:\n", salary);

            List<String> emails = list.stream()
                    .filter(x -> x.getSalary() > salary)
                    .map(x -> x.getEmail())
                    .sorted(Comparator.comparing(String::toUpperCase))
                    .collect(Collectors.toList());

            emails.forEach(System.out::println);

            Double sum = list.stream()
                    .filter(x -> x.getName().charAt(0) == 'M')
                    .map(x -> x.getSalary())
                    .reduce(0.0, (x, y) -> x + y);

            System.out.printf("Sum of salary of people whose name starts with 'M': %.2f", sum);

        } catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
