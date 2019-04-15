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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import exception.DAOException;
import main.MainConfig;
import model.Company;
import model.Computer;
import utils.RunSQLScript;

public class TestCompanyDAO {

	static AnnotationConfigApplicationContext context;

	CompanyDao companyDao;
	ComputerDao computerDao;

	DataSource dataSourceTest;
	DataSource dataSource;

	@BeforeAll
	public static void setUp() {
		context = new AnnotationConfigApplicationContext(MainConfig.class);
	}

	@BeforeEach
	public void init() throws IOException, DAOException {
		this.companyDao = context.getBean(CompanyDao.class);
		this.computerDao = context.getBean(ComputerDao.class);
		dataSourceTest = (DataSource) context.getBean("dataSourceTest");
		dataSource = this.companyDao.dataSource;
		this.companyDao.setDataSource(dataSourceTest);
		this.computerDao.setDataSource(dataSourceTest);
		RunSQLScript.run(dataSourceTest);
	}

	@AfterEach
	public void stop() {
		this.companyDao.setDataSource(dataSource);
		this.computerDao.setDataSource(dataSource);
	}

	@AfterAll
	public static void shutDown() {
		context.close();
	}

	@Test
	public void testGetById() throws DAOException, SQLException {
		Optional<Company> companyOpt = this.companyDao.get(1);
		if (companyOpt.isPresent()) {
			Company company = companyOpt.get();
			assertEquals(company.getId(), 1);
			assertEquals(company.getName(), "Apple Inc.");
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testGetByName() throws SQLException, DAOException {
		Optional<Company> companyOpt = this.companyDao.get("RCA");
		if (companyOpt.isPresent()) {
			Company company = companyOpt.get();
			assertEquals(company.getId(), 3);
			assertEquals(company.getName(), "RCA");
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testGetAll() throws SQLException, DAOException {
		List<Company> listCompany = this.companyDao.getAll();
		assertEquals(listCompany.size(), 4);
	}

	@Test
	public void testGetAllOrderByName() throws SQLException, DAOException {
		List<String> sortedCompanyList = Arrays.asList("Apple Inc.", "Netronics", "RCA", "Thinking Machines");
		List<Company> listCompany = this.companyDao.getAllOrderByName(false);
		for (int i = 0; i < 4; ++i) {
			assertEquals(sortedCompanyList.get(i), listCompany.get(i).getName());
		}
	}

	@Test
	public void testDeleteCompany() throws SQLException, DAOException, IOException {
		List<Company> companyList;
		List<Computer> computerList = this.computerDao.getAll();
		assertEquals(computerList.size(), 4);
		for (int i = 0; i < 10; ++i) {
			Computer computer = new Computer.Builder().withName("Computer_" + i).withCompanyId(3).build();
			this.computerDao.create(computer);
		}
		computerList = this.computerDao.getAll();
		assertEquals(computerList.size(), 14);
		Company company = new Company.Builder().withId(3).build();
		this.companyDao.delete(company);
		companyList = this.companyDao.getAll();
		computerList = this.computerDao.getAll();
		assertEquals(computerList.size(), 4);
		assertEquals(companyList.size(), 3);
	}
}