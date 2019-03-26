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

import dto.CompanyDTO;
import model.Company;

@ExtendWith(MockitoExtension.class)
public class TestCompanyMapper {

	@Mock
	ResultSet rs;

	CompanyMapper companyMapper;

	@BeforeEach
	public void init() throws SQLException {
		companyMapper = CompanyMapper.getInstance();
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
		Company c = (new Company.Builder()).withId(0).withName("Apple").build();
		CompanyDTO cDTO = companyMapper.createDTO(c);
		assertEquals(c.getId(), cDTO.getId());
		assertEquals(c.getName(), cDTO.getName());
	}

	@Test
	public void testCreateBean() {
		CompanyDTO cDTO = new CompanyDTO();
		cDTO.setId(0);
		cDTO.setName("Apple");
		Company c = companyMapper.createBean(cDTO);
		assertEquals(c.getId(), cDTO.getId());
		assertEquals(c.getName(), cDTO.getName());
	}

}
