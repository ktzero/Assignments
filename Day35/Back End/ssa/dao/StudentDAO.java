package com.ssa.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssa.entity.Student;

@Transactional
@Repository
public class StudentDAO implements IStudentDAO {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public List<Student> getAllStudents() {
        String hql = "FROM Student as s ORDER BY s.id";
        return (List<Student>) hibernateTemplate.find(hql);
    }

	@Override
	public Student getStudentById(int studentId) {
		String hql = "FROM Student where id=" + studentId;
        List<Student> stu = (List<Student>) hibernateTemplate.find(hql);
        if(!stu.isEmpty())
        	return stu.get(0);
        else
        	return null;
        	
	}

	@Override
	public boolean addStudent(Student student) {
		hibernateTemplate.save(student);
		return false;
	}

	@Override
	public void updateStudent(Student student) {
		hibernateTemplate.update(student);
		
	}

	@Override
	public void deleteStudent(Student student) {
		hibernateTemplate.delete(student);
	}
}
