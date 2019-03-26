package model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Company {
	int id;
	String name;

	private Company() {
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
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", id).append("name", name)
				.toString();
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

	public static class Builder {
		private int id;
		private String name;

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

		public Company build() {
			Company company = new Company();
			company.setId(id);
			company.setName(name);
			return company;
		}
	}
}
