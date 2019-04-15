package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import dto.CompanyDTO;
import model.Company;

@Component
public class CompanyMapper implements RowMapper<Company> {

	private CompanyMapper() {
	}

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
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
