package com.example.student.extra;

import com.example.student.model.Student;

import java.util.List;

public class CountWorker implements Runnable {
    private final List<Student> students;
    private final StudentMatcher matcher;
    private final int start;
    private final int end;
    private int count;

    public CountWorker(List<Student> students, StudentMatcher matcher, int start, int end) {
        this.students = students;
        this.matcher = matcher;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            Student currentStudent = students.get(i);
            if (matcher.matches(currentStudent)) {
                count++;
            }
        }
    }

    public int getCount() {
        return count;
    }
}
