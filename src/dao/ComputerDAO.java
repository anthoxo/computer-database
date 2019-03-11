package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;
import model.Computer;
import persistence.ConnexionDB;

public class ComputerDAO implements DAOInterface<Computer> {
	
	private ConnexionDB connexionDB;
	private static ComputerDAO INSTANCE = null;
	
	private ComputerDAO() {
		this.connexionDB = ConnexionDB.getInstance();
	}
	
	public static ComputerDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ComputerDAO();
		}
		return INSTANCE;
	}

	@Override
	public void create(Computer obj) {
		String request = "INSERT INTO computer VALUES (?,?,?,?,?)";
		try {
			PreparedStatement stmt = this.connexionDB
					.getConnection().prepareStatement(request);
			
			stmt.setInt(1, obj.getId());
			stmt.setString(2, obj.getName());
			stmt.setDate(3, obj.getIntroduced());
			stmt.setDate(4, obj.getDiscontinued());
			stmt.setInt(5, obj.getCompanyId());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Computer get(int id) {
		Computer computer = null;
		try {
			String request = "SELECT * FROM computer WHERE id = ?";
			PreparedStatement stmt = this.connexionDB
					.getConnection().prepareStatement(request);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				computer = new Computer();
				computer.setId(id);
				computer.setName(rs.getString("name"));
				computer.setIntroduced(rs.getDate("introduced"));
				computer.setDiscontinued(rs.getDate("discontinued"));
				computer.setCompanyId(rs.getInt("company_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	@Override
	public void update(Computer obj) {
		String request = "UPDATE computer "
				+ "SET name = ?, "
				+ "introduced = ?, "
				+ "discontinued = ?, "
				+ "company_id = ? "
				+ "WHERE id = ?";
		try {
			PreparedStatement stmt = this.connexionDB
					.getConnection().prepareStatement(request);
			
			stmt.setString(1, obj.getName());
			stmt.setDate(2, obj.getIntroduced());
			stmt.setDate(3, obj.getDiscontinued());
			stmt.setInt(4, obj.getCompanyId());
			stmt.setInt(5, obj.getId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Computer obj) {
		String request = "DELETE FROM computer WHERE id = ?";
		try {
			PreparedStatement stmt = this.connexionDB
					.getConnection().prepareStatement(request);
			
			stmt.setInt(1, obj.getId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
