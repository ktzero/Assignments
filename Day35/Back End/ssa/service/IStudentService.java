package com.ssa.service;

import java.util.List;

import com.ssa.entity.Student;

public interface IStudentService {

	List<Student> getAllStudents();
	Student getStudentById(int studentId);
	boolean addStudent(Student student);
	void updateStudent(Student student);
	void deleteStudent(Student student);
}
