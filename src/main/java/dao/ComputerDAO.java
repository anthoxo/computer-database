package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dto.ComputerDTO;
import exception.DAOException;
import mapper.ComputerMapper;
import model.Company;
import model.Computer;
import utils.Utils;

public class ComputerDAO {

	private static ComputerDAO instance = null;

	DAOFactory daoFactory = DAOFactory.getInstance();
	ComputerMapper computerMapper = ComputerMapper.getInstance();

	static final String REQUEST_CREATE = "INSERT INTO computer VALUES (?,?,?,?,?)";
	static final String REQUEST_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	static final String REQUEST_DELETE = "DELETE FROM computer WHERE id = ?";
	static final String REQUEST_GET_ALL = "SELECT id,name,introduced,discontinued,company_id FROM computer";
	static final String REQUEST_GET_BY_ID = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?";
	static final String REQUEST_GET_BY_NAME = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name = ?";
	static final String REQUEST_GET_LIKE = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name LIKE ?" ;

	/**
	 * Default constructor.
	 */
	private ComputerDAO() {
	}

	/**
	 * Get the only instance of ComputerDAO.
	 *
	 * @return The instance of ComputerDAO.
	 */
	public static ComputerDAO getInstance() {
		if (instance == null) {
			instance = new ComputerDAO();
		}
		return instance;
	}

	public void create(Computer obj) throws DAOException {
		boolean validComputer = obj.isValidComputer();
		if (validComputer) {
			try (Connection conn = this.daoFactory.getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(REQUEST_CREATE);
				stmt.setInt(1, obj.getId());
				stmt.setString(2, obj.getName());
				stmt.setTimestamp(3, obj.getIntroduced());
				stmt.setTimestamp(4, obj.getDiscontinued());
				if (daoFactory.getCompanyDAO().get(obj.getCompanyId()).isPresent()) {
					stmt.setInt(5, obj.getCompanyId());
				} else {
					stmt.setObject(5, null);
				}
				stmt.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		} else {
			// TODO throw new ValidatorException
		}
	}

	public Optional<Computer> get(int id) throws DAOException {
		Computer computer = null;
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				computer = computerMapper.map(rs);
				Optional<Company> company = daoFactory.getCompanyDAO().get(computer.getCompanyId());
				if (company.isPresent()) {
					computer.setCompany(company.get());
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(computer);
	}

	public void update(Computer obj) throws DAOException {
		Optional<Computer> computer = this.get(obj.getId());

		if (obj.isValidComputer() && computer.isPresent()) {

			try (Connection conn = this.daoFactory.getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(REQUEST_UPDATE);
				stmt.setString(1, obj.getName());
				stmt.setTimestamp(2, obj.getIntroduced());
				stmt.setTimestamp(3, obj.getDiscontinued());
				if (daoFactory.getCompanyDAO().get(obj.getCompanyId()).isPresent()) {
					stmt.setInt(4, obj.getCompanyId());
				} else {
					stmt.setObject(4, null);
				}
				stmt.setInt(5, obj.getId());
				stmt.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		} else {
			// TODO throw new validator exception
		}
	}

	public void delete(Computer obj) throws DAOException {
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_DELETE);

			stmt.setInt(1, obj.getId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Retrieve all computers.
	 *
	 * @return A list of all computers.
	 * @throws DAOException
	 */
	public List<Computer> getAll() throws DAOException {
		ArrayList<Computer> listComputers = new ArrayList<Computer>();

		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_ALL);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Computer computer = computerMapper.map(rs);
				Optional<Company> company = daoFactory.getCompanyDAO().get(computer.getCompanyId());
				if (company.isPresent()) {
					computer.setCompany(company.get());
				}
				listComputers.add(computer);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return listComputers;
	}

	/**
	 * Retrieve a computer by giving its name.
	 *
	 * @param name The name of the computer.
	 * @return A computer that has the same name
	 * @throws DAOException
	 */
	public Optional<Computer> get(String name) throws DAOException {
		Computer computer = null;
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				computer = computerMapper.map(rs);
				Optional<Company> company = daoFactory.getCompanyDAO().get(computer.getCompanyId());
				if (company.isPresent()) {
					computer.setCompany(company.get());
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(computer);
	}

	public List<Computer> getPattern(String pattern) throws DAOException {
		List<Computer> result = new ArrayList<Computer>();
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_LIKE);
			stmt.setString(1, "%" + pattern + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Computer c = computerMapper.map(rs);
				Optional<Company> company = daoFactory.getCompanyDAO().get(c.getCompanyId());
				if (company.isPresent()) {
					c.setCompany(company.get());
				}
				result.add(c);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result;
	}

	/**
	 * Convert a model to its DTO representation.
	 *
	 * @param computer The computer we want to transform.
	 * @return A computer DTO.
	 */
	public Optional<ComputerDTO> createDTO(Optional<Computer> computer) {
		ComputerDTO cDTO = null;
		if (computer.isPresent()) {
			Computer c = computer.get();
			cDTO = new ComputerDTO();
			cDTO.setId(c.getId());
			cDTO.setName(c.getName());
			cDTO.setIntroducedDate(Utils.timestampToString(c.getIntroduced()));
			cDTO.setDiscontinuedDate(Utils.timestampToString(c.getDiscontinued()));
			if (c.getCompany() != null) {
				cDTO.setCompanyName(c.getCompany().getName());
			}
		}
		return Optional.ofNullable(cDTO);
	}

	/**
	 * Convert a DTO to its model.
	 *
	 * @param cDTO The DTO we want to transform.
	 * @return A Company model.
	 * @throws DAOException
	 */
	public Optional<Computer> createBean(Optional<ComputerDTO> cDTO) throws DAOException {
		Computer computer = null;
		if (cDTO.isPresent()) {
			ComputerDTO c = cDTO.get();
			computer = new Computer();
			computer.setId(c.getId());
			computer.setName(c.getName());
			computer.setIntroduced(Utils.stringToTimestamp(c.getIntroducedDate()));
			computer.setDiscontinued(Utils.stringToTimestamp(c.getDiscontinuedDate()));
			Optional<Company> company = this.daoFactory.getCompanyDAO().get(c.getCompanyName());
			if (company.isPresent()) {
				computer.setCompanyId(company.get().getId());
				computer.setCompany(company.get());
			}
		}
		return Optional.ofNullable(computer);
	}
}
