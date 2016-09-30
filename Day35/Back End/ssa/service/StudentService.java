package com.ssa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssa.dao.IStudentDAO;
import com.ssa.entity.Student;

@Service
public class StudentService implements IStudentService {
	
	@Autowired
	private IStudentDAO studentDAO;

	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return studentDAO.getAllStudents();
	}

	@Override
	public Student getStudentById(int studentId) {
		// TODO Auto-generated method stub
//		Student studs = null;
//		for(Student stu : getAllStudents())
//		{
//			if(stu.getId() == studentId)
//				studs = stu;
//		}
//		
//		return studs;
		return studentDAO.getStudentById(studentId);
	}

	@Override
	public boolean addStudent(Student student) {
		studentDAO.addStudent(student);
		return false;
	}

	@Override
	public void updateStudent(Student student) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStudent(Student student) {
		studentDAO.deleteStudent(student);
		
	}

}
