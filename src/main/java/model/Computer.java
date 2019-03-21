package model;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Computer {
	int id;
	String name;
	Timestamp introduced;
	Timestamp discontinued;
	int companyId;
	Company company;

	/**
	 * Default constructor.
	 */
	public Computer() {
	}

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

	/**
	 * @return true if it's a valid computer, else false.
	 */
	public boolean isValidComputer() {
		boolean validComputer = true;
		if (this.getIntroduced() != null && this.getDiscontinued() != null) {
			if (this.getDiscontinued().getTime() - this.getIntroduced().getTime() < 0) {
				validComputer = false;
			}
		}
		return validComputer;
	}

	@Override
	public String toString() {
		ToStringBuilder ts = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		ts.append("id", id);
		ts.append("name", name);
		ts.append("introduced", introduced);
		ts.append("discontinued", discontinued);
		ts.append("company_id", companyId);
		if (this.getCompany() != null) {
			ts.append("company", company.toString());
		}
		return ts.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		} else {
			Computer computer = (Computer) obj;
			return this.id == computer.id && this.companyId == computer.companyId && this.name.equals(computer.name)
					&& this.introduced.equals(computer.introduced) && this.discontinued.equals(computer.discontinued)
					&& this.company.equals(computer.company);
		}
	}

	@Override
	public int hashCode() {
		return this.getId() + this.getName().hashCode() + this.getIntroduced().hashCode()
				+ this.getDiscontinued().hashCode() + this.getCompanyId() + this.getCompany().hashCode();
	}

}
