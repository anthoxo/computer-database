package binding.mapper;

import java.sql.Timestamp;
import java.util.Optional;

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
		Computer.Builder computerBuilder = new Computer.Builder();

		Optional<Timestamp> intro = Utils.stringToTimestamp(cDTO.getIntroducedDate());
		Optional<Timestamp> discontinued = Utils.stringToTimestamp(cDTO.getDiscontinuedDate());

		Optional<Company> companyOpt = this.companyRepository.findById(cDTO.getCompanyId());

		computerBuilder = computerBuilder.withIntroducedDate(intro.orElse(null))
				.withDiscontinuedDate(discontinued.orElse(null));

		return computerBuilder.withId(cDTO.getId()).withName(cDTO.getName()).withCompany(companyOpt.orElse(null)).build();
	}
}
