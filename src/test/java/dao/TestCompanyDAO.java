package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.DAOException;
import model.Company;
import utils.RunSQLScript;

public class TestCompanyDAO {

	CompanyDAO companyDAO;

	@BeforeAll
	public static void setUp() throws IOException, DAOException {
		DAOFactory.startTest();
		RunSQLScript.run();
	}

	@AfterAll
	public static void stop() {
		DAOFactory.stopTest();
	}

	@BeforeEach
	public void init() {
		this.companyDAO = CompanyDAO.getInstance();
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
}