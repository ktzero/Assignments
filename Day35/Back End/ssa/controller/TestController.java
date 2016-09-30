//package com.ssa.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import eddb.Student;
//
//@CrossOrigin
//@RestController
//public class TestController {
//    
//    @RequestMapping("/students")
//    public List<Student> students() {
//		ArrayList<Student> students = new ArrayList<Student>();
//		students.add(new Student("Greg","Doud", 1250, 3.5));
//		students.add(new Student("Kyle","Deen", 1050, 3.9));
//		students.add(new Student("Derp","Dorpson",635,2.0));
//		students.add(new Student("First","Name",325,3.52));
//		students.add(new Student("Perd","Hapley",1000,3.1));
//        return students;
//	}
//    
//	@RequestMapping("/student")
//    public Student student() {
//        return new Student("Kyle","Deen", 1050, 3.9);
//	}
//}
