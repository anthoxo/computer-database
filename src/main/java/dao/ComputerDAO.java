package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
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
	static final String REQUEST_GET_ALL_ORDER_BY = "SELECT id,name,introduced,discontinued,company_id FROM computer ORDER BY ";
	static final String REQUEST_GET_ALL_ORDER_BY_COMPANY_NAME = "SELECT computer.id,computer.name,computer.introduced,computer.discontinued,computer.company_id FROM computer LEFT JOIN company ON company.id = computer.company_id ORDER BY company.name";
	static final String REQUEST_GET_BY_ID = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?";
	static final String REQUEST_GET_BY_NAME = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name = ?";
	static final String REQUEST_GET_LIKE = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name LIKE ?";
	static final String REQUEST_GET_LIKE_ORDER_BY = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name LIKE ? ORDER BY ";
	static final String REQUEST_GET_LIKE_ORDER_BY_COMPANY_NAME = "SELECT computer.id,computer.name,computer.introduced,computer.discontinued,computer.company_id FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.name LIKE ? ORDER BY company.name";

	static final String[] COMPUTER_COLUMN = { "id", "name", "introduced", "discontinued", "company_id" };

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
		TransactionHandler.from((Connection conn, Computer computerArg) -> {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_CREATE);
			stmt.setInt(1, computerArg.getId());
			stmt.setString(2, computerArg.getName());
			stmt.setTimestamp(3, computerArg.getIntroduced());
			stmt.setTimestamp(4, computerArg.getDiscontinued());
			if (daoFactory.getCompanyDAO().get(computerArg.getCompanyId()).isPresent()) {
				stmt.setInt(5, computerArg.getCompanyId());
			} else {
				stmt.setObject(5, null);
			}
			stmt.executeUpdate();
			return Optional.empty();
		}).run(obj);
	}

	public Optional<Computer> get(int id) throws DAOException {
		Optional<Computer> computerOpt = Optional.empty();
		return TransactionHandler.from((Connection conn, Optional<Computer> computerOptArg) -> {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Computer computer = computerMapper.map(rs);
				Optional<Company> companyOpt = daoFactory.getCompanyDAO().get(computer.getCompanyId());
				if (companyOpt.isPresent()) {
					computer.setCompany(companyOpt.get());
				}
				computerOptArg = Optional.ofNullable(computer);
			}
			return computerOptArg;
		}).run(computerOpt).getResult();
	}

	public void update(Computer obj) throws DAOException, ItemNotFoundException {
		Optional<Computer> computerOpt = this.get(obj.getId());
		if (computerOpt.isPresent()) {
			TransactionHandler.from((Connection conn, Computer computerArg) -> {
				PreparedStatement stmt = conn.prepareStatement(REQUEST_UPDATE);
				stmt.setString(1, computerArg.getName());
				stmt.setTimestamp(2, computerArg.getIntroduced());
				stmt.setTimestamp(3, computerArg.getDiscontinued());
				if (daoFactory.getCompanyDAO().get(computerArg.getCompanyId()).isPresent()) {
					stmt.setInt(4, computerArg.getCompanyId());
				} else {
					stmt.setObject(4, null);
				}
				stmt.setInt(5, obj.getId());
				stmt.executeUpdate();
				return Optional.empty();
			}).run(obj);
		} else {
			throw new ItemNotFoundException("update");
		}
	}

	public void delete(Computer obj) throws DAOException {
		TransactionHandler.from((Connection conn, Computer computerArg) -> {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_DELETE);
			stmt.setInt(1, computerArg.getId());
			stmt.executeUpdate();
			return Optional.empty();
		}).run(obj);
	}

	/**
	 * Retrieve all computers.
	 *
	 * @return A list of all computers.
	 * @throws DAOException
	 */
	public List<Computer> getAll() throws DAOException {
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		return TransactionHandler.from((Connection conn, ArrayList<Computer> computerListArg) -> {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_ALL);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Computer computer = computerMapper.map(rs);
				Optional<Company> company = daoFactory.getCompanyDAO().get(computer.getCompanyId());
				if (company.isPresent()) {
					computer.setCompany(company.get());
				}
				computerListArg.add(computer);
			}
			return computerListArg;
		}).run(computerList).getResult();
	}

	public List<Computer> getAllOrderBy(String order, boolean isDesc) throws DAOException {
		TransactionHandler<String, List<Computer>> transactionHandler = TransactionHandler
				.from((Connection conn, String req) -> {
					ArrayList<Computer> computerListArg = new ArrayList<Computer>();
					PreparedStatement stmt = conn.prepareStatement(req);
					ResultSet rs = stmt.executeQuery();
					while (rs.next()) {
						Computer computer = computerMapper.map(rs);
						Optional<Company> company = daoFactory.getCompanyDAO().get(computer.getCompanyId());
						if (company.isPresent()) {
							computer.setCompany(company.get());
						}
						computerListArg.add(computer);
					}
					return computerListArg;
				});
		String desc = isDesc ? " DESC" : "";
		if (Arrays.asList(COMPUTER_COLUMN).contains(order)) {
			return transactionHandler.run(REQUEST_GET_ALL_ORDER_BY + order + desc).getResult();
		} else if (order.equals("companyName")) {
			return transactionHandler.run(REQUEST_GET_ALL_ORDER_BY_COMPANY_NAME + desc).getResult();
		} else {
			return getAll();
		}
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
		return TransactionHandler.from((Connection conn, Optional<Computer> computerOptArg) -> {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Computer computer = computerMapper.map(rs);
				Optional<Company> company = daoFactory.getCompanyDAO().get(computer.getCompanyId());
				if (company.isPresent()) {
					computer.setCompany(company.get());
				}
				computerOptArg = Optional.ofNullable(computer);
			}
			return computerOptArg;
		}).run(computerOpt).getResult();
	}

	public List<Computer> getPattern(String pattern) throws DAOException {
		List<Computer> result = new ArrayList<Computer>();
		return TransactionHandler.from((Connection conn, List<Computer> computerListArg) -> {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_LIKE);
			String patternRequest = new StringBuilder().append("%").append(pattern).append("%").toString();
			stmt.setString(1, patternRequest);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Computer computer = computerMapper.map(rs);
				Optional<Company> companyOpt = daoFactory.getCompanyDAO().get(computer.getCompanyId());
				if (companyOpt.isPresent()) {
					computer.setCompany(companyOpt.get());
				}
				computerListArg.add(computer);
			}
			return computerListArg;
		}).run(result).getResult();
	}

	public List<Computer> getPatternOrderBy(String pattern, String order, boolean isDesc) throws DAOException {
		TransactionHandler<String, List<Computer>> transactionHandler = TransactionHandler
				.from((Connection conn, String request) -> {
					ArrayList<Computer> computerListArg = new ArrayList<Computer>();
					PreparedStatement stmt = conn.prepareStatement(request);
					String patternRequest = new StringBuilder().append("%").append(pattern).append("%").toString();
					stmt.setString(1, patternRequest);
					ResultSet rs = stmt.executeQuery();
					while (rs.next()) {
						Computer computer = computerMapper.map(rs);
						Optional<Company> company = daoFactory.getCompanyDAO().get(computer.getCompanyId());
						if (company.isPresent()) {
							computer.setCompany(company.get());
						}
						computerListArg.add(computer);
					}
					return computerListArg;
				});
		String desc = isDesc ? " DESC" : "";
		if (Arrays.asList(COMPUTER_COLUMN).contains(order)) {
			return transactionHandler.run(REQUEST_GET_LIKE_ORDER_BY + order + desc).getResult();
		} else if (order.equals("companyName")) {
			return transactionHandler.run(REQUEST_GET_LIKE_ORDER_BY_COMPANY_NAME + desc).getResult();
		} else {
			return getPattern(pattern);
		}
	}

}
