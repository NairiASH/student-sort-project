package Programe;

import com.example.student.model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;

public class Sort {

    public static List<Student> loadStudents(Path filePath) throws IOException {
        List<Student> students = new ArrayList<>();

        List<String> lines = Files.readAllLines(filePath);

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\s+");

            if (parts.length != 3) {
                System.err.println("Пропущена некорректная строка: " + line);
                continue;
            }

            try {
                Integer groupNumber = Integer.valueOf(parts[0]);
                Double scoreAverage = Double.valueOf(parts[1]);
                Integer cardNumber = Integer.valueOf(parts[2]);

                Student student = new Student.Builder(groupNumber)
                        .scoreAverage(scoreAverage)
                        .cardNumber(cardNumber)
                        .build();

                students.add(student);

            } catch (NumberFormatException e) {
                System.err.println("Ошибка в числовых данных: " + line);
            }
        }

        return students;
    }

    public static void SortByAllFields(List<Student> students) {
        for (int i = 0; i < students.size() - 1; i++) {
            boolean swapped = false;
            
            for (int j = 0; j < students.size() - 1 - i; j++) {
            	int column = 0;
            	double firstScore;
            	double secondScore;
            	do {
                firstScore = getScore(students.get(j), column);
                secondScore = getScore(students.get(j + 1), column);

                if (firstScore > secondScore) {
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);

                    swapped = true;
                }
                column += 1;
            	} while(firstScore == secondScore && column < 3);
                
            }

            if (!swapped) {
                break;
            }
        }
    }

    private static double getScore(Student student, int n) {
        String[] parts = student.toString().split("\\s+");
        return Double.parseDouble(parts[n]);
    }
    
    public static void saveStudents(
            Path filePath,
            List<Student> students
    ) throws IOException {
        List<String> lines = new ArrayList<>();

        for (Student student : students) {
            lines.add(student.toString());
        }

        Files.write(
                filePath,
                lines,
                StandardCharsets.UTF_8
        );
    }
}





// как использовать Sort
    //public static void main(String[] args) {
     //   Path path = Path.of("D:\\перенести\\java\\2.txt");

     //   try {
     //       List<Student> students = Sort.loadStudents(path);

     //       Sort.SortByAllFields(students);
      //      Sort.saveStudents(path, students);

     //       System.out.println("Студентов: " + students.size());
       //     System.out.println("Файл успешно отсортирован и сохранён.");

       //     for (Student student : students) {
       //         System.out.println(student);
       //     }

      //  } catch (IOException e) {
       //     System.err.println(
       //             "Ошибка работы с файлом: " + e.getMessage()
        //    );
        //}
   // }
