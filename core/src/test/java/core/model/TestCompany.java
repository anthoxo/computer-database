package core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCompany {

	Company company;

	@BeforeEach
	public void init() {
		company = (new Company.Builder()).withId(1).withName("Apple").build();
	}

	@Test
	public void testParam() {
		assertEquals(1, company.getId());
		assertEquals("Apple", company.getName());
	}

	@Test
	public void testEquals1() {
		Company company2 = (new Company.Builder()).build();
		assertFalse(company.equals(company2));
	}

	@Test
	public void testEquals2() {
		Object company2 = new ArrayList<Company>();
		assertFalse(company.equals(company2));
	}

	@Test
	public void testEquals3() {
		Company company2 = (new Company.Builder()).withId(1).withName("apple").build();
		assertFalse(company.equals(company2));
	}

	@Test
	public void testEquals4() {
		Company company2 = (new Company.Builder()).withId(1).withName("Apple").build();
		;
		assertTrue(company.equals(company2));
	}

	@Test
	public void testEquals5() {
		Company company2 = company;
		assertTrue(company.equals(company2));
	}
}
