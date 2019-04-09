package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import dto.CompanyDTO;
import model.Company;

@Component
public class CompanyMapper {

	private CompanyMapper() {
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
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(company.getId());
		companyDTO.setName(company.getName());
		return companyDTO;
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
