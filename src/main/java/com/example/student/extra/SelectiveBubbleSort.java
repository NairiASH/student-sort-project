package com.example.student.extra;

import com.example.student.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectiveBubbleSort implements SortEvenCardNumbers {
    /**
     * Сортирует студентов с чётными номерами зачётных книжек по возрастанию.
     *
     * @param students коллекция студентов для сортировки
     */
    public void sortEvenCardNumbers(List<Student> students) {
        if (Objects.isNull(students) || students.isEmpty()) {
            System.out.println("Список студентов пуст или не задан");
            return;
        }
        List<Student> evenStudents = collectEvenStudents(students);
        bubbleSortByCardNumber(evenStudents);
        replaceEvenStudents(students, evenStudents);
//        System.out.println(students);
    }

    /**
     * Формирует временную коллекцию студентов с чётными номерами
     * зачётных книжек.
     *
     * @param students исходная коллекция студентов
     * @return коллекция студентов с чётными номерами зачётных книжек
     */
    private List<Student> collectEvenStudents(List<Student> students) {
        List<Student> evenStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getCardNumber() % 2 == 0) {
                evenStudents.add(student);
            }
        }
        return evenStudents;
    }

    /**
     * Сортирует коллекцию студентов пузырьковым методом
     * по возрастанию номера зачётной книжки.
     *
     * @param evenStudents коллекция студентов с чётными номерами
     *                     зачётных книжек
     */
    private void bubbleSortByCardNumber(List<Student> evenStudents) {
        if (evenStudents.size() < 2) {
            return;
        }
        for (int i = 0; i < evenStudents.size() - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < evenStudents.size() - 1 - i; j++) {
                Student leftStudent = evenStudents.get(j);
                Student rightStudent = evenStudents.get(j + 1);
                if (leftStudent.getCardNumber() > rightStudent.getCardNumber()) {
                    evenStudents.set(j + 1, leftStudent);
                    evenStudents.set(j, rightStudent);

                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    /**
     * Заменяет студентов с чётными номерами зачётных книжек
     * в исходной коллекции на студентов из отсортированной коллекции.
     * Студенты с нечётными номерами сохраняют исходные позиции.
     *
     * @param students           исходная коллекция студентов
     * @param sortedEvenStudents отсортированная коллекция студентов
     *                           с чётными номерами зачётных книжек
     */
    private void replaceEvenStudents(List<Student> students, List<Student> sortedEvenStudents) {
        int evenStudentIndex = 0;
        for (int i = 0; i < students.size(); i++) {
            Student currentStudent = students.get(i);
            if (currentStudent.getCardNumber() % 2 == 0) {
                students.set(i, sortedEvenStudents.get(evenStudentIndex));
                evenStudentIndex++;
            }
        }
    }
}
