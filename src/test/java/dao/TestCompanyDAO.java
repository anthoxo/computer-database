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

import exception.DAOException;
import model.Company;
import model.Computer;
import utils.RunSQLScript;

public class TestCompanyDAO {

	CompanyDao companyDAO;
	ComputerDao computerDAO;

	@BeforeAll
	public static void setUp() {
		DaoFactory.startTest();
	}

	@AfterAll
	public static void stop() {
		DaoFactory.stopTest();
	}

	@BeforeEach
	public void init() throws IOException, DAOException {
		RunSQLScript.run();
		this.companyDAO = CompanyDao.getInstance();
		this.computerDAO = ComputerDao.getInstance();
	}

	@Test
	public void testGetById() throws DAOException, SQLException {
		Optional<Company> companyOpt = this.companyDAO.get(1);
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
		Optional<Company> companyOpt = this.companyDAO.get("RCA");
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
		List<Company> listCompany = this.companyDAO.getAll();
		assertEquals(listCompany.size(), 4);
	}

	@Test
	public void testGetAllOrderByName() throws SQLException, DAOException {
		List<String> sortedCompanyList = Arrays.asList("Apple Inc.", "Netronics", "RCA", "Thinking Machines");
		List<Company> listCompany = this.companyDAO.getAllOrderByName(false);
		for (int i = 0 ; i < 4 ; ++i) {
			assertEquals(sortedCompanyList.get(i), listCompany.get(i).getName());
		}
	}


	@Test
	public void testDeleteCompany() throws SQLException, DAOException, IOException {
		List<Company> companyList;
		List<Computer> computerList = this.computerDAO.getAll();
		assertEquals(computerList.size(), 4);
		for (int i = 0 ; i < 10 ; ++i) {
			Computer computer = new Computer.Builder().withName("Computer_" + i).withCompanyId(3).build();
			this.computerDAO.create(computer);
		}
		computerList = this.computerDAO.getAll();
		assertEquals(computerList.size(), 14);
		Company company = new Company.Builder().withId(3).build();
		this.companyDAO.delete(company);
		companyList = this.companyDAO.getAll();
		computerList = this.computerDAO.getAll();
		assertEquals(computerList.size(), 4);
		assertEquals(companyList.size(), 3);
	}
}