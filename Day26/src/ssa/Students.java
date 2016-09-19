package ssa;

import java.sql.*;
import java.util.*;

public class Students extends HashMap<Integer, Student>{

	SqlDB db = null;
	
	public Students()
	{	super(); makeSqlConnection();	}
	
	private void makeSqlConnection() {
		try {
			Properties prop = new Properties();
			prop.load(new java.io.FileInputStream("demo.properties"));
			String url = prop.getProperty("dburl");
			String user = prop.getProperty("user");
			String pass = prop.getProperty("password");
			db = new SqlDB(url, user, pass);
		} catch (Exception ex) { ex.printStackTrace(); }
	}
	
	public void insert(Student stud)
	{
		try {
			db.executeSqlUpdate(String.format("INSERT student (first_name, last_name, gpa, sat) values ('%s', '%s', %f, %d)",
					stud.getFirstName(), stud.getLastName(), stud.getGpa(), stud.getSat()));
		} catch (SQLException ex) { ex.printStackTrace(); }
	}
	
	public void update(Student stud)
	{
		try {
			db.executeSqlUpdate(String.format("UPDATE student set first_name=%s, last_name=%s, gpa = %f, sat=%d"
					+ " where id = %d",
					stud.getFirstName(), stud.getLastName(), stud.getGpa(), stud.getSat(), stud.getId()));
		} catch (SQLException ex) { ex.printStackTrace(); }
	}
	
	public void delete(int id)
	{
		try {
			db.executeSqlUpdate(String.format("DELETE from student WHERE id=%d", id));
		} catch (SQLException ex) { ex.printStackTrace(); }
	}
	
	public Student getById(int id)
	{
		List<Student> students = new ArrayList<Student>();
		students = select("select * from student where id = " + id);
		if(students.isEmpty())
			return null;
		else
			return students.get(0);
	}
	
	public List<Student> getAll()
	{
		List<Student> students = select("select * from student");
		if(students.isEmpty())
			return null;
		else
			return students;
			
	}
	
	private List<Student> select(String sql)
	{
		ArrayList<Student> students = new ArrayList<Student>();
		ResultSet rs;
		try {
			rs = db.executeSqlQuery(sql);
			
			while(rs.next())
				students.add(new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
					rs.getInt("sat"), rs.getDouble("gpa")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return students;
	}
}
