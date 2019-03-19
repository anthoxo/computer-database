package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerdatabase.model.Computer;

public class ComputerMapper {

	private static ComputerMapper instance = null;

	/**
	 * Default constructor.
	 */
	private ComputerMapper() {

	}

	/**
	 * @return The only instance of ComputerMapper.
	 */
	public static ComputerMapper getInstance() {
		if (instance == null) {
			instance = new ComputerMapper();
		}
		return instance;
	}

	/**
	 * Map the ResultSet rs into Computer object.
	 *
	 * @param rs The ResultSet
	 * @return New Computer object.
	 * @throws SQLException if there is a problem with SQL connection.
	 */
	public Computer map(ResultSet rs) throws SQLException {
		Computer computer = new Computer();
		computer.setId(rs.getInt("id"));
		computer.setName(rs.getString("name"));
		computer.setIntroduced(rs.getTimestamp("introduced"));
		computer.setDiscontinued(rs.getTimestamp("discontinued"));
		computer.setCompanyId(rs.getInt("company_id"));
		return computer;
	}
}
