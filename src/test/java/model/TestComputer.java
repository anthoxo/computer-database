package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.Utils;

public class TestComputer {
	Computer computer;

	Company company;

	@BeforeEach
	public void init() {
		company = (new Company.Builder()).withId(1).withName("Apple").build();
		computer = (new Computer.Builder()).withId(1)
				.withName("MacBook")
				.withIntroducedDate(Utils.stringToTimestamp("2000/01/01").get())
				.withDiscontinuedDate(Utils.stringToTimestamp("2001/01/01").get())
				.withCompanyId(1)
				.withCompany(company)
				.build();
		}

	@Test
	public void testParam() {
		assertEquals(computer.getId(), 1);
		assertEquals(computer.getName(), "MacBook");
		assertEquals(computer.getIntroduced(), Utils.stringToTimestamp("2000/01/01").get());
		assertEquals(computer.getDiscontinued(), Utils.stringToTimestamp("2001/01/01").get());
		assertEquals(computer.getCompanyId(), 1);
		assertEquals(computer.getCompany(), company);
	}


	@Test
	public void testEquals1() {
		Computer c = new Computer.Builder().build();
		assertFalse(computer.equals(c));
	}

	@Test
	public void testEquals2() {
		Object c = new ArrayList<Company>();
		assertFalse(computer.equals(c));
	}

	@Test
	public void testEquals3() {
		Computer c = (new Computer.Builder()).withId(1)
				.withName("Macbook")
				.withIntroducedDate(Utils.stringToTimestamp("2000/01/01").get())
				.withDiscontinuedDate(Utils.stringToTimestamp("2001/01/01").get())
				.withCompanyId(1)
				.withCompany(company)
				.build();
		assertFalse(computer.equals(c));
	}

	@Test
	public void testEquals4() {
		Computer c = (new Computer.Builder()).withId(1)
				.withName("MacBook")
				.withIntroducedDate(Utils.stringToTimestamp("2000/01/01").get())
				.withDiscontinuedDate(Utils.stringToTimestamp("2001/01/01").get())
				.withCompanyId(1)
				.withCompany(company)
				.build();
		assertTrue(computer.equals(c));
	}

	@Test
	public void testEquals5() {
		Computer c = computer;
		assertTrue(computer.equals(c));
	}

	@Test
	public void testToString() {
		String computerString = computer.toString();
		assertTrue(computerString.contains(computer.getName()));
		assertTrue(computerString.contains(computer.getIntroduced().toString()));
		assertTrue(computerString.contains(computer.getDiscontinued().toString()));
		assertTrue(computerString.contains(computer.getCompany().toString()));
	}
}
