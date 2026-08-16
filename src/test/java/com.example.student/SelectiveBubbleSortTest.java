package com.example.student;

import com.example.student.extra.SelectiveBubbleSort;
import com.example.student.model.Student;

import java.util.ArrayList;
import java.util.List;

final class SelectiveBubbleSortTest {

    private SelectiveBubbleSortTest() {
    }

    static boolean runAll() {
        return TestSupport.runTestClass("SelectiveBubbleSort", () -> {
            Student oddFirst = StudentTest.student(101, 4.0, 9);
            Student oddSecond = StudentTest.student(102, 4.0, 7);
            List<Student> students = new ArrayList<>(List.of(
                    StudentTest.student(103, 4.0, 8),
                    oddFirst,
                    StudentTest.student(104, 4.0, 4),
                    oddSecond,
                    StudentTest.student(105, 4.0, 6)
            ));

            new SelectiveBubbleSort().sortEvenCardNumbers(students);

            TestSupport.assertEquals(List.of(4, 9, 6, 7, 8),
                    students.stream().map(Student::getCardNumber).toList(),
                    "Чётные номера отсортированы неверно");
            TestSupport.assertTrue(students.get(1) == oddFirst && students.get(3) == oddSecond,
                    "Студенты с нечётными номерами изменили позиции");
        });
    }
}
