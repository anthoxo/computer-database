package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import mapper.ComputerMapper;
import model.Computer;
import utils.Utils;

@ExtendWith(MockitoExtension.class)
public class TestComputerDAO {
	@Mock
	DAOFactory dao;

	@Mock
	Connection connection;

	@Mock
	PreparedStatement stmt;

	@Mock
	ResultSet rs;

	@Mock
	ComputerMapper computerMapper;

	@Mock
	CompanyDAO companyDAO;

	@InjectMocks
	ComputerDAO computerDAO;

	List<Computer> listComputers;

	@BeforeEach
	public void init() throws SQLException, NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		this.computerDAO = ComputerDAO.getInstance();

		listComputers = new ArrayList<Computer>();
		for (int i = 0; i < 3; ++i) {
			Computer computer = new Computer();
			computer.setId(i + 1);
			computer.setName("Computer_" + String.valueOf(i + 1));
			listComputers.add(computer);
		}

		Field field = ComputerDAO.class.getDeclaredField("daoFactory");
		field.setAccessible(true);
		field.set(computerDAO, dao);

		field = ComputerDAO.class.getDeclaredField("computerMapper");
		field.setAccessible(true);
		field.set(computerDAO, computerMapper);
	}

	public void initGoodRequest() throws SQLException {
		Mockito.when(dao.getCompanyDAO()).thenReturn(companyDAO);
		Mockito.when(dao.getConnection()).thenReturn(connection);
	}

	public void initGet() throws SQLException {
		initGoodRequest();
		Mockito.when(stmt.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE)
				.thenReturn(Boolean.FALSE);
		Mockito.doReturn(listComputers.get(0)).when(this.computerDAO.computerMapper).map(rs);
	}

	public void initUpdate() throws SQLException {
		initGet();
		Mockito.when(connection.prepareStatement(ComputerDAO.REQUEST_GET_BY_ID)).thenReturn(stmt);
	}

	public void initDelete() throws SQLException {
		Mockito.when(dao.getConnection()).thenReturn(connection);
		Mockito.when(connection.prepareStatement(ComputerDAO.REQUEST_DELETE)).thenReturn(stmt);
	}

	@Test
	public void testGetById() throws SQLException {
		initGet();
		Mockito.when(connection.prepareStatement(ComputerDAO.REQUEST_GET_BY_ID)).thenReturn(stmt);
		Computer computer = this.computerDAO.get(0);
		assertEquals(computer.getId(), 1);
		assertEquals(computer.getName(), "Computer_1");
	}

	@Test
	public void testGetByName() throws SQLException {
		initGet();
		Mockito.when(connection.prepareStatement(ComputerDAO.REQUEST_GET_BY_NAME)).thenReturn(stmt);
		Computer computer = this.computerDAO.get("Computer_1");
		assertEquals(computer.getId(), 1);
		assertEquals(computer.getName(), "Computer_1");
	}

	@Test
	public void testGetAll() throws SQLException {
		initGet();
		Mockito.when(connection.prepareStatement(ComputerDAO.REQUEST_GET_ALL)).thenReturn(stmt);
		List<Computer> list = this.computerDAO.getAll();
		assertEquals(list.size(), listComputers.size());
	}

	@Test
	public void testCreate() throws SQLException {
		initGoodRequest();
		Mockito.when(connection.prepareStatement(ComputerDAO.REQUEST_CREATE)).thenReturn(stmt);
		Computer computer = listComputers.get(0);
		boolean t = this.computerDAO.create(computer);
		assertTrue(t);
	}

	@Test
	public void testCreateBad() throws SQLException {
		Computer computer = new Computer();
		computer.setId(1);
		computer.setName("Computer");
		computer.setIntroduced(Utils.computeTimestamp("2000/01/01"));
		computer.setDiscontinued(Utils.computeTimestamp("1999/01/01"));
		computer.setCompanyId(1);
		boolean t = this.computerDAO.create(computer);
		assertTrue(t == false);
	}

	@Test
	public void testUpdate() throws SQLException {
		initUpdate();
		Mockito.doReturn(stmt).when(connection).prepareStatement(ComputerDAO.REQUEST_UPDATE);
		Computer computer = new Computer();
		computer.setId(1);
		computer.setName("Computer");
		computer.setIntroduced(Utils.computeTimestamp("1999/01/01"));
		computer.setDiscontinued(Utils.computeTimestamp("2000/01/01"));
		computer.setCompanyId(1);
		boolean t = this.computerDAO.update(computer);
		assertTrue(t);
	}

	@Test
	public void testUpdateBad() throws SQLException {
		initUpdate();
		Computer computer = new Computer();
		computer.setId(1);
		computer.setName("Computer");
		computer.setIntroduced(Utils.computeTimestamp("2000/01/01"));
		computer.setDiscontinued(Utils.computeTimestamp("1999/01/01"));
		computer.setCompanyId(1);
		boolean t = this.computerDAO.update(computer);
		assertTrue(t == false);
	}

	@Test
	public void testDelete() throws SQLException {
		initDelete();
		Computer computer = new Computer();
		computer.setId(1);
		computer.setName("Computer");
		computer.setIntroduced(Utils.computeTimestamp("2000/01/01"));
		computer.setDiscontinued(Utils.computeTimestamp("1999/01/01"));
		computer.setCompanyId(1);
		boolean t = this.computerDAO.delete(computer);
		assertTrue(t);
	}
}
