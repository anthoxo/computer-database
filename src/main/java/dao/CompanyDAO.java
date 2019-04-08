package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exception.DAOException;
import mapper.CompanyMapper;
import model.Company;

public class CompanyDAO {

	private static CompanyDAO instance = null;

	CompanyMapper companyMapper = CompanyMapper.getInstance();

	static final String REQUEST_GET_BY_ID = "SELECT id,name FROM company WHERE id = ?";
	static final String REQUEST_GET_ALL = "SELECT id,name FROM company";
	static final String REQUEST_GET_ALL_ORDER_BY_NAME = "SELECT id,name FROM company ORDER BY name";
	static final String REQUEST_GET_BY_NAME = "SELECT id,name FROM company WHERE name = ?";
	static final String REQUEST_DELETE_COMPUTER_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id = ?";
	static final String REQUEST_DELETE_COMPANY_BY_ID = "DELETE FROM company WHERE id = ?";

	/**
	 * Default constructor.
	 */
	private CompanyDAO() {
	}

	/**
	 * Method to retrieve instance of Company DAO.
	 *
	 * @return The only instance of CompanyDAO.
	 */
	public static CompanyDAO getInstance() {
		if (instance == null) {
			instance = new CompanyDAO();
		}
		return instance;
	}

	public Optional<Company> get(int id) throws DAOException {
		Optional<Company> company = Optional.empty();
		return TransactionHandler.create((Connection conn, Optional<Company> companyArg) -> {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				companyArg = Optional.ofNullable(companyMapper.map(rs));
			}
			return companyArg;
		}).run(company).getResult();
	}

	/**
	 * Retrieve all companies.
	 *
	 * @return The list of all companies.
	 * @throws DAOException
	 */
	public List<Company> getAll() throws DAOException {
		List<Company> listCompanies = new ArrayList<Company>();
		return TransactionHandler.create((Connection conn, List<Company> l) -> {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_ALL);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Company company = companyMapper.map(rs);
				l.add(company);
			}
			return l;
		}).run(listCompanies).getResult();
	}

	public List<Company> getAllOrderByName(boolean isDesc) throws DAOException {
		TransactionHandler<String, List<Company>> transactionHandler = TransactionHandler.create((Connection conn, String request) -> {
			List<Company> listCompanies = new ArrayList<Company>();
			PreparedStatement stmt = conn.prepareStatement(request);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Company company = companyMapper.map(rs);
				listCompanies.add(company);
			}
			return listCompanies;
		});
		if (isDesc) {
			return transactionHandler.run(REQUEST_GET_ALL_ORDER_BY_NAME + " DESC").getResult();
		} else {
			return transactionHandler.run(REQUEST_GET_ALL_ORDER_BY_NAME).getResult();
		}
	}

	/**
	 * Retrieve a company by giving its name.
	 *
	 * @param name The name (in String) of the company.
	 * @return The company object.
	 * @throws DAOException
	 */
	public Optional<Company> get(String name) throws DAOException {
		Optional<Company> company = Optional.empty();
		return TransactionHandler.create((Connection conn, Optional<Company> companyOpt) -> {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				companyOpt = Optional.ofNullable(companyMapper.map(rs));
			}
			return companyOpt;
		}).run(company).getResult();
	}

	public void delete(Company obj) throws DAOException {
		TransactionHandler.create((Connection conn, Company company) -> {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_DELETE_COMPUTER_BY_COMPANY_ID);
			stmt.setInt(1, company.getId());
			stmt.executeUpdate();
			stmt = conn.prepareStatement(REQUEST_DELETE_COMPANY_BY_ID);
			stmt.setInt(1, company.getId());
			stmt.executeUpdate();
			return Optional.empty();
		}).run(obj);
	}
}
