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

public class Student {

	public static Connection myConn = null;
	public static PreparedStatement myStmt = null;
	public static ResultSet myRs = null;
	
	private int stuID;
	private String firstName, lastName;
	private int sat, majorID;
	private double gpa;
	ArrayList<String> classInMajor;
	ArrayList<String> classNotInMajor;
	
	public Student(String firstName, String lastName, int sat, double gpa) throws SQLException
	{
		int setSAT = 0;
		double setGPA = 0;
		//check for valid sat, else will use default sat = 0
		if(sat > 0 && sat < 1401)
			setSAT = sat; 
		if(gpa > 0 && gpa < 4.01)
			setGPA = gpa;
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.sat = setSAT;
		majorID = -1;
		gpa = setGPA;
		classInMajor = new ArrayList<String>();
		classNotInMajor = new ArrayList<String>();
		
		enrollStudent(this.firstName,this.lastName,this.sat, this.gpa);
	}
	
	public void assignMajor(int majorID) throws SQLException
	{
		try{
			//get major SAT req
			makeConnection();
			myStmt = myConn.prepareStatement("select req_sat from major where id=?");
			myStmt.setInt(1, majorID);
			myRs = myStmt.executeQuery();
			myRs.next();
			int req_sat = myRs.getInt("req_sat");
			//System.out.println(req_sat);
			//get student's SAT score
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
				this.majorID = majorID;
			}
			else
				System.out.println("The student does not meet the SAT requirement for this major");
		}
		catch(Exception e){e.printStackTrace();}
		finally{close();}
		
		
	}
	
	private void enrollStudent(String firstName, String lastName, int sat, double gpa) throws SQLException
	{
		try{
			makeConnection();
			myStmt = myConn.prepareStatement("insert into student(first_name, last_name, sat, gpa, major_id) "
					+ "values(?,?,?,?,null)");
			myStmt.setString(1, firstName);
			myStmt.setString(2, lastName);
			myStmt.setInt(3, sat);
			myStmt.setDouble(4, gpa);
			myStmt.executeUpdate();
			
			myStmt = myConn.prepareStatement("SELECT LAST_INSERT_ID()");
			myRs = myStmt.executeQuery();
			myRs.next();
			stuID = myRs.getInt(1);
		}
		catch(Exception e){e.printStackTrace();}
		finally{close();}
	}
	
	public void enrollClass() throws SQLException
	{
		try{
			makeConnection();

			
			ArrayList<String> majorClasses = new ArrayList<String>();
			//get all classes associated with major
			myStmt = myConn.prepareStatement("select * from major_class_relationship where major_id=?");
			myStmt.setInt(1, majorID);
			myRs = myStmt.executeQuery();
			while(myRs.next())
				majorClasses.add(myRs.getString("class_ID"));
			
			
			//get all classes again but this time enroll
			myStmt = myConn.prepareStatement("select * from major_class_relationship where major_id=?");
			myStmt.setInt(1, majorID);
			myRs = myStmt.executeQuery();
			//count how many classes
			int ctr = 0;
			//add major classes
			while(myRs.next() && ctr < 2)
			{
				String classID = myRs.getString("class_ID");
				classInMajor.add(classID);
				myStmt = myConn.prepareStatement("insert into student_class_relationship"
						+ "(student_id, class_id) values(?,?)");
				myStmt.setInt(1, stuID);
				myStmt.setInt(2,  Integer.parseInt(classID));
				myStmt.executeUpdate();
				ctr++;
				
			}
			
			//add regular classes (not in major)
			//check to see if major exists
			if(majorID < 0)
				myStmt = myConn.prepareStatement("select id from class");
			else
				myStmt = myConn.prepareStatement("SELECT id from class where id not in (" + String.join(",", majorClasses) + ")");
			//myStmt.setInt(1, majorID);
			
			myRs = myStmt.executeQuery();
			
			//count how many classes
			ctr = 0;
			//add regular classes
			while(myRs.next() && ctr < 2)
			{
				String classID = myRs.getString("id");
				classNotInMajor.add(classID);
				myStmt = myConn.prepareStatement("insert into student_class_relationship"
						+ "(student_id, class_id) values(?,?)");
				myStmt.setInt(1, stuID);
				myStmt.setInt(2,  Integer.parseInt(classID));
				myStmt.executeUpdate();
				ctr++;
			}
			
		}
		catch(Exception e){e.printStackTrace();}
		finally{close(); }
	}
	
	public void display() throws SQLException
	{
		try{
			System.out.println("Education System - Enrollment Process");
			System.out.println("=====================================");
			
			makeConnection();
			myStmt = myConn.prepareStatement("select * from student where id=?");
			myStmt.setInt(1, stuID);
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
				String major_desc;
				int major_sat;
				if(myRs.next())
				{
					major_desc = myRs.getString("description");
					major_sat = myRs.getInt("req_sat");
				}
				else
				{
					major_desc = "No major";
					major_sat = 0;
				}
				
				
				//get class info
				myStmt = myConn.prepareStatement("select * from student_class_relationship where student_id=?");
				myStmt.setInt(1, stuID);
				myRs = myStmt.executeQuery();
				//String major_desc = myRs.getString("description");
				//int major_sat = myRs.getInt("req_sat");
				
				
				//System.out.printf("%s %s %d %.2f %d\n", myRs.getString("first_name"), 
				//		myRs.getString("last_name"), myRs.getInt("sat") ,myRs.getDouble("gpa"), myRs.getInt("major_id"));
				System.out.printf("Enrolled %s as a new student with id %d\n", name, stuID);
				System.out.printf("%s has a SAT score of %d\n", name, sat);
				System.out.printf("Assigned %s major to the %s which requires SAT score of %d\n", 
						name, major_desc, major_sat);
				
				/**
				while(myRs.next()){
					myStmt = myConn.prepareStatement("select * from class where id=?");
					myStmt.setInt(1, myRs.getInt("class_id"));
					ResultSet tempRs = myStmt.executeQuery();
					tempRs.next();
					System.out.println(tempRs.getString("subject") + tempRs.getString("section"));
				}
				*/
				for(String id : classInMajor)
				{
					myStmt = myConn.prepareStatement("select * from class where id=?");
					myStmt.setInt(1, Integer.parseInt(id));
					ResultSet tempRs = myStmt.executeQuery();
					tempRs.next();
					System.out.println(tempRs.getString("subject") + tempRs.getString("section") + " required for major");
				}
				
				if(majorID < 0)
					System.out.printf("%s is not currently in a major\n", name);
				for(String id : classNotInMajor)
				{
					myStmt = myConn.prepareStatement("select * from class where id=?");
					myStmt.setInt(1, Integer.parseInt(id));
					ResultSet tempRs = myStmt.executeQuery();
					tempRs.next();
					System.out.println(tempRs.getString("subject") + tempRs.getString("section") + " (not required for major)");
				}
				
				
					
		
			}
		}
		catch(Exception e){e.printStackTrace();}
		finally{close(); }
		
		System.out.println("");
	}
	
	
	private void delete() throws SQLException
	{
		try{
			makeConnection();
			myStmt = myConn.prepareStatement("delete from student where last_name=?");
			myStmt.setString(1,"barker");
			myStmt.executeUpdate();
		}
		catch(Exception e){e.printStackTrace();}
		finally{close(); }
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
		if(myRs != null)
			myRs.close();
	}

	public String getFirstName() {	return firstName;	}

	public void setFirstName(String firstName) {	this.firstName = firstName;	}

	public String getLastName() {	return lastName;	}

	public void setLastName(String lastName) {	this.lastName = lastName;	}

	public int getSat() {	return sat;	}

	public void setSat(int sat) {	this.sat = sat;	}

	public int getMajorID() {	return majorID;	}

	public void setMajorID(int majorID) {	this.majorID = majorID;	}

	public double getGpa() {	return gpa;	}

	public void setGpa(double gpa) {	this.gpa = gpa;	}
	
	
}
