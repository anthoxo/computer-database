package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import exception.DAOException;
import exception.ItemNotFoundException;
import main.MainConfig;
import model.Computer;
import utils.RunSQLScript;
import utils.Utils;

@ExtendWith(MockitoExtension.class)
public class TestComputerDAO {

	ComputerDao computerDAO;

	static DaoFactory daoFactory;

	@BeforeAll
	public static void setUp() throws IOException, DAOException {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
		daoFactory = context.getBean(DaoFactory.class);
		daoFactory.startTest();
		context.close();
	}

	@AfterAll
	public static void stop() {
		daoFactory.stopTest();
	}

	@BeforeEach
	public void init() throws IOException, DAOException {
		RunSQLScript.run(daoFactory);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
		this.computerDAO = context.getBean(ComputerDao.class);
		context.close();
	}

	@Test
	public void testGetById() throws SQLException, DAOException {
		Optional<Computer> computerOpt = this.computerDAO.get(1);
		if (computerOpt.isPresent()) {
			Computer computer = computerOpt.get();
			assertEquals(computer.getId(), 1);
			assertEquals(computer.getName(), "MacBook Pro 15.4 inch");
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testGetByName() throws SQLException, DAOException {
		Optional<Computer> computerOpt = this.computerDAO.get("CM-2a");
		if (computerOpt.isPresent()) {
			Computer computer = computerOpt.get();
			assertEquals(computer.getId(), 2);
			assertEquals(computer.getName(), "CM-2a");
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testGetAll() throws SQLException, DAOException {
		List<Computer> list = this.computerDAO.getAll();
		assertEquals(list.size(), 4);
	}

	@Test
	public void testGetAllOrderByName() throws SQLException, DAOException {
		List<String> l = Arrays.asList("CM-200", "CM-2a", "CM-5e", "MacBook Pro 15.4 inch");
		List<Computer> list = this.computerDAO.getAllOrderBy("name", false);
		for (int i = 0; i < 4; ++i) {
			assertEquals(l.get(i), list.get(i).getName());
		}
	}

	@Test
	public void testGetPattern() throws SQLException, DAOException {
		List<String> l = Arrays.asList("CM-2a", "CM-200");
		List<Computer> list = this.computerDAO.getPattern("-2");
		for (int i = 0; i < l.size(); ++i) {
			assertEquals(l.get(i), list.get(i).getName());
		}
	}

	@Test
	public void testGetPatternOrderBy() throws SQLException, DAOException {
		List<String> l = Arrays.asList("CM-200", "CM-2a");
		List<Computer> list = this.computerDAO.getPatternOrderBy("-2", "name", false);
		for (int i = 0; i < l.size(); ++i) {
			assertEquals(l.get(i), list.get(i).getName());
		}
	}

	@Test
	public void testCreateAndDelete() throws SQLException {
		try {
			Computer computer = new Computer.Builder().withName("Computer_test").build();
			this.computerDAO.create(computer);
			List<Computer> list = this.computerDAO.getAll();
			assertEquals(list.size(), 5);
			this.computerDAO.delete(this.computerDAO.get("Computer_test").get());
			list = this.computerDAO.getAll();
			assertEquals(list.size(), 4);
		} catch (DAOException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testCreateAndUpdate() throws SQLException, ItemNotFoundException {
		try {
			Computer computer = new Computer.Builder().withName("Computer_test").build();
			this.computerDAO.create(computer);
			List<Computer> list = this.computerDAO.getAll();
			assertEquals(list.size(), 5);
			computer = this.computerDAO.get("Computer_test").get();
			computer.setIntroduced(Utils.stringToTimestamp("1999/01/01").get());
			computer.setDiscontinued(Utils.stringToTimestamp("2000/01/01").get());
			this.computerDAO.update(computer);
			computer = this.computerDAO.get("Computer_test").get();
			assertEquals(computer.getName(), "Computer_test");
			assertEquals(computer.getIntroduced(), Utils.stringToTimestamp("1999/01/01").get());
			assertEquals(computer.getDiscontinued(), Utils.stringToTimestamp("2000/01/01").get());
			this.computerDAO.delete(this.computerDAO.get("Computer_test").get());
			list = this.computerDAO.getAll();
			assertEquals(list.size(), 4);
		} catch (DAOException e) {
			assertTrue(false);
		}
	}
}
