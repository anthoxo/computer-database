package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mapper.CompanyMapper;
import model.Company;

public class CompanyDAO implements DAOInterface<Company> {

	private static CompanyDAO INSTANCE = null;

	DAOFactory daoFactory = DAOFactory.getInstance();
	CompanyMapper companyMapper = CompanyMapper.getInstance();

	static final String REQUEST_GET_BY_ID = "SELECT * FROM company WHERE id = ?";
	static final String REQUEST_GET_ALL = "SELECT * FROM company";
	static final String REQUEST_GET_BY_NAME = "SELECT * FROM company WHERE name = ?";

	private CompanyDAO() {
	}

	public static CompanyDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CompanyDAO();
		}
		return INSTANCE;
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
}
