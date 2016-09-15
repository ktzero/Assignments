package ssa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Mainline {

	public static Connection myConn = null;
	public static PreparedStatement myStmt = null;
	public static ResultSet myRs = null;
	
	public static void main(String[] args) throws SQLException {
	
		try {
			makeConnection();
			//delete();
			//enrollStudent("bob","barker",1000, 2.0);
			//assignMajor(1006,2);
			//enrollStudent("Adam", "Zapel", 1200, 3.0);
			//enrollStudent("Graham", "Krakir", 500, 2.5);
			//enrollStudent("Ella", "Vader", 800, 3.0);
			//enrollStudent("Stanley", "Kupp", 1350, 3.3);
			//enrollStudent("Lou", "Zar", 950, 3.0);
			//assignMajor(1007, 3);
			//assignMajor(1008, 7);
			//assignMajor(1009, 2);
			//assignMajor(1010, 5);
			//assignMajor(1011, 6);
			//enrollClass(1007, 10101);
			//enrollClass(1007, 10102);
			//enrollClass(1007, 20203);
			//enrollClass(1007, 20204);
			
			
			//display();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			close();
		}

	}
	
	public static boolean enrollClass(int student_ID, int class_ID) throws SQLException
	{
		boolean inMajor = false;
		
		myStmt = myConn.prepareStatement("select * from major_class_relationship where id=?");
		myStmt.setInt(1, class_ID);
		myRs = myStmt.executeQuery();
		if(myRs.next())
		{
			int class_majorID = myRs.getInt("major_ID");
			//System.out.println(req_sat);
			
			myStmt = myConn.prepareStatement("select * from student where id=?");
			myStmt.setInt(1, student_ID);
			myRs = myStmt.executeQuery();
			myRs.next();
			int stu_major = myRs.getInt("major_id");
			
			//check to see if class is in major
			if(stu_major >= class_majorID)
				inMajor = true;
		}

		myStmt = myConn.prepareStatement("insert into student_class_relationship"
				+ "(student_id, class_id) values(?,?)");
		myStmt.setInt(1, student_ID);
		myStmt.setInt(2,  class_ID);
		myStmt.executeUpdate();
		
		
		return inMajor;
	}
	
	public static void enrollStudent(String firstName, String lastName, int sat, double gpa) throws SQLException
	{
		myStmt = myConn.prepareStatement("insert into student(first_name, last_name, sat, gpa, major_id) "
				+ "values(?,?,?,?,null)");
		myStmt.setString(1, firstName);
		myStmt.setString(2, lastName);
		myStmt.setInt(3, sat);
		myStmt.setDouble(4, gpa);
		myStmt.executeUpdate();
	}
	
	public static void assignMajor(int stuID, int majorID) throws SQLException
	{
		myStmt = myConn.prepareStatement("select req_sat from major where id=?");
		myStmt.setInt(1, majorID);
		myRs = myStmt.executeQuery();
		myRs.next();
		int req_sat = myRs.getInt("req_sat");
		//System.out.println(req_sat);
		myStmt = myConn.prepareStatement("select sat from student where id=?");
		myStmt.setInt(1, stuID);
		myRs = myStmt.executeQuery();
		myRs.next();
		int stu_sat = myRs.getInt("sat");
		
		if(stu_sat >= req_sat)
		{
			myStmt = myConn.prepareStatement("update student set major_id =? where student.id=?");
			myStmt.setInt(1, majorID);
			myStmt.setInt(2,  stuID);
			myStmt.executeUpdate();
		}
		else
			System.out.println("The student does not meet the SAT requirement for this major");
		
		
	}
	
	public static void display(int stu_id) throws SQLException
	{
		System.out.println("Education System - Enrollment Process");
		System.out.println("=====================================");
		
		
		myStmt = myConn.prepareStatement("select * from student where id=?");
		myStmt.setInt(1, stu_id);
		myRs = myStmt.executeQuery();
		if(myRs.next())
		{
			//get student info
			String name = myRs.getString("first_name") + " " + myRs.getString("last_name");
			int sat = myRs.getInt("sat");
			int stu_major_id = myRs.getInt("major_id");
			
			//get major info
			myStmt = myConn.prepareStatement("select * from major where id=?");
			myStmt.setInt(1, stu_major_id);
			myRs = myStmt.executeQuery();
			myRs.next();
			String major_desc = myRs.getString("description");
			int major_sat = myRs.getInt("req_sat");
			
			//get class info
			myStmt = myConn.prepareStatement("select * from student_class_relationship where student_id=?");
			myStmt.setInt(1, stu_id);
			myRs = myStmt.executeQuery();
			myRs.next();
			//String major_desc = myRs.getString("description");
			//int major_sat = myRs.getInt("req_sat");
			
			
			//System.out.printf("%s %s %d %.2f %d\n", myRs.getString("first_name"), 
			//		myRs.getString("last_name"), myRs.getInt("sat") ,myRs.getDouble("gpa"), myRs.getInt("major_id"));
			System.out.printf("Enrolled %s as a new student", name);
			System.out.printf("%s has a SAT score of %d", name, sat);
			System.out.printf("Assigned %s to the %s which requires SAT score of %d", 
					name, major_desc, major_sat);
	
		}
	}
	
	public static void fetch() throws FileNotFoundException, IOException, SQLException
	{
		
		myStmt = myConn.prepareStatement("select * from student where gpa > ? and sat > ?");
		
		//set parameter
		myStmt.setDouble(1, 2.0);
		myStmt.setDouble(2, 1000);
		
		//execute query
		myRs = myStmt.executeQuery();
		
		//process the resultset
		//display();
	
	}
	
	private static void delete() throws SQLException
	{
			myStmt = myConn.prepareStatement("delete from student where last_name=?");
			myStmt.setString(1,"barker");
			myStmt.executeUpdate();
	}
	
	private static void insert()
	{
		try{
			myStmt = myConn.prepareStatement("insert into student values(?, ?, 'Barker', 1250, 3.0, 1)");
			myStmt.setInt(1, 999);
			myStmt.setString(2, "Bob");
			myStmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private static void makeConnection() throws FileNotFoundException, IOException, SQLException
	{
		Properties prop = new Properties();
		
		prop.load(new FileInputStream("demo.properties"));
		String user = prop.getProperty("user");
		String pass = prop.getProperty("password");
		String url = prop.getProperty("dburl");
		
		myConn= DriverManager.getConnection(url, user, pass);
	}
	
	private static void close() throws SQLException
	{
		if(myConn != null)
			myConn.close();
		if(myStmt != null)
			myStmt.close();
		if(myRs!= null)
			myRs.close();
	}


}
