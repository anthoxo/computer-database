package controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dao.DAOFactory;
import dto.CompanyDTO;
import model.Page;

@ExtendWith(MockitoExtension.class)
public class TestCompanyController {
	private List<CompanyDTO> listCompanies;

	@Mock
	private Page<CompanyDTO> pageCompanies;

	@InjectMocks
	private CompanyController companyController;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		DAOFactory.getInstance();

		listCompanies = new ArrayList<CompanyDTO>();
		for (int i = 0; i < 40; ++i) {
			CompanyDTO c = new CompanyDTO();
			c.setId(i + 1);
			c.setName("Company_" + String.valueOf(i + 1));
			listCompanies.add(c);
		}

		pageCompanies = new Page<CompanyDTO>(listCompanies);

		companyController = new CompanyController();
		companyController.companyPage = pageCompanies;
	}

	@Test
	public void testSelectActionNext() {
		assertTrue(companyController.selectAction("next"));
	}

	@Test
	public void testSelectActionPrevious() {
		assertTrue(companyController.selectAction("previous"));
	}

	@Test
	public void testSelectActionBack() {
		assertTrue(companyController.selectAction("back"));
		assertTrue(companyController.isGoingBack());

	}

	@Test
	public void testSelectActionBadAction1() {
		assertFalse(companyController.selectAction("bad_action"));
	}

	@Test
	public void testSelectActionBadAction2() {
		assertFalse(companyController.selectAction("kfdblifd"));
	}
}
