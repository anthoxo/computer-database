package model;

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
		assertEquals(company.getId(), 1);
		assertEquals(company.getName(), "Apple");
	}


	@Test
	public void testEquals1() {
		Company c = (new Company.Builder()).build();
		assertFalse(company.equals(c));
	}

	@Test
	public void testEquals2() {
		Object c = new ArrayList<Company>();
		assertFalse(company.equals(c));
	}

	@Test
	public void testEquals3() {
		Company c = (new Company.Builder()).withId(1).withName("apple").build();
		assertFalse(company.equals(c));
	}

	@Test
	public void testEquals4() {
		Company c = (new Company.Builder()).withId(1).withName("Apple").build();;
		assertTrue(company.equals(c));
	}

	@Test
	public void testEquals5() {
		Company c = company;
		assertTrue(company.equals(c));
	}
}
