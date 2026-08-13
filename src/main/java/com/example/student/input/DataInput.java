package com.example.student.input;

import com.example.student.model.Student;

import java.util.List;

public interface DataInput {

    List<Student> read(int size);
}