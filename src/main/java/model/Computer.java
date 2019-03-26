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

	private Computer() {
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

	public static class Builder {
		private int id;
		private String name;
		private Timestamp introduced;
		private Timestamp discontinued;
		private int companyId;
		private Company company;

		public Builder() {
		}

		public Builder withId(int id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withIntroducedDate(Timestamp introduced) {
			this.introduced = introduced;
			return this;
		}

		public Builder withDiscontinuedDate(Timestamp discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public Builder withCompanyId(int companyId) {
			this.companyId = companyId;
			return this;
		}

		public Builder withCompany(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			Computer computer = new Computer();
			computer.setId(this.id);
			computer.setName(this.name);
			computer.setIntroduced(this.introduced);
			computer.setDiscontinued(this.discontinued);
			computer.setCompanyId(this.companyId);
			computer.setCompany(this.company);
			return computer;
		}
	}

}
