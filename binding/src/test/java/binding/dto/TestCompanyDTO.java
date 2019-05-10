package binding.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCompanyDTO {
	CompanyDTO companyDTO;

	@BeforeEach
	public void init() {
		companyDTO = new CompanyDTO();
		companyDTO.setId(1);
		companyDTO.setName("Apple");
	}

	@Test
	public void testParam() {
		assertEquals(1, companyDTO.getId());
		assertEquals("Apple", companyDTO.getName());
	}
}
