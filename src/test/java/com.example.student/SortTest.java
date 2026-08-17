package com.example.student;

import com.example.student.model.Student;
import com.example.student.sorting.Sort;

import java.util.ArrayList;
import java.util.List;

final class SortTest {

    private SortTest() {}

    static boolean runAll() {
        return TestSupport.runTestClass("Sort", () -> {
            testSortingByGroupNumber();
            testSortingByScoreAverage();
            testSortingByCardNumber();
            testSortingByAllThreeFields();
            testEmptyList();
        });
    }

    private static void testSortingByGroupNumber() {
        assertSorted(
                List.of(
                        StudentTest.student(103, 4.0, 30),
                        StudentTest.student(101, 4.0, 10),
                        StudentTest.student(102, 4.0, 20)
                ),
                List.of(
                        StudentTest.student(101, 4.0, 10),
                        StudentTest.student(102, 4.0, 20),
                        StudentTest.student(103, 4.0, 30)
                ),
                "Сортировка по номеру группы работает неверно"
        );
    }

    private static void testSortingByScoreAverage() {
        assertSorted(
                List.of(
                        StudentTest.student(101, 4.5, 30),
                        StudentTest.student(101, 3.5, 10),
                        StudentTest.student(101, 4.0, 20)
                ),
                List.of(
                        StudentTest.student(101, 3.5, 10),
                        StudentTest.student(101, 4.0, 20),
                        StudentTest.student(101, 4.5, 30)
                ),
                "Сортировка по среднему баллу работает неверно"
        );
    }

    private static void testSortingByCardNumber() {
        assertSorted(
                List.of(
                        StudentTest.student(101, 4.0, 30),
                        StudentTest.student(101, 4.0, 10),
                        StudentTest.student(101, 4.0, 20)
                ),
                List.of(
                        StudentTest.student(101, 4.0, 10),
                        StudentTest.student(101, 4.0, 20),
                        StudentTest.student(101, 4.0, 30)
                ),
                "Сортировка по номеру зачётной книжки работает неверно"
        );
    }

    private static void testSortingByAllThreeFields() {
        assertSorted(
                List.of(
                        StudentTest.student(102, 3.0, 30),
                        StudentTest.student(101, 4.5, 20),
                        StudentTest.student(101, 3.5, 40),
                        StudentTest.student(101, 3.5, 10)
                ),
                List.of(
                        StudentTest.student(101, 3.5, 10),
                        StudentTest.student(101, 3.5, 40),
                        StudentTest.student(101, 4.5, 20),
                        StudentTest.student(102, 3.0, 30)
                ),
                "Общая сортировка по трём полям работает неверно"
        );
    }

    private static void testEmptyList() {
        List<Student> empty = new ArrayList<>();
        Sort.SortByAllFields(empty);
        TestSupport.assertTrue(empty.isEmpty(), "Пустой список должен остаться пустым");
    }

    private static void assertSorted(
            List<Student> source,
            List<Student> expected,
            String errorMessage
    ) {
        List<Student> actual = new ArrayList<>(source);
        Sort.SortByAllFields(actual);
        TestSupport.assertEquals(expected, actual, errorMessage);
    }
}
