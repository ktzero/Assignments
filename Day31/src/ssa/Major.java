package ssa;

import javax.persistence.*;


@Entity
@Table(name="major")

public class Major {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="req_sat")
	private int req_sat;
	
	public Major(){}

	public Major(String description, int req_sat) {
		this.description = description;
		this.req_sat = req_sat;
	}
	
	public String toString()
	{
		return String.format("Id: %s, Description: %s, required SAT: %d", 
				id, description, req_sat);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReq_sat() {
		return req_sat;
	}

	public void setReq_sat(int req_sat) {
		this.req_sat = req_sat;
	}
	
	
}
