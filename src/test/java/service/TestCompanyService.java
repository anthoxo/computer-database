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

import dao.CompanyDao;
import exception.DAOException;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import main.MainConfig;
import mapper.CompanyMapper;
import model.Company;
import utils.Utils.OrderByOption;

@ExtendWith(MockitoExtension.class)
public class TestCompanyService {

	@Mock
	CompanyDao companyDao;

	CompanyMapper companyMapper;

	@InjectMocks
	CompanyService companyService;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
		companyService = context.getBean(CompanyService.class);
		companyMapper = context.getBean(CompanyMapper.class);
		Field field = CompanyService.class.getDeclaredField("companyDao");
		field.setAccessible(true);
		field.set(companyService, companyDao);
		context.close();
	}

	@Test
	public void testGetAll() throws DAOException {
		this.companyService.getAllCompanies();
		Mockito.verify(companyDao).getAll();
	}

	@Test
	public void testGetAllCompaniesOrderByName() throws DAOException {
		this.companyService.getAllCompaniesOrderByName(OrderByOption.ASC);
		Mockito.verify(companyDao).getAllOrderByName(false);
	}

	@Test
	public void testGetCompanyById() throws DAOException, ItemNotFoundException {
		Optional<Company> companyOpt = Optional.of(new Company.Builder().build());
		Mockito.when(companyDao.get(Mockito.anyInt())).thenReturn(companyOpt);
		this.companyService.getCompanyById(0);
		Mockito.verify(companyDao).get(0);
	}

	@Test
	public void testDeleteCompany() throws DAOException, ItemNotFoundException, ItemNotDeletedException {
		Company company = new Company.Builder().withId(0).withName("Company_0").build();
		Optional<Company> companyOpt = Optional.of(company);
		Mockito.when(companyDao.get(Mockito.anyInt())).thenReturn(companyOpt);

		this.companyService.deleteCompany(companyMapper.createDTO(company));
		Mockito.verify(companyDao).delete(company);
	}
}
