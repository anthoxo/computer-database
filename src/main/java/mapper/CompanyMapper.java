package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;

public class CompanyMapper {
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Company map(ResultSet rs) throws SQLException {
		Company company = new Company();
		company.setId(rs.getInt("id"));
		company.setName(rs.getString("name"));
		return company;
	}
}
