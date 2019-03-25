package validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exception.CompanyException;
import model.Company;

public class TestCompanyValidator {

	static CompanyValidator companyValidator;

	@BeforeAll
	public static void setUp() {
		companyValidator = new CompanyValidator();
	}

	@Test
	public void testGoodCompany() {
		Company company = new Company();
		company.setId(1);
		company.setName("Apple Inc.");
		try {
			companyValidator.validate(company);
			assertTrue(true);
		} catch (CompanyException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testBadCompany1() {
		Company company = new Company();
		company.setId(1);
		try {
			companyValidator.validate(company);
			assertTrue(false);
		} catch (CompanyException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testBadCompany2() {
		Company company = new Company();
		company.setId(1);
		company.setName("");
		try {
			companyValidator.validate(company);
			assertTrue(false);
		} catch (CompanyException e) {
			assertTrue(true);
		}
	}


}
