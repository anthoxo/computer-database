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

import model.Company;

@ExtendWith(MockitoExtension.class)
public class TestCompanyMapper {
	
	@Mock
	ResultSet rs;
	
	CompanyMapper companyMapper;
	
	@BeforeEach
	public void init() throws SQLException {
		companyMapper = CompanyMapper.getInstance();
		Mockito.when(rs.getInt("id")).thenReturn(1);
		Mockito.when(rs.getString("name")).thenReturn("Apple Inc.");
	}
	
	@Test
	public void testMap() throws SQLException {
		Company company = companyMapper.map(rs);
		assertEquals(company.getId(),1);
		assertEquals(company.getName(),"Apple Inc.");
	}


}
