package controller;

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
		boolean result = companyController.selectAction("next");
		assertTrue(result);
	}

	@Test
	public void testSelectActionPrevious() {
		boolean result = companyController.selectAction("previous");
		assertTrue(result);
	}

	@Test
	public void testSelectActionBack() {
		boolean result = companyController.selectAction("back");
		assertTrue(result);
		assertTrue(companyController.isGoingBack());

	}

	@Test
	public void testSelectActionBadAction1() {
		boolean result = companyController.selectAction("bad_action");
		assertTrue(result == false);
	}

	@Test
	public void testSelectActionBadAction2() {
		boolean result = companyController.selectAction("kfdblifd");
		assertTrue(result == false);
	}
}
