package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.CompanyDAO;
import model.Company;

public class CompanyMapper {

	private static CompanyMapper INSTANCE = null;

	private CompanyMapper() {

	}
	
	public static CompanyMapper getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CompanyMapper();
		}
		return INSTANCE;
	}

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public Company map(ResultSet rs) throws SQLException {
		Company company = new Company();
		company.setId(rs.getInt("id"));
		company.setName(rs.getString("name"));
		return company;
	}
}
