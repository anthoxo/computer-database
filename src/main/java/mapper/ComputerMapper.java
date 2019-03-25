package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
		Computer computer = new Computer();
		computer.setId(rs.getInt("id"));
		computer.setName(rs.getString("name"));
		computer.setIntroduced(rs.getTimestamp("introduced"));
		computer.setDiscontinued(rs.getTimestamp("discontinued"));
		computer.setCompanyId(rs.getInt("company_id"));
		return computer;
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
		Computer computer = new Computer();

		computer.setId(cDTO.getId());
		computer.setName(cDTO.getName());
		computer.setIntroduced(Utils.stringToTimestamp(cDTO.getIntroducedDate()));
		computer.setDiscontinued(Utils.stringToTimestamp(cDTO.getDiscontinuedDate()));
		computer.setCompanyId(cDTO.getCompanyId());

		Company company = new Company();
		company.setId(cDTO.getCompanyId());
		company.setName(cDTO.getCompanyName());
		computer.setCompany(company);

		return computer;
	}

}
