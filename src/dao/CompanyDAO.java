package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;

public class CompanyDAO implements DAOInterface<Company> {
	
	private DAOFactory daoFactory = DAOFactory.getInstance();
	private static CompanyDAO INSTANCE = null;
	
	private CompanyDAO() { }
	
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
		try {
			String request = "SELECT * FROM company WHERE id = ?";
			PreparedStatement stmt = this.daoFactory
					.getConnection().prepareStatement(request);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				company = new Company();
				company.setId(id);
				company.setName(rs.getString("name"));
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
		String request = "SELECT * FROM company";
		try {
			PreparedStatement stmt = this.daoFactory
					.getConnection().prepareStatement(request);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				listCompanies.add(company);
			}
		} catch (SQLException e) {
			this.daoFactory.getLogger().error(e.getMessage());
		}
		return listCompanies;
	}
	
	public Company get(String name) {
		Company company = null;
		try {
			String request = "SELECT * FROM company WHERE name = ?";
			PreparedStatement stmt = this.daoFactory
					.getConnection().prepareStatement(request);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			this.daoFactory.getLogger().error(e.getMessage());
		}
		return company;
	}
}
