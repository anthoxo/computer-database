package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Company;
import persistence.ConnexionDB;

public class CompanyDAO implements DAOInterface<Company> {
	
	private ConnexionDB connexionDB;
	private static CompanyDAO INSTANCE = null;
	
	private CompanyDAO() {
		this.connexionDB = ConnexionDB.getInstance();
	}
	
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
			PreparedStatement stmt = this.connexionDB
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

}
