package com.example.student;

import com.example.student.input.ConsoleInput;
import com.example.student.input.DataInput;
import com.example.student.input.FileInput;
import com.example.student.input.RandomInput;
import com.example.student.model.Student;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {

    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();  // Вызываем отдельный метод который выводит главное меню

            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Программа завершена.");
                break;
            }

            try {
                List<Student> students = readStudents(choice, scanner); // Вызываем метод readStudents(), который определяет,
                // какой способ заполнения студентов выбрал пользователь

                System.out.println("\nИсходные данные:");
                students.forEach(System.out::println);

            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }
    // Метод отвечает за вывод главного меню программы
    private static void printMenu() {
        System.out.println("\n===== МЕНЮ =====");
        System.out.println("1 - Ввести студентов вручную");
        System.out.println("2 - Заполнить случайными данными");
        System.out.println("3 - Загрузить студентов из файла");
        System.out.println("0 - Выход");
        System.out.print("Выберите действие: ");
    }
    // Метод отвечает за выбор способа получения студентов
    private static List<Student> readStudents(
            int choice,
            Scanner scanner
    ) {
        DataInput input = switch (choice) {
            case 1 -> new ConsoleInput(scanner);
            case 2 -> new RandomInput();
            case 3 -> new FileInput(
                    Path.of("resources\\student.txt"),
                    scanner
            );
            default -> throw new IllegalArgumentException(
                    "Неизвестный пункт меню."
            );
        };

        if (choice == 3) {
            return input.read(0);
        }

        System.out.print("Введите количество студентов: ");
        int size = scanner.nextInt();

        if (size <= 0) {
            throw new IllegalArgumentException(
                    "Количество студентов должно быть больше 0."
            );
        }

        return input.read(size);
    }
}