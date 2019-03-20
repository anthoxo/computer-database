package dto;

public class ComputerDTO {
	private int id;
	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private String companyName;

	/**
	 * Default constructor.
	 */
	public ComputerDTO() {

	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getIntroducedDate() {
		return this.introducedDate;
	}

	public String getDiscontinuedDate() {
		return this.discontinuedDate;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroducedDate(String introduced) {
		this.introducedDate = introduced;
	}

	public void setDiscontinuedDate(String discontinued) {
		this.discontinuedDate = discontinued;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
