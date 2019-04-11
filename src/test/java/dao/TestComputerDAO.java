package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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

	static AnnotationConfigApplicationContext context;

	ComputerDao computerDao;

	DataSource dataSourceTest;
	DataSource dataSource;

	@BeforeAll
	public static void setUp() throws IOException, DAOException {
		context = new AnnotationConfigApplicationContext(MainConfig.class);
	}

	@AfterAll
	public static void shutDown() {
		context.close();
	}

	@BeforeEach
	public void init() throws IOException, DAOException {
		this.computerDao = context.getBean(ComputerDao.class);
		dataSourceTest = (DataSource)context.getBean("dataSourceTest");
		dataSource = this.computerDao.dataSource;
		this.computerDao.setDataSource(dataSourceTest);
		RunSQLScript.run(dataSourceTest);
	}

	@AfterEach
	public void stop() {
		this.computerDao.setDataSource(dataSource);
	}


	@Test
	public void testGetById() throws SQLException, DAOException {
		Optional<Computer> computerOpt = this.computerDao.get(1);
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
		Optional<Computer> computerOpt = this.computerDao.get("CM-2a");
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
		List<Computer> list = this.computerDao.getAll();
		assertEquals(list.size(), 4);
	}

	@Test
	public void testGetAllOrderByName() throws SQLException, DAOException {
		List<String> l = Arrays.asList("CM-200", "CM-2a", "CM-5e", "MacBook Pro 15.4 inch");
		List<Computer> list = this.computerDao.getAllOrderBy("name", false);
		for (int i = 0; i < 4; ++i) {
			assertEquals(l.get(i), list.get(i).getName());
		}
	}

	@Test
	public void testGetPattern() throws SQLException, DAOException {
		List<String> l = Arrays.asList("CM-2a", "CM-200");
		List<Computer> list = this.computerDao.getPattern("-2");
		for (int i = 0; i < l.size(); ++i) {
			assertEquals(l.get(i), list.get(i).getName());
		}
	}

	@Test
	public void testGetPatternOrderBy() throws SQLException, DAOException {
		List<String> l = Arrays.asList("CM-200", "CM-2a");
		List<Computer> list = this.computerDao.getPatternOrderBy("-2", "name", false);
		for (int i = 0; i < l.size(); ++i) {
			assertEquals(l.get(i), list.get(i).getName());
		}
	}

	@Test
	public void testCreateAndDelete() throws SQLException {
		try {
			Computer computer = new Computer.Builder().withName("Computer_test").build();
			this.computerDao.create(computer);
			List<Computer> list = this.computerDao.getAll();
			assertEquals(list.size(), 5);
			this.computerDao.delete(this.computerDao.get("Computer_test").get());
			list = this.computerDao.getAll();
			assertEquals(list.size(), 4);
		} catch (DAOException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testCreateAndUpdate() throws SQLException, ItemNotFoundException {
		try {
			Computer computer = new Computer.Builder().withName("Computer_test").build();
			this.computerDao.create(computer);
			List<Computer> list = this.computerDao.getAll();
			assertEquals(list.size(), 5);
			computer = this.computerDao.get("Computer_test").get();
			computer.setIntroduced(Utils.stringToTimestamp("1999/01/01").get());
			computer.setDiscontinued(Utils.stringToTimestamp("2000/01/01").get());
			this.computerDao.update(computer);
			computer = this.computerDao.get("Computer_test").get();
			assertEquals(computer.getName(), "Computer_test");
			assertEquals(computer.getIntroduced(), Utils.stringToTimestamp("1999/01/01").get());
			assertEquals(computer.getDiscontinued(), Utils.stringToTimestamp("2000/01/01").get());
			this.computerDao.delete(this.computerDao.get("Computer_test").get());
			list = this.computerDao.getAll();
			assertEquals(list.size(), 4);
		} catch (DAOException e) {
			assertTrue(false);
		}
	}
}
