package dto;

public class CompanyDTO {
	private int id;
	private String name;

	/**
	 * Default constructor.
	 */
	public CompanyDTO() {

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
}
