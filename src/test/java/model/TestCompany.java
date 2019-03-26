package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		boolean t = company.equals(c);
		assertTrue(t == false);
	}

	@Test
	public void testEquals2() {
		Object c = new ArrayList<Company>();
		boolean t = company.equals(c);
		assertTrue(t == false);
	}

	@Test
	public void testEquals3() {
		Company c = (new Company.Builder()).withId(1).withName("apple").build();
		boolean t = company.equals(c);
		assertTrue(t == false);
	}

	@Test
	public void testEquals4() {
		Company c = (new Company.Builder()).withId(1).withName("Apple").build();;
		boolean t = company.equals(c);
		assertTrue(t);
	}

	@Test
	public void testEquals5() {
		Company c = company;
		assertTrue(company.equals(c));
	}
}
