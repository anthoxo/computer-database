package com.excilys.computerdatabase.model;

public class Company {
	int id;
	String name;

	/**
	 * Default constructor.
	 */
	public Company() {
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		} else {
			Company company = (Company) obj;
			return this.id == company.id && this.name.equals(company.name);
		}
	}

	@Override
	public int hashCode() {
		return this.getId() + this.getName().hashCode();
	}
}
