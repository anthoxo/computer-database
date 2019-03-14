package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Computer;

public class ComputerMapper {
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Computer map(ResultSet rs) throws SQLException {
		Computer computer = new Computer();
		computer.setId(rs.getInt("id"));
		computer.setName(rs.getString("name"));
		computer.setIntroduced(rs.getTimestamp("introduced"));
		computer.setDiscontinued(rs.getTimestamp("discontinued"));
		computer.setCompanyId(rs.getInt("company_id"));
		return computer;
	}
}
