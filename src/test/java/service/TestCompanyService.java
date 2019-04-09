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

import dao.CompanyDao;
import exception.DAOException;
import exception.ItemNotDeletedException;
import exception.ItemNotFoundException;
import mapper.CompanyMapper;
import model.Company;
import utils.Utils.OrderByOption;

@ExtendWith(MockitoExtension.class)
public class TestCompanyService {

	@Mock
	CompanyDao companyDAO;

	@InjectMocks
	CompanyService companyService;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = CompanyService.class.getDeclaredField("companyDAO");
		field.setAccessible(true);
		field.set(companyService, companyDAO);
	}

	@Test
	public void testGetAll() throws DAOException {
		this.companyService.getAllCompanies();
		Mockito.verify(companyDAO).getAll();
	}

	@Test
	public void testGetAllCompaniesOrderByName() throws DAOException {
		this.companyService.getAllCompaniesOrderByName(OrderByOption.ASC);
		Mockito.verify(companyDAO).getAllOrderByName(false);
	}

	@Test
	public void testGetCompanyById() throws DAOException, ItemNotFoundException {
		Optional<Company> companyOpt = Optional.of(new Company.Builder().build());
		Mockito.when(companyDAO.get(Mockito.anyInt())).thenReturn(companyOpt);
		this.companyService.getCompanyById(0);
		Mockito.verify(companyDAO).get(0);
	}

	@Test
	public void testDeleteCompany() throws DAOException, ItemNotFoundException, ItemNotDeletedException {
		Company company = new Company.Builder().withId(0).withName("Company_0").build();
		Optional<Company> companyOpt = Optional.of(company);
		Mockito.when(companyDAO.get(Mockito.anyInt())).thenReturn(companyOpt);
		this.companyService.deleteCompany(CompanyMapper.getInstance().createDTO(company));
		Mockito.verify(companyDAO).delete(company);
	}



}
