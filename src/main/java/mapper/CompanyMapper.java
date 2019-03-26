package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import dto.CompanyDTO;
import model.Company;

public class CompanyMapper {

	private static CompanyMapper instance = null;

	/**
	 * Default constructor.
	 */
	private CompanyMapper() {

	}

	/**
	 * @return The only instance of CompanyMapper.
	 */
	public static CompanyMapper getInstance() {
		if (instance == null) {
			instance = new CompanyMapper();
		}
		return instance;
	}

	/**
	 * Map the ResultSet rs into Company object.
	 *
	 * @param rs The ResultSet
	 * @return New Company object.
	 * @throws SQLException if there is a problem with SQL connection.
	 */
	public Company map(ResultSet rs) throws SQLException {
		return (new Company.Builder()).withId(rs.getInt("id")).withName(rs.getString("name")).build();
	}

	/**
	 * Convert a model to its DTO representation.
	 *
	 * @param company The company we want to transform.
	 * @return A company DTO.
	 */
	public CompanyDTO createDTO(Company company) {
		CompanyDTO cDTO = new CompanyDTO();
		cDTO.setId(company.getId());
		cDTO.setName(company.getName());
		return cDTO;
	}

	/**
	 * Convert a DTO to its model.
	 *
	 * @param cDTO The DTO we want to transform.
	 * @return A Company model.
	 */
	public Company createBean(CompanyDTO cDTO) {
		return (new Company.Builder()).withId(cDTO.getId()).withName(cDTO.getName()).build();
	}
}
