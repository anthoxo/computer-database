package binding.dto;

public class ComputerDTO {

	private int id;
	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private int companyId;
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

	public int getCompanyId() {
		return this.companyId;
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

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
