package com.example.student.input;

import com.example.student.model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class FileInput implements DataInput {

    private final Path filePath;
    private final Scanner scanner;

    public FileInput(Path filePath, Scanner scanner) {
        this.filePath = filePath;
        this.scanner = scanner;
    }
    // Метод read загружает студентов из файла
    @Override
    public List<Student> read(int size) {

        try (Stream<String> lines = Files.lines(filePath)) {

            List<Student> students = lines
                    .filter(line -> !line.isBlank())   // Убираем пустые строки из файла
                    .map(this::parseStudent)   // Каждую строку преобразуем в объект Student
                    .toList();   // Собираем результаты Stream в List

            if (students.isEmpty()) {   // Проверяем есть ли хотя бы один студент в файле
                throw new IllegalArgumentException(
                        "Файл не содержит студентов."
                );
            }

            System.out.println(
                    "В файле найдено студентов: " + students.size()
            );

            System.out.println(
                    "1 - Загрузить всех студентов"
            );
            System.out.println(
                    "2 - Загрузить определённое количество"
            );

            int choice = readChoice();

            if (choice == 1) {
                return students;
            }

            int count = readCount(students.size());
            // Если пользователь выбрал второй вариант спрашиваем, сколько студентов необходимо загрузить
            // Создаём новый список, содержащий только первые count студентов
            return students.stream()
                    .limit(count)
                    .toList();  // Преобразуем поток обратно в список

        } catch (IOException e) {
            throw new RuntimeException(
                    "Не удалось прочитать файл.",
                    e
            );
        }
    }
    // Метод отвечает за получение от пользователя выбора 1 или 2
    private int readChoice() {
        while (true) {
            System.out.print("Выберите вариант: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                // Допустимы только значения 1 и 2
                if (choice == 1 || choice == 2) {
                    return choice;
                }
            } else {
                scanner.next();
            }

            System.out.println("Ошибка! Введите 1 или 2.");
        }
    }
    // Метод спрашивает пользователя, сколько студентов необходимо загрузить из файла
    private int readCount(int max) {
        while (true) {
            System.out.print(   // Выводим диапазон допустимых значений
                    "Сколько студентов загрузить (1-" + max + "): "
            );

            if (scanner.hasNextInt()) {
                int count = scanner.nextInt();
                // Проверяем, что количество находится в допустимом диапазоне
                if (count >= 1 && count <= max) {
                    return count;
                }
            } else {
                scanner.next();
            }

            System.out.println(
                    "Ошибка! Количество должно быть от 1 до " + max + "."
            );
        }
    }
    // Метод преобразует одну строку файла в объект Student
    private Student parseStudent(String line) {
        // Убираем пробелы в начале и конце строки и разделяем строку по одному или нескольким пробелам
        String[] parts = line.trim().split("\\s+");
        // Например: "101 4.5 12345" превратится в:
        // parts[0] = "101"
        // parts[1] = "4.5"
        // parts[2] = "12345"

        // Проверяем, что в строке ровно три значения
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Неверный формат строки: " + line
            );
        }
        // преобразуем текстовые значения в числа
        try {
            int groupNumber = Integer.parseInt(parts[0]);
            double scoreAverage = Double.parseDouble(parts[1]);
            int cardNumber = Integer.parseInt(parts[2]);
            // Проверяем, что полученные значения находятся в допустимых пределах
            validate(groupNumber, scoreAverage, cardNumber);
            // Создаём объект Student
            return new Student.Builder(groupNumber)
                    .scoreAverage(scoreAverage)
                    .cardNumber(cardNumber)
                    .build();

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Некорректные данные: " + line
            );
        }
    }
    // Метод проверяет корректность данных одного студента
    private void validate(
            int groupNumber,
            double scoreAverage,
            int cardNumber
    ) {
        if (groupNumber <= 0) {
            throw new IllegalArgumentException(
                    "Номер группы должен быть положительным."
            );
        }

        if (scoreAverage < 0 || scoreAverage > 5) {
            throw new IllegalArgumentException(
                    "Средний балл должен быть от 0 до 5."
            );
        }

        if (cardNumber <= 0) {
            throw new IllegalArgumentException(
                    "Номер зачётной книжки должен быть положительным."
            );
        }
    }
}