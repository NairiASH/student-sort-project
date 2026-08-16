package com.example.student;

import com.example.student.model.Student;

final class StudentTest {

    private StudentTest() {}

    static boolean runAll() {
        return TestSupport.runTestClass("Student", () -> {
            //
            Student student = student(101, 4.5, 12345);

            TestSupport.assertEquals(101, student.getGroupNumber(), "Неверный номер группы");
            TestSupport.assertDoubleEquals(4.5, student.getScoreAverage(), "Неверный средний балл");
            TestSupport.assertEquals(12345, student.getCardNumber(), "Неверный номер зачётной книжки");
            TestSupport.assertEquals("101 4.5 12345", student.toString(), "Неверный формат toString");

            Student equalStudent = student(101, 4.5, 12345);
            TestSupport.assertTrue(student.equals(equalStudent), "Равные студенты не распознаны");
            TestSupport.assertEquals(student.hashCode(), equalStudent.hashCode(),
                    "У равных студентов должны совпадать hashCode");
            TestSupport.assertTrue(!student.equals(student(102, 4.5, 12345)),
                    "Разные студенты ошибочно признаны равными");
        });
    }

    static Student student(int group, double score, int card) {
        return new Student.Builder(group)
                .scoreAverage(score)
                .cardNumber(card)
                .build();
    }
}
