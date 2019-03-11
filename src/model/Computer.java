package model;

import java.sql.Date;

public class Computer {
	int id;
	String name;
	Date introduced;
	Date discontinued;
	int companyId;
	
	public Computer() { }
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Date getIntroduced() {
		return this.introduced;
	}
	
	public Date getDiscontinued() {
		return this.discontinued;
	}
	
	public int getCompanyId() {
		return this.companyId;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}
	
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
 
}
