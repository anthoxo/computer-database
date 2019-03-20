package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CompanyDTO;
import mapper.CompanyMapper;
import model.Company;

public class CompanyDAO implements DAOInterface<Company> {

	private static CompanyDAO instance = null;

	DAOFactory daoFactory = DAOFactory.getInstance();
	CompanyMapper companyMapper = CompanyMapper.getInstance();

	static final String REQUEST_GET_BY_ID = "SELECT * FROM company WHERE id = ?";
	static final String REQUEST_GET_ALL = "SELECT * FROM company";
	static final String REQUEST_GET_BY_NAME = "SELECT * FROM company WHERE name = ?";

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

	@Override
	public boolean create(Company obj) {
		daoFactory.getLogger().warn("IMPOSSIBLE TO CREATE NEW COMPANY");
		return false;
	}

	@Override
	public Company get(int id) {
		Company company = null;
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				company = companyMapper.map(rs);
			}
		} catch (SQLException e) {
			this.daoFactory.getLogger().error(e.getMessage());
		}
		return company;
	}

	@Override
	public boolean update(Company obj) {
		this.daoFactory.getLogger().warn("IMPOSSIBLE TO UPDATE COMPANIES");
		return false;
	}

	@Override
	public boolean delete(Company obj) {
		this.daoFactory.getLogger().warn("IMPOSSIBLE TO DELETE COMPANIES");
		return false;
	}

	/**
	 * Retrieve all companies.
	 *
	 * @return The list of all companies.
	 */
	public List<Company> getAll() {

		List<Company> listCompanies = new ArrayList<Company>();

		try (Connection conn = this.daoFactory.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_ALL);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Company company = companyMapper.map(rs);
				listCompanies.add(company);
			}

		} catch (SQLException e) {
			this.daoFactory.getLogger().error(e.getMessage());
		}
		return listCompanies;
	}

	/**
	 * Retrieve a company by giving its name.
	 *
	 * @param name The name (in String) of the company.
	 * @return The company object.
	 */
	public Company get(String name) {
		Company company = null;
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				company = companyMapper.map(rs);
			}
		} catch (SQLException e) {
			this.daoFactory.getLogger().error(e.getMessage());
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
		if (company == null) {
			return null;
		} else {
			CompanyDTO cDTO = new CompanyDTO();
			cDTO.setId(company.getId());
			cDTO.setName(company.getName());
			return cDTO;
		}
	}

	/**
	 * Convert a DTO to its model
	 *
	 * @param cDTO The DTO we want to transform.
	 * @return A Company model.
	 */
	public Company createBean(CompanyDTO cDTO) {
		if (cDTO == null) {
			return null;
		} else {
			Company company = new Company();
			company.setId(cDTO.getId());
			company.setName(cDTO.getName());
			return company;
		}
	}
}
