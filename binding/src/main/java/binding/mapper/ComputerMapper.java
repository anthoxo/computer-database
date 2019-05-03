package binding.mapper;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import binding.dto.ComputerDTO;
import core.model.Company;
import core.model.Computer;
import core.util.Utils;
import persistence.CompanyRepository;

@Component
public class ComputerMapper {

	CompanyRepository companyRepository;

	private ComputerMapper(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	/**
	 * Convert a model to its DTO representation.
	 *
	 * @param computer The computer we want to transform.
	 * @return A computer DTO.
	 */
	public ComputerDTO createDTO(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();

		computerDTO.setId(computer.getId());
		computerDTO.setName(computer.getName());
		computerDTO.setIntroducedDate(Utils.timestampToString(computer.getIntroduced()));
		computerDTO.setDiscontinuedDate(Utils.timestampToString(computer.getDiscontinued()));
		if (computer.getCompany() != null) {
			computerDTO.setCompanyId(computer.getId());
			computerDTO.setCompanyName(computer.getCompany().getName());
		}
		return computerDTO;
	}

	/**
	 * Convert a DTO to its model.
	 *
	 * @param cDTO The DTO we want to transform.
	 * @return A Company model.
	 */
	public Computer createEntity(ComputerDTO cDTO) {
		Timestamp intro = Utils.stringToTimestamp(cDTO.getIntroducedDate()).orElse(null);
		Timestamp discontinued = Utils.stringToTimestamp(cDTO.getDiscontinuedDate()).orElse(null);
		Company company = this.companyRepository.findById(cDTO.getCompanyId()).orElse(null);

		return (new Computer.Builder())
				.withId(cDTO.getId())
				.withName(cDTO.getName())
				.withIntroducedDate(intro)
				.withCompany(company)
				.withDiscontinuedDate(discontinued)
				.build();
	}
}
