package com.example.student.model;


import com.example.student.model.Student;

import java.util.List;


public class Sort {                                            // как применять Sort.SortByAllFields(написать сюда);

 

    public static void SortByAllFields(List<Student> students) {
        for (int i = 0; i < students.size() - 1; i++) {
            boolean swapped = false;
            
            for (int j = 0; j < students.size() - 1 - i; j++) {
            	if (
            			students.get(j).getGroupNumber() > students.get(j + 1).getGroupNumber() || 
            			(
            					students.get(j).getGroupNumber().equals(students.get(j + 1).getGroupNumber()) && 
            					(
            							Double.compare(students.get(j).getScoreAverage(),students.get(j + 1).getScoreAverage()) > 0 ||
            							(
            									Double.compare( students.get(j).getScoreAverage(),students.get(j + 1).getScoreAverage()) == 0 && students.get(j).getCardNumber()> students.get(j + 1).getCardNumber()
            	                )
            	            )
            	        )
            	) {
                	ChangeStudent(students,j);
                    swapped = true;
                } 
                
            }

            if (!swapped) {
                break;
            }
        }
    }
    
    private static void ChangeStudent(List<Student> students, int j) {
	   	  Student temp = students.get(j);
	      students.set(j, students.get(j + 1));
	      students.set(j + 1, temp);
    } 

}
