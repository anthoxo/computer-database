package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import dto.ComputerDTO;
import exception.DAOException;
import model.Company;
import model.Computer;
import utils.Utils;

public class ComputerMapper {

	private static ComputerMapper instance = null;

	/**
	 * Default constructor.
	 */
	private ComputerMapper() {

	}

	/**
	 * @return The only instance of ComputerMapper.
	 */
	public static ComputerMapper getInstance() {
		if (instance == null) {
			instance = new ComputerMapper();
		}
		return instance;
	}

	/**
	 * Map the ResultSet rs into Computer object.
	 *
	 * @param rs The ResultSet
	 * @return New Computer object.
	 * @throws SQLException if there is a problem with SQL connection.
	 */
	public Computer map(ResultSet rs) throws SQLException {
		return (new Computer.Builder()).withId(rs.getInt("id")).withName(rs.getString("name"))
				.withIntroducedDate(rs.getTimestamp("introduced")).withDiscontinuedDate(rs.getTimestamp("discontinued"))
				.withCompanyId(rs.getInt("company_id")).build();
	}

	/**
	 * Convert a model to its DTO representation.
	 *
	 * @param computer The computer we want to transform.
	 * @return A computer DTO.
	 */
	public ComputerDTO createDTO(Computer computer) {
		ComputerDTO cDTO = new ComputerDTO();

		cDTO.setId(computer.getId());
		cDTO.setName(computer.getName());
		cDTO.setIntroducedDate(Utils.timestampToString(computer.getIntroduced()));
		cDTO.setDiscontinuedDate(Utils.timestampToString(computer.getDiscontinued()));
		cDTO.setCompanyId(computer.getCompanyId());
		if (computer.getCompany() != null) {
			cDTO.setCompanyName(computer.getCompany().getName());
		}

		return cDTO;
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
		if (intro.isPresent()) {
			computerBuilder = computerBuilder.withIntroducedDate(intro.get());
		}
		if (discontinued.isPresent()) {
			computerBuilder = computerBuilder.withDiscontinuedDate(discontinued.get());
		}

		return computerBuilder.withId(cDTO.getId()).withName(cDTO.getName()).withCompanyId(cDTO.getCompanyId())
				.withCompany(company).build();
	}

}
