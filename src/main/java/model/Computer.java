package model;

import java.sql.Timestamp;

public class Computer {
	int id;
	String name;
	Timestamp introduced;
	Timestamp discontinued;
	int companyId;
	Company company;
	
	public Computer() { }
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Timestamp getIntroduced() {
		return this.introduced;
	}
	
	public Timestamp getDiscontinued() {
		return this.discontinued;
	}
	
	public int getCompanyId() {
		return this.companyId;
	}
	
	public Company getCompany() {
		return this.company;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}
	
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		String result = "Name: " + this.getName() + 
				"\nIntroduced in: " + this.getIntroduced() +
				"\nDiscountinued in: " + this.getDiscontinued();
		if (this.getCompany() != null) {
			result += "\nCompany: " + this.getCompany().toString();
		}
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		} else {
			Computer computer = (Computer)obj;
			return this.id == computer.id && 
					this.companyId == computer.companyId &&
					this.name.equals(computer.name) &&
					this.introduced.equals(computer.introduced) &&
					this.discontinued.equals(computer.discontinued) &&
					this.company.equals(computer.company);
		}
	}
 
}
