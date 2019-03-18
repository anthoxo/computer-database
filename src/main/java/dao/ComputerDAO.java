package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mapper.ComputerMapper;
import model.Computer;

public class ComputerDAO implements DAOInterface<Computer> {

	private static ComputerDAO INSTANCE = null;

	DAOFactory daoFactory = DAOFactory.getInstance();
	ComputerMapper computerMapper = ComputerMapper.getInstance();

	static final String REQUEST_CREATE = "INSERT INTO computer VALUES (?,?,?,?,?)";
	static final String REQUEST_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	static final String REQUEST_DELETE = "DELETE FROM computer WHERE id = ?";
	static final String REQUEST_GET_ALL = "SELECT * FROM computer";
	static final String REQUEST_GET_BY_ID = "SELECT * FROM computer WHERE id = ?";
	static final String REQUEST_GET_BY_NAME = "SELECT * FROM computer WHERE name = ?";

	private ComputerDAO() {
	}

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
			try (Connection conn = this.daoFactory.getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(REQUEST_CREATE);

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
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				computer = computerMapper.map(rs);
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
		boolean validComputer = true;

		if (computer == null) {
			validComputer = false;
		}
		if (obj.getIntroduced() != null && obj.getDiscontinued() != null) {
			if (obj.getDiscontinued().getTime() - obj.getIntroduced().getTime() < 0) {
				validComputer = false;
			}
		}

		if (validComputer) {
			try (Connection conn = this.daoFactory.getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(REQUEST_UPDATE);

				stmt.setString(1, obj.getName());
				stmt.setTimestamp(2, obj.getIntroduced());
				stmt.setTimestamp(3, obj.getDiscontinued());

				if (daoFactory.getCompanyDAO().get(obj.getCompanyId()) == null) {
					stmt.setObject(4, null);
				} else {
					stmt.setInt(4, obj.getCompanyId());
				}

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
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_DELETE);

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

		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_ALL);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Computer computer = computerMapper.map(rs);
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
		try (Connection conn = this.daoFactory.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(REQUEST_GET_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				computer = computerMapper.map(rs);
				computer.setCompany(daoFactory.getCompanyDAO().get(computer.getCompanyId()));
			}
		} catch (SQLException e) {
			this.daoFactory.getLogger().error(e.getMessage());
		}
		return computer;
	}

}
