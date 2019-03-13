package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.Computer;

public class ComputerDAO implements DAOInterface<Computer> {
	
	private DAOFactory daoFactory = DAOFactory.getInstance();
	private static ComputerDAO INSTANCE = null;
	
	private ComputerDAO() { }
	
	public static ComputerDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ComputerDAO();
		}
		return INSTANCE;
	}

	@Override
	public boolean create(Computer obj) {
		boolean validComputer = true;
		
		if (obj.getIntroduced() != null && obj.getDiscontinued() != null) {
			if (obj.getDiscontinued().getTime() - obj.getIntroduced().getTime() < 0) {
				validComputer = false;
			}
		}
		
		if (validComputer) {
			String request = "INSERT INTO computer VALUES (?,?,?,?,?)";
			try {
				PreparedStatement stmt = this.daoFactory
						.getConnection().prepareStatement(request);
				
				stmt.setInt(1, obj.getId());
				stmt.setString(2, obj.getName());
				stmt.setTimestamp(3, obj.getIntroduced());
				stmt.setTimestamp(4, obj.getDiscontinued());
				
				if (daoFactory.getCompanyDAO().get(obj.getCompanyId()) == null) {
					stmt.setObject(5, null);
				} else {
					stmt.setInt(5, obj.getCompanyId());
				}

				stmt.executeUpdate();
			} catch (SQLException e) {
				this.daoFactory.getLogger().error(e.getMessage());
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Computer get(int id) {
		Computer computer = null;
		try {
			String request = "SELECT * FROM computer WHERE id = ?";
			PreparedStatement stmt = this.daoFactory
					.getConnection().prepareStatement(request);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				computer = new Computer();
				computer.setId(id);
				computer.setName(rs.getString("name"));
				computer.setIntroduced(rs.getTimestamp("introduced"));
				computer.setDiscontinued(rs.getTimestamp("discontinued"));
				computer.setCompanyId(rs.getInt("company_id"));
				computer.setCompany(daoFactory.getCompanyDAO().get(computer.getCompanyId()));
			}
		} catch (SQLException e) {
			this.daoFactory.getLogger().error(e.getMessage());
		}
		return computer;
	}

	@Override
	public boolean update(Computer obj) {
		
		Computer computer = this.get(obj.getId());
		Company company = daoFactory.getCompanyDAO().get(computer.getCompanyId());
		boolean validComputer = true;
		
		if (computer == null || company == null) {
			validComputer = false;
		}
		if (obj.getIntroduced() != null && obj.getDiscontinued() != null) {
			if (obj.getDiscontinued().getTime() - obj.getIntroduced().getTime() < 0) {
				validComputer = false;
			}
		}
		
		if (validComputer) {
			String request = "UPDATE computer "
					+ "SET name = ?, "
					+ "introduced = ?, "
					+ "discontinued = ?, "
					+ "company_id = ? "
					+ "WHERE id = ?";
			try {
				PreparedStatement stmt = this.daoFactory
						.getConnection().prepareStatement(request);
				
				stmt.setString(1, obj.getName());
				stmt.setTimestamp(2, obj.getIntroduced());
				stmt.setTimestamp(3, obj.getDiscontinued());
				stmt.setInt(4, obj.getCompanyId());
				stmt.setInt(5, obj.getId());

				stmt.executeUpdate();
			} catch (SQLException e) {
				this.daoFactory.getLogger().error(e.getMessage());
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(Computer obj) {
		String request = "DELETE FROM computer WHERE id = ?";
		try {
			PreparedStatement stmt = this.daoFactory
					.getConnection().prepareStatement(request);
			
			stmt.setInt(1, obj.getId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			this.daoFactory.getLogger().error(e.getMessage());
			return false;
		}
		return true;
	}
	
	public List<Computer> getAll() {
		ArrayList<Computer> listComputers = new ArrayList<Computer>();
		
		String request = "SELECT * FROM computer";
		try {
			PreparedStatement stmt = this.daoFactory
					.getConnection().prepareStatement(request);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Computer computer = new Computer();
				computer.setId(rs.getInt("id"));
				computer.setName(rs.getString("name"));
				computer.setIntroduced(rs.getTimestamp("introduced"));
				computer.setDiscontinued(rs.getTimestamp("discontinued"));
				computer.setCompanyId(rs.getInt("company_id"));
				computer.setCompany(daoFactory.getCompanyDAO().get(computer.getCompanyId()));
				listComputers.add(computer);
			}
		} catch (SQLException e) {
			this.daoFactory.getLogger().error(e.getMessage());
		}

		return listComputers;
	}
	
	public Computer get(String name) {
		Computer computer = null;
		try {
			String request = "SELECT * FROM computer WHERE name = ?";
			PreparedStatement stmt = this.daoFactory
					.getConnection().prepareStatement(request);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				computer = new Computer();
				computer.setId(rs.getInt("id"));
				computer.setName(rs.getString("name"));
				computer.setIntroduced(rs.getTimestamp("introduced"));
				computer.setDiscontinued(rs.getTimestamp("discontinued"));
				computer.setCompanyId(rs.getInt("company_id"));
				computer.setCompany(daoFactory.getCompanyDAO().get(computer.getCompanyId()));
			}
		} catch (SQLException e) {
			this.daoFactory.getLogger().error(e.getMessage());
		}
		return computer;
	}


}
