package core.model;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "computer")
public class Computer {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(name = "name")
	String name;

	@Column(name = "introduced")
	Timestamp introduced;

	@Column(name = "discontinued")
	Timestamp discontinued;

	@ManyToOne(fetch = FetchType.EAGER, optional = true, targetEntity = Company.class)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
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
		if (this.getCompany() != null) {
			ts.append("company", company.toString());
		}
		return ts.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Computer other = (Computer) obj;
		return Objects.equals(company, other.company) && Objects.equals(discontinued, other.discontinued)
				&& id == other.id && Objects.equals(introduced, other.introduced) && Objects.equals(name, other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(company, discontinued, id, introduced, name);
	}

	public static class Builder {
		private int id;
		private String name;
		private Timestamp introduced;
		private Timestamp discontinued;
		private Company company;

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
			computer.setCompany(this.company);
			return computer;
		}
	}

}
