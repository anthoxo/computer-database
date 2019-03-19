package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerdatabase.model.Company;

public class CompanyMapper {

	private static CompanyMapper instance = null;

	/**
	 * Default constructor.
	 */
	private CompanyMapper() {

	}

	/**
	 * @return The only instance of CompanyMapper.
	 */
	public static CompanyMapper getInstance() {
		if (instance == null) {
			instance = new CompanyMapper();
		}
		return instance;
	}

	/**
	 * Map the ResultSet rs into Company object.
	 *
	 * @param rs The ResultSet
	 * @return New Company object.
	 * @throws SQLException if there is a problem with SQL connection.
	 */
	public Company map(ResultSet rs) throws SQLException {
		Company company = new Company();
		company.setId(rs.getInt("id"));
		company.setName(rs.getString("name"));
		return company;
	}
}
