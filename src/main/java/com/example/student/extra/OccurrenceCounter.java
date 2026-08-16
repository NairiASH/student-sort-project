package com.example.student.extra;

import com.example.student.model.Student;

import java.util.List;
import java.util.Objects;

public class OccurrenceCounter {

    private static final int THREAD_COUNT = 5;

    /**
     * Подсчитывает количество вхождений искомого студента в коллекцию
     * и выводит результат в консоль.
     *
     * @param students      коллекция студентов
     * @param targetStudent искомый студент
     */
    public void countOccurrencesStudent(List<Student> students, Student targetStudent) {
        if (targetStudent == null) {
            System.out.println("Искомый студент не задан");
            return;
        }
        countOccurrences(students,
                student -> student.equals(targetStudent),
                "Количество совпадений: ");
    }

    /**
     * Подсчитывает количество студентов с заданным номером группы
     * и выводит результат в консоль.
     *
     * @param students          коллекция студентов
     * @param targetGroupNumber искомый номер группы
     */
    public void countOccurrencesGroupNumber(List<Student> students, Integer targetGroupNumber) {
        if (targetGroupNumber == null) {
            System.out.println("Номер группы не задан");
            return;
        }
        countOccurrences(students,
                student -> Objects.equals(student.getGroupNumber(), targetGroupNumber),
                "Количество студентов в группе " + targetGroupNumber + ": ");
    }

    /**
     * Подсчитывает количество студентов с заданным средним баллом
     * и выводит результат в консоль.
     *
     * @param students           коллекция студентов
     * @param targetScoreAverage искомый средний балл
     */
    public void countOccurrencesScoreAverage(List<Student> students, Double targetScoreAverage) {
        if (targetScoreAverage == null) {
            System.out.println("Средний балл не задан");
            return;
        }
        countOccurrences(students,
                student -> Objects.equals(student.getScoreAverage(), targetScoreAverage),
                "Количество студентов в группе со средним баллом " + targetScoreAverage + ": ");
    }

    /**
     * Подсчитывает количество студентов с заданным номером зачётной книжки
     * и выводит результат в консоль.
     *
     * @param students         коллекция студентов
     * @param targetCardNumber искомый номер зачётной книжки
     */
    public void countOccurrencesCardNumber(List<Student> students, Integer targetCardNumber) {
        if (targetCardNumber == null) {
            System.out.println("Номер зачётной книжки не задан");
        }

        countOccurrences(students,
                student -> Objects.equals(student.getCardNumber(), targetCardNumber),
                "Количество студентов с номером зачётной книжки " + targetCardNumber + ": ");
    }


    private void countOccurrences(List<Student> students, StudentMatcher matcher, String resultMessage) {

        if (students == null) {
            System.out.println("Список студентов не задан");
            return;
        }

        if (students.isEmpty()) {
            System.out.println("Список студентов пуст");
            return;
        }
        int actualThreadCount = Math.min(THREAD_COUNT, students.size());
        int partSize = (students.size() + actualThreadCount - 1) / actualThreadCount;

        CountWorker[] workers = new CountWorker[actualThreadCount];
        Thread[] threads = new Thread[actualThreadCount];

        for (int i = 0; i < actualThreadCount; i++) {
            int start = i * partSize;
            int end = Math.min(start + partSize, students.size());

            workers[i] = new CountWorker(students, matcher, start, end);
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Подсчёт был прерван");
                return;
            }
        }
        int totalCount = 0;
        for (CountWorker worker : workers) {
            totalCount += worker.getCount();
        }
        System.out.println(resultMessage + totalCount);
    }
}