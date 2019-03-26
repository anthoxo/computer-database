package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exception.DAOException;
import exception.ItemNotFoundException;
import mapper.ComputerMapper;
import model.Company;
import model.Computer;

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
	static final String REQUEST_GET_LIKE = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name LIKE ?";

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
	}

	public Optional<Computer> get(int id) throws DAOException {
		Optional<Computer> computerOpt = Optional.empty();
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Computer computer = computerMapper.map(rs);
				Optional<Company> company = daoFactory.getCompanyDAO().get(computer.getCompanyId());
				if (company.isPresent()) {
					computer.setCompany(company.get());
				}
				computerOpt = Optional.ofNullable(computer);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return computerOpt;
	}

	public void update(Computer obj) throws DAOException, ItemNotFoundException {
		Optional<Computer> computerOpt = this.get(obj.getId());
		if (computerOpt.isPresent()) {
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
			throw new ItemNotFoundException("update");
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
		Optional<Computer> computerOpt = Optional.empty();
		try (Connection conn = this.daoFactory.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Computer computer = computerMapper.map(rs);
				Optional<Company> company = daoFactory.getCompanyDAO().get(computer.getCompanyId());
				if (company.isPresent()) {
					computer.setCompany(company.get());
				}
				computerOpt = Optional.ofNullable(computer);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return computerOpt;
	}

	public List<Computer> getPattern(String pattern) throws DAOException {
		List<Computer> result = new ArrayList<Computer>();
		String patternRequest = new StringBuilder().append("%").append(pattern).append("%").toString();
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_LIKE);
			stmt.setString(1, patternRequest);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Computer computer = computerMapper.map(rs);
				Optional<Company> companyOpt = daoFactory.getCompanyDAO().get(computer.getCompanyId());
				if (companyOpt.isPresent()) {
					computer.setCompany(companyOpt.get());
				}
				result.add(computer);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result;
	}
}
