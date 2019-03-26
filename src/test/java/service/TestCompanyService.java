package service;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dao.CompanyDAO;
import exception.DAOException;

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
}
