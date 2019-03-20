package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dto.CompanyDTO;
import mapper.CompanyMapper;
import model.Company;

@ExtendWith(MockitoExtension.class)
public class TestCompanyDAO {

	@Mock
	DAOFactory dao;

	@Mock
	Connection connection;

	@Mock
	PreparedStatement stmt;

	@Mock
	ResultSet rs;

	@Mock
	CompanyMapper companyMapper;

	@InjectMocks
	CompanyDAO companyDAO;

	List<Company> listCompanies;

	@BeforeEach
	public void init()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		this.companyDAO = CompanyDAO.getInstance();

		listCompanies = new ArrayList<Company>();
		for (int i = 0; i < 3; ++i) {
			Company company = new Company();
			company.setId(i + 1);
			company.setName("Company_" + String.valueOf(i + 1));
			listCompanies.add(company);
		}

		Field field = CompanyDAO.class.getDeclaredField("daoFactory");
		field.setAccessible(true);
		field.set(companyDAO, dao);

		field = CompanyDAO.class.getDeclaredField("companyMapper");
		field.setAccessible(true);
		field.set(companyDAO, companyMapper);
	}

	public void initSQL() throws SQLException {
		Mockito.when(dao.getConnection()).thenReturn(connection);
		Mockito.when(stmt.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE)
				.thenReturn(Boolean.FALSE);
		Mockito.doReturn(listCompanies.get(0)).when(this.companyDAO.companyMapper).map(rs);
	}

	@Test
	public void testGetById() throws SQLException {
		initSQL();
		Mockito.when(connection.prepareStatement(CompanyDAO.REQUEST_GET_BY_ID)).thenReturn(stmt);
		Company company = this.companyDAO.get(0);
		assertEquals(company.getId(), 1);
		assertEquals(company.getName(), "Company_1");
	}

	@Test
	public void testGetByName() throws SQLException {
		initSQL();
		Mockito.when(connection.prepareStatement(CompanyDAO.REQUEST_GET_BY_NAME)).thenReturn(stmt);
		Company company = this.companyDAO.get("Company_1");
		assertEquals(company.getId(), 1);
		assertEquals(company.getName(), "Company_1");
	}

	@Test
	public void testGetAll() throws SQLException {
		initSQL();
		Mockito.when(connection.prepareStatement(CompanyDAO.REQUEST_GET_ALL)).thenReturn(stmt);
		List<Company> listCompany = this.companyDAO.getAll();
		assertEquals(listCompany.size(), 3);
	}

	@Test
	public void testCreateDTO() {
		Company c = listCompanies.get(0);
		CompanyDTO cDTO = companyDAO.createDTO(c);
		assertEquals(c.getId(), cDTO.getId());
		assertEquals(c.getName(), cDTO.getName());
	}

	@Test
	public void testCreateBean() {
		CompanyDTO cDTO = new CompanyDTO();
		cDTO.setId(0);
		cDTO.setName("Apple");
		Company c = companyDAO.createBean(cDTO);
		assertEquals(c.getId(), cDTO.getId());
		assertEquals(c.getName(), cDTO.getName());
	}
}
