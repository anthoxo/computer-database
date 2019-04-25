package persistence;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import core.model.Company;

@ExtendWith(SpringExtension.class)
// @ContextConfiguration(classes = {console.MainConfig.class})
@DataJpaTest
public class TestCompanyDAO {

    @Autowired
    private TestEntityManager entityManager;

	@Autowired
	CompanyRepository companyRepository;

	@Test
	public void testGetById() throws SQLException {
		Company company = new Company.Builder().withId(3).withName("Company_5").build();
//		entityManager.persist(company);
//		entityManager.flush();
//
//		Optional<Company> found = companyRepository.findById(company.getId());
//
//		if (found.isPresent()) {
//			assertTrue(company.equals(found.get()));
//		} else {
//			assertTrue(false);
//		}
		System.out.println("BONJOUR !");
	}

//	@Test
//	public void testGetByName() throws SQLException, DAOException {
//		Optional<Company> companyOpt = this.companyDao.get("RCA");
//		if (companyOpt.isPresent()) {
//			Company company = companyOpt.get();
//			assertEquals(company.getId(), 3);
//			assertEquals(company.getName(), "RCA");
//		} else {
//			assertTrue(false);
//		}
//	}
//
//	@Test
//	public void testGetAll() throws SQLException, DAOException {
//		List<Company> listCompany = this.companyDao.getAll();
//		assertEquals(listCompany.size(), 4);
//	}
//
//	@Test
//	public void testGetAllOrderByName() throws SQLException, DAOException {
//		List<String> sortedCompanyList = Arrays.asList("Apple Inc.", "Netronics", "RCA", "Thinking Machines");
//		List<Company> listCompany = this.companyDao.getAllOrderByName(false);
//		for (int i = 0; i < 4; ++i) {
//			assertEquals(sortedCompanyList.get(i), listCompany.get(i).getName());
//		}
//	}
//
//	@Test
//	public void testDeleteCompany() throws SQLException, DAOException, IOException {
//		List<Company> companyList;
//		List<Computer> computerList = this.computerDao.getAll();
//		assertEquals(computerList.size(), 4);
//		for (int i = 0; i < 10; ++i) {
//			Computer computer = new Computer.Builder().withName("Computer_" + i).withCompanyId(3).build();
//			this.computerDao.create(computer);
//		}
//		computerList = this.computerDao.getAll();
//		assertEquals(computerList.size(), 14);
//		Company company = new Company.Builder().withId(3).build();
//		this.companyDao.delete(company);
//		companyList = this.companyDao.getAll();
//		computerList = this.computerDao.getAll();
//		assertEquals(computerList.size(), 4);
//		assertEquals(companyList.size(), 3);
//	}
}