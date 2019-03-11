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
	public void create(Company obj) {
		System.out.println("IMPOSSIBLE TO CREATE NEW COMPANY");
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
			e.printStackTrace();
		}
		return company;
	}
	
	@Override
	public void update(Company obj) {
		System.out.println("IMPOSSIBLE TO UPDATE COMPANIES");
	}

	@Override
	public void delete(Company obj) {
		System.out.println("IMPOSSIBLE TO DELETE COMPANIES");
	}
	
	public List<Company> getAll() {
		List<Company> listCompanies = new ArrayList<Company>();
		String request = "SELECT * FROM company";
		PreparedStatement stmt;
		try {
			stmt = this.daoFactory
					.getConnection().prepareStatement(request);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				listCompanies.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return company;
	}
}
