package binding.mapper;

import org.springframework.stereotype.Component;

import binding.dto.CompanyDTO;
import core.model.Company;

@Component
public class CompanyMapper {

	private CompanyMapper() {
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
