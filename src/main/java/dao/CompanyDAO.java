package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dto.CompanyDTO;
import exception.DAOException;
import mapper.CompanyMapper;
import model.Company;

public class CompanyDAO {

	private static CompanyDAO instance = null;

	DAOFactory daoFactory = DAOFactory.getInstance();
	CompanyMapper companyMapper = CompanyMapper.getInstance();

	static final String REQUEST_GET_BY_ID = "SELECT id,name FROM company WHERE id = ?";
	static final String REQUEST_GET_ALL = "SELECT id,name FROM company";
	static final String REQUEST_GET_BY_NAME = "SELECT id,name FROM company WHERE name = ?";

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
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				company = Optional.ofNullable(companyMapper.map(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return company;
	}

	/**
	 * Retrieve all companies.
	 *
	 * @return The list of all companies.
	 * @throws DAOException
	 */
	public List<Company> getAll() throws DAOException {

		List<Company> listCompanies = new ArrayList<Company>();

		try (Connection conn = this.daoFactory.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_ALL);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Company company = companyMapper.map(rs);
				listCompanies.add(company);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return listCompanies;
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
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				company = Optional.ofNullable(companyMapper.map(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return company;
	}

	/**
	 * Convert a model to its DTO representation.
	 *
	 * @param company The company we want to transform.
	 * @return A company DTO.
	 */
	public CompanyDTO createDTO(Company company) {
		CompanyDTO cDTO = new CompanyDTO();
		cDTO.setId(company.getId());
		cDTO.setName(company.getName());
		return cDTO;
	}

	/**
	 * Convert a DTO to its model.
	 *
	 * @param cDTO The DTO we want to transform.
	 * @return A Company model.
	 */
	public Company createBean(CompanyDTO cDTO) {
		Company company = new Company();
		company.setId(cDTO.getId());
		company.setName(cDTO.getName());
		return company;
	}
}
