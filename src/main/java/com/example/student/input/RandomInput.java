package com.example.student.input;

import com.example.student.model.Student;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class RandomInput implements DataInput {

    private final Random random = new Random();

    @Override
    public List<Student> read(int size) {   // Метод read() создаёт и возвращает список студентов
        return Stream.generate(this::createRandomStudent)
                .limit(size)
                .toList();    // Метод toList() преобразует Stream в обычный список List<Student>
    }
    // метод создаёт одного студента со случайными данными
    // Метод возвращает объект Student
    private Student createRandomStudent() {
        int groupNumber = random.nextInt(100, 1000);  // от 100 включительно до 1000 не включительно

        double scoreAverage = 2.0
                + random.nextDouble() * 3.0;//случайный средний балл находится в допустимом для студента диапазоне от 2 до 5

        scoreAverage = Math.round(scoreAverage * 10) / 10.0;  // Округляем средний балл до одного знака после запятой

        int cardNumber = random.nextInt(10000, 100000);     // Значение будет от 10000 включительнодо 100000 не включительно

        // Создаём объект Student с помощью паттерна Builder.
        return new Student.Builder(groupNumber)
                .scoreAverage(scoreAverage)
                .cardNumber(cardNumber)
                .build();
    }
}