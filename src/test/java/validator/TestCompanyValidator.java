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
		Company company = (new Company.Builder()).withId(1).withName("Apple Inc.").build();
		try {
			companyValidator.validate(company);
			assertTrue(true);
		} catch (CompanyException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testBadCompany1() {
		Company company = (new Company.Builder()).withId(1).build();
		try {
			companyValidator.validate(company);
			assertTrue(false);
		} catch (CompanyException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testBadCompany2() {
		Company company = (new Company.Builder()).withId(1).withName("").build();
		try {
			companyValidator.validate(company);
			assertTrue(false);
		} catch (CompanyException e) {
			assertTrue(true);
		}
	}


}
