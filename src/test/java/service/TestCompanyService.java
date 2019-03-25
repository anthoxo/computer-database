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

import dao.CompanyDAO;
import exception.DAOException;
import exception.ItemNotFoundException;
import model.Company;

@ExtendWith(MockitoExtension.class)
public class TestCompanyService {

	@Mock
	CompanyDAO companyDAO;

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
	public void testGetCompanyByName() throws ItemNotFoundException, DAOException {
		Mockito.when(companyDAO.get("")).thenReturn(Optional.of(new Company()));
		this.companyService.getCompanyByName("");
		Mockito.verify(companyDAO).get("");
	}

	@Test
	public void testGetCompanyByNameThrow() throws DAOException {
		try {
			this.companyService.getCompanyByName("");
		} catch (ItemNotFoundException e) {
			Mockito.verify(companyDAO).get("");
		}
	}


}
