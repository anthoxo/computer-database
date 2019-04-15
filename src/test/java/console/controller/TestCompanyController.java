package console.controller;

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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import console.controller.CompanyController;
import dto.CompanyDTO;
import main.MainConfig;
import model.Page;

@ExtendWith(MockitoExtension.class)
public class TestCompanyController {
	private List<CompanyDTO> companyList;

	@Mock
	private Page<CompanyDTO> companyPage;

	@InjectMocks
	private CompanyController companyController;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

		companyList = new ArrayList<CompanyDTO>();
		for (int i = 0; i < 40; ++i) {
			CompanyDTO companyDTO = new CompanyDTO();
			companyDTO.setId(i + 1);
			companyDTO.setName("Company_" + String.valueOf(i + 1));
			companyList.add(companyDTO);
		}

		companyPage = new Page<CompanyDTO>(companyList);

		companyController = context.getBean(CompanyController.class);
		companyController.companyPage = companyPage;
		context.close();
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
