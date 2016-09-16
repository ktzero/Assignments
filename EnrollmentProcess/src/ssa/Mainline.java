package ssa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class Mainline {

	
	public static void main(String[] args) throws SQLException {
		
		ArrayList<Student> students = new ArrayList<Student>();
	
		//enroll students
		students.add(new Student("Adam", "Zapel", 1200, 3.0));
		students.add(new Student("Graham", "Krakir", 500, 2.5));
		students.add(new Student("Ella", "Vader", 800, 3.0));
		students.add(new Student("Stanley", "Kupp", 1350, 3.3));
		students.add(new Student("Lou", "Zar", 950, 3.0));
		students.get(0).assignMajor(3);
		students.get(1).assignMajor(7);
		students.get(2).assignMajor(2);
		students.get(3).assignMajor(5);
		students.get(4).assignMajor(6);
		students.get(0).enrollClass();
		for(Student stu: students)
		{
			stu.display();
		}
		
	}
}
