package mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dto.CompanyDTO;
import main.MainConfig;
import model.Company;

@ExtendWith(MockitoExtension.class)
public class TestCompanyMapper {

	@Mock
	ResultSet rs;

	CompanyMapper companyMapper;

	@BeforeEach
	public void init() throws SQLException {
		AnnotationConfigApplicationContext context = MainConfig.getApplicationContext();
		companyMapper = context.getBean(CompanyMapper.class);
	}

	@Test
	public void testMap() throws SQLException {
		Mockito.when(rs.getInt("id")).thenReturn(1);
		Mockito.when(rs.getString("name")).thenReturn("Apple Inc.");

		Company company = companyMapper.map(rs);
		assertEquals(company.getId(), 1);
		assertEquals(company.getName(), "Apple Inc.");
	}

	@Test
	public void testCreateDTO() {
		Company company = (new Company.Builder()).withId(0).withName("Apple").build();
		CompanyDTO companyDTO = companyMapper.createDTO(company);
		assertEquals(company.getId(), companyDTO.getId());
		assertEquals(company.getName(), companyDTO.getName());
	}

	@Test
	public void testCreateBean() {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(0);
		companyDTO.setName("Apple");
		Company company = companyMapper.createBean(companyDTO);
		assertEquals(company.getId(), companyDTO.getId());
		assertEquals(company.getName(), companyDTO.getName());
	}

}
