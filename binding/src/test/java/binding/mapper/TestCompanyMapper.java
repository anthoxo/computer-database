package binding.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import binding.dto.CompanyDTO;
import core.model.Company;

@ExtendWith(MockitoExtension.class)
public class TestCompanyMapper {

	@Mock
	ResultSet rs;

	static AnnotationConfigApplicationContext context;

	CompanyMapper companyMapper;

	@BeforeAll
	public static void setUp() {
		context = new AnnotationConfigApplicationContext(MapperConfig.class);
	}

	@AfterAll
	public static void shutDown() {
		context.close();
	}


	@BeforeEach
	public void init() throws SQLException {
		companyMapper = context.getBean(CompanyMapper.class);
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
		Company company = companyMapper.createEntity(companyDTO);
		assertEquals(company.getId(), companyDTO.getId());
		assertEquals(company.getName(), companyDTO.getName());
	}

}
