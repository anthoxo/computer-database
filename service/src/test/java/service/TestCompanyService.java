package service;

import java.lang.reflect.Field;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import core.model.Company;
import core.util.Utils.OrderByOption;
import persistence.CompanyRepository;
import persistence.exception.DAOException;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TestCompanyService {

	@Mock
	CompanyRepository companyRepository;

	@InjectMocks
	CompanyService companyService;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);
		companyService = context.getBean(CompanyService.class);
		Field field = CompanyService.class.getDeclaredField("companyRepository");
		field.setAccessible(true);
		field.set(companyService, companyRepository);
		context.close();
	}

	@Test
	public void testGetAll() throws ItemNotFoundException {
		this.companyService.getAllCompanies();
		Mockito.verify(companyRepository).findAll();
	}

	@Test
	public void testGetAllCompaniesOrderByName() throws DAOException, ItemNotFoundException {
		this.companyService.getAllCompaniesOrderByName(OrderByOption.ASC);
		Mockito.verify(companyRepository).findAllByOrderByNameAsc();
	}

	@Test
	public void testGetCompanyById() throws DAOException, ItemNotFoundException {
		Optional<Company> companyOpt = Optional.of(new Company.Builder().build());
		Mockito.when(companyRepository.findById(Mockito.anyInt())).thenReturn(companyOpt);
		this.companyService.getCompanyById(0);
		Mockito.verify(companyRepository).findById(0);
	}

	@Test
	public void testDeleteCompany() throws DAOException, ItemNotFoundException, ItemNotDeletedException {
		Company company = new Company.Builder().withId(0).withName("Company_0").build();
		Optional<Company> companyOpt = Optional.of(company);
		Mockito.when(companyRepository.findById(Mockito.anyInt())).thenReturn(companyOpt);

		this.companyService.deleteCompany(company);
		Mockito.verify(companyRepository).delete(company);
	}
}
