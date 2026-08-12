package com.example.student.extra;

import com.example.student.model.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ResultFileService {

    private final String fileName = "saved-results.txt";

    /**
     * Сохраняет переданную коллекцию студентов в файл для результатов.
     * Данные добавляются в конец файла, поэтому ранее сохранённые записи не удаляются.
     *
     * @param students          коллекция студентов для сохранения
     * @param resultDescription описание результата или выполненной операции
     */

    public void saveResults(List<Student> students, String resultDescription) {

        if (Objects.isNull(resultDescription) || Objects.isNull(students)) {
            return;
        }

        try (FileWriter fileWriter = new FileWriter("resources/" + fileName, true)) {
            fileWriter.write("==================================================================");
            fileWriter.write(System.lineSeparator());
            fileWriter.write(resultDescription);
            fileWriter.write(System.lineSeparator());

            for (Student student : students) {
                fileWriter.write(student.toString());
                fileWriter.write(System.lineSeparator());
            }
            fileWriter.write(System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        Student student1 = new Student.Builder(101)
//                .scoreAverage(4.5)
//                .cardNumber(12345)
//                .build();
//        Student student2 = new Student.Builder(102)
//                .scoreAverage(3.8)
//                .cardNumber(12346)
//                .build();
//        Student student3 = new Student.Builder(103)
//                .scoreAverage(4.9)
//                .cardNumber(12347)
//                .build();
//        List<Student> students = Arrays.asList(student1, student2, student3);
//
//        ResultFileService resultFileService = new ResultFileService();
//        resultFileService.saveResults(students, "Сортировка по среднему баллу по возрастанию");
//    }
}
