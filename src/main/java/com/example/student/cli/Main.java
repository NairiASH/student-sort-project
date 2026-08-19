package com.example.student.cli;

import com.example.student.input.ConsoleInput;
import com.example.student.input.DataInput;
import com.example.student.input.FileInput;
import com.example.student.input.RandomInput;
import com.example.student.model.Student;
import com.example.student.sorting.Sort;
import com.example.student.extra.ResultFileService;
import com.example.student.extra.SelectiveBubbleSort;
import com.example.student.extra.OccurrenceCounter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class  Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ResultFileService fileService = new ResultFileService();

    public static void main(String[] args) {
        try {
            displayWelcome();
            List<Student> students = new ArrayList<>(loadStudents());

            if (students.isEmpty()) {
                System.out.println("Студенты не загружены.");
                return;
            }

            mainMenu(students);
        } finally {
            scanner.close();
        }
    }

    private static void displayWelcome() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║    Консольное приложение управления    ║");
        System.out.println("║           списком студентов            ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
    }

    private static List<Student> loadStudents() {
        while (true) {
            System.out.println("Выберите способ загрузки студентов:");
            System.out.println("1 - Загрузить из файла (resources/students.txt)");
            System.out.println("2 - Ввести вручную из консоли");
            System.out.println("3 - Сгенерировать случайных студентов");
            System.out.print("Ваш выбор: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        return loadFromFile();
                    case 2:
                        return loadFromConsole();
                    case 3:
                        return loadRandom();
                    default:
                        System.out.println("Ошибка! Выберите 1, 2 или 3.\n");
                }
            } else {
                scanner.nextLine();
                System.out.println("Ошибка! Введите число.\n");
            }
        }
    }

    private static List<Student> loadFromFile() {
        try {
            DataInput fileInput = new FileInput(Path.of("resources\\student.txt"), scanner);
            List<Student> students = fileInput.read(0);
            System.out.println("✓ Студенты успешно загружены!\n");
            return students;
        } catch (Exception e) {
            System.out.println("✗ Ошибка: " + e.getMessage() + "\n");
            System.out.print("Вернуться в главное меню? (да/нет): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("да") || response.equals("y")) {
                return loadStudents();
            } else {
                return loadFromFile();
            }
        }
    }

    private static List<Student> loadFromConsole() {
        System.out.print("Введите количество студентов: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Ошибка! Введите число: ");
            scanner.nextLine();
        }
        int count = scanner.nextInt();
        scanner.nextLine();

        if (count <= 0) {
            System.out.println("Ошибка! Количество должно быть положительным.\n");
            return loadFromConsole();
        }

        System.out.println();
        DataInput consoleInput = new ConsoleInput(scanner);
        List<Student> students = consoleInput.read(count);
        System.out.println("\n✓ Студенты успешно загружены!\n");
        return students;
    }

    private static List<Student> loadRandom() {
        System.out.print("Введите количество случайных студентов: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Ошибка! Введите число: ");
            scanner.nextLine();
        }
        int count = scanner.nextInt();
        scanner.nextLine();

        if (count <= 0) {
            System.out.println("Ошибка! Количество должно быть положительным.\n");
            return loadRandom();
        }

        System.out.println();
        DataInput randomInput = new RandomInput();
        List<Student> students = randomInput.read(count);
        System.out.println("✓ " + count + " случайных студентов сгенерировано!\n");
        return students;
    }

    private static void mainMenu(List<Student> students) {
        while (true) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║              ГЛАВНОЕ МЕНЮ              ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("1 - Отобразить список студентов");
            System.out.println("2 - Отсортировать студентов");
            System.out.println("3 - Подсчитать вхождения элемента");
            System.out.println("4 - Загрузить новый список");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                System.out.println();

                switch (choice) {
                    case 1:
                        displayStudents(students);
                        break;
                    case 2:
                        sortStudents(students);
                        break;
                    case 3:
                        countOccurrences(students);
                        break;
                    case 4:
                        List<Student> newStudents = loadStudents();
                        if (!newStudents.isEmpty()) {
                            students.clear();
                            students.addAll(newStudents);
                        }
                        break;
                    case 0:
                        System.out.println("До свидания!");
                        return;
                    default:
                        System.out.println("✗ Ошибка! Выберите пункт из меню.\n");
                }
            } else {
                scanner.nextLine();
                System.out.println("✗ Ошибка! Введите число.\n");
            }
        }
    }

    private static void displayStudents(List<Student> students) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║            СПИСОК СТУДЕНТОВ            ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  Группа  │  Средний балл  │  Зачётка   ║");
        System.out.println("╠════════════════════════════════════════╣");

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("║   %3d    │     %5.1f      │   %5d    ║%n",
                    s.getGroupNumber(), s.getScoreAverage(), s.getCardNumber());
        }

        System.out.println("╚════════════════════════════════════════╝\n");
    }

    private static void sortStudents(List<Student> students) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║        ВЫБОР МЕТОДА СОРТИРОВКИ         ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1 - Стандартная сортировка (все студенты)");
        System.out.println("2 - Избирательная сортировка (чётные зачётки)");
        System.out.print("Ваш выбор: ");

        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("✗ Ошибка! Введите число.\n");
            return;
        }

        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        List<Student> sortedStudents = new ArrayList<>(students);

        switch (choice) {
            case 1:
                Sort.SortByAllFields(sortedStudents);
                displaySortResult(sortedStudents, 
                    "Сохраненный список студентов");
                break;
            case 2:
                SelectiveBubbleSort selectiveSort = new SelectiveBubbleSort();
                selectiveSort.sortEvenCardNumbers(sortedStudents);
                System.out.println("✓ Студенты с чётными номерами зачёток отсортированы!");
                System.out.println();
                displayStudents(sortedStudents);
                askToSave(sortedStudents, "Сортировка студентов с чётными номерами зачёток");
                break;
            default:
                System.out.println("✗ Ошибка! Выберите 1 или 2.\n");
        }
    }

    private static void displaySortResult(List<Student> sortedStudents, String description) {
        System.out.println("✓ Студенты отсортированы!");
        System.out.println();
        displayStudents(sortedStudents);
        askToSave(sortedStudents, description);
    }

    private static void askToSave(List<Student> students, String description) {
        System.out.print("Сохранить результат? (да/нет): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("да") || response.equals("y")) {
            fileService.saveResults(students, description);
            System.out.println("✓ Результаты сохранены в файл saved-results.txt\n");
        } else {
            System.out.println();
        }
    }

    private static void countOccurrences(List<Student> students) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║       ПОДСЧЁТ ВХОЖДЕНИЙ ЭЛЕМЕНТА       ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1 - Подсчитать по номеру группы");
        System.out.println("2 - Подсчитать по среднему баллу");
        System.out.println("3 - Подсчитать по номеру зачётки");
        System.out.print("Ваш выбор: ");

        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("✗ Ошибка! Введите число.\n");
            return;
        }

        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        OccurrenceCounter counter = new OccurrenceCounter();

        switch (choice) {
            case 1:
                System.out.print("Введите номер группы: ");
                if (scanner.hasNextInt()) {
                    int groupNumber = scanner.nextInt();
                    scanner.nextLine();
                    counter.countOccurrencesGroupNumber(students, groupNumber);
                } else {
                    scanner.nextLine();
                    System.out.println("✗ Ошибка! Введите целое число.");
                }
                break;
            case 2:
                System.out.print("Введите средний балл: ");
                if (scanner.hasNextDouble()) {
                    double scoreAverage = scanner.nextDouble();
                    scanner.nextLine();
                    counter.countOccurrencesScoreAverage(students, scoreAverage);
                } else {
                    scanner.nextLine();
                    System.out.println("✗ Ошибка! Введите число.");
                }
                break;
            case 3:
                System.out.print("Введите номер зачётки: ");
                if (scanner.hasNextInt()) {
                    int cardNumber = scanner.nextInt();
                    scanner.nextLine();
                    counter.countOccurrencesCardNumber(students, cardNumber);
                } else {
                    scanner.nextLine();
                    System.out.println("✗ Ошибка! Введите целое число.");
                }
                break;
            default:
                System.out.println("✗ Ошибка! Выберите 1, 2 или 3.\n");
        }
        System.out.println();
    }
}
