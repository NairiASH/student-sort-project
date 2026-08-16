package com.example.student.extra;

import com.example.student.model.Student;

@FunctionalInterface
public interface StudentMatcher {
    boolean matches(Student student);
}
