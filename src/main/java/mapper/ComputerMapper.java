package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import dao.CompanyDao;
import dto.ComputerDTO;
import exception.DAOException;
import model.Company;
import model.Computer;
import utils.Utils;

@Component
public class ComputerMapper implements RowMapper<Computer> {

	CompanyDao companyDao;

	private ComputerMapper(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Optional<Company> companyOpt;
		try {
			companyOpt = companyDao.get(rs.getInt("company_id"));
		} catch (DAOException e) {
			companyOpt = Optional.empty();
		}
		return (new Computer.Builder()).withId(rs.getInt("id")).withName(rs.getString("name"))
				.withIntroducedDate(rs.getTimestamp("introduced")).withDiscontinuedDate(rs.getTimestamp("discontinued"))
				.withCompanyId(rs.getInt("company_id")).withCompany(companyOpt.orElse(null)).build();
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
		computerDTO.setCompanyId(computer.getCompanyId());
		if (computer.getCompany() != null) {
			computerDTO.setCompanyName(computer.getCompany().getName());
		}

		return computerDTO;
	}

	/**
	 * Convert a DTO to its model.
	 *
	 * @param cDTO The DTO we want to transform.
	 * @return A Company model.
	 * @throws DAOException
	 */
	public Computer createBean(ComputerDTO cDTO) {
		Company company = (new Company.Builder()).withId(cDTO.getCompanyId()).withName(cDTO.getCompanyName()).build();
		Computer.Builder computerBuilder = new Computer.Builder();

		Optional<Timestamp> intro = Utils.stringToTimestamp(cDTO.getIntroducedDate());
		Optional<Timestamp> discontinued = Utils.stringToTimestamp(cDTO.getDiscontinuedDate());

		computerBuilder = computerBuilder.withIntroducedDate(intro.orElse(null))
				.withDiscontinuedDate(discontinued.orElse(null));

		return computerBuilder.withId(cDTO.getId()).withName(cDTO.getName()).withCompanyId(cDTO.getCompanyId())
				.withCompany(company).build();
	}
}
