package com.example.student.input;

import com.example.student.model.Student;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;// позволяет создать поток целых чисел

public class ConsoleInput implements DataInput {

    private final Scanner scanner;

    public ConsoleInput(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public List<Student> read(int size) {
        return IntStream.range(0, size) //метод readStudent() будет вызван ровно size раз
                .mapToObj(i -> readStudent())   //Для каждого числа из потока вызываем метод readStudent()
                .toList();   // Собираем все созданные объекты Student в список List<Student>
    }
//метод для ввода данных одного студента
    private Student readStudent() {
        int groupNumber = readInteger(
                "Введите номер группы, например 123: ",
                100,
                999
        );

        double scoreAverage = readDouble(
                "Введите средний балл, например 4.5: ",
                0,
                5
        );

        int cardNumber = readInteger(
                "Введите номер зачетной книжки, например 12345: ",
                1,
                99999
        );

        return new Student.Builder(groupNumber)
                .scoreAverage(scoreAverage)
                .cardNumber(cardNumber)
                .build();
    }

    private int readInteger(String message, int min, int max) {
        while (true) {
            System.out.print(message);

            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();

                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scanner.next();
            }

            System.out.println("Ошибка! Введите корректное целое число.");
        }
    }

    private double readDouble(String message, double min, double max) {
        while (true) {
            System.out.print(message);

            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();

                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scanner.next();
            }

            System.out.println("Ошибка! Введите число от "
                    + min + " до " + max + ".");
        }
    }
}