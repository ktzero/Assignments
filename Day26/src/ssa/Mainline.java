package ssa;

import java.util.*;

public class Mainline {

	public static void main(String[] args) {
		// base assignment

		Students students = new Students();
		Student aStudent = students.getById(1064);
		System.out.println(aStudent);
	    
	    //=================================
	    //challenge
	    
	    //Student Bob = new Student("Bob","Barker",1300,3.5);
	    //students.insert(Bob);
		//aStudent.setGpa(3.33);
		students.delete(1064);
	    
	    List<Student> allStudents = students.getAll();
	    
	    for(Student student : allStudents)
	        System.out.println(student);
	}

}
