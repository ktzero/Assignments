package ssa;

public class Student {

	private int id, sat;
	private String firstName, lastName;
	private double gpa;
	
	public String toString() {
		return String.format("%4d %-20s %4.2f %4d",this.id,
				this.firstName + " " + this.lastName,
				this.gpa, this.sat);
	}
	
	public Student(int id, String first, String last, int sat, double gpa)
	{
		this.id = id;
		this.firstName = first;
		this.lastName = last;
		this.sat = sat;
		this.gpa = gpa;
	}
	
	public Student(String first, String last, int sat, double gpa)
	{
		this.firstName = first;
		this.lastName = last;
		this.sat = sat;
		this.gpa = gpa;
	}

	public int getId() {	return id;	}

	public void setId(int id) {	this.id = id;	}

	public int getSat() {	return sat;	}

	public void setSat(int sat) {	this.sat = sat;	}

	public String getFirstName() {	return firstName;	}

	public void setFirstName(String firstName) {this.firstName = firstName;	}

	public String getLastName() {return lastName;}

	public void setLastName(String lastName) {	this.lastName = lastName;	}

	public double getGpa() {return gpa;	}

	public void setGpa(double gpa) {	this.gpa = gpa;	}
	
	
}
