package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		computer = new Computer();
		company = new Company();

		company.setId(1);
		company.setName("Apple");

		computer.setId(1);
		computer.setName("MacBook");
		computer.setIntroduced(Utils.stringToTimestamp("2000/01/01"));
		computer.setDiscontinued(Utils.stringToTimestamp("2001/01/01"));
		computer.setCompanyId(1);
		computer.setCompany(company);
	}

	@Test
	public void testParam() {
		assertEquals(computer.getId(), 1);
		assertEquals(computer.getName(), "MacBook");
		assertEquals(computer.getIntroduced(), Utils.stringToTimestamp("2000/01/01"));
		assertEquals(computer.getDiscontinued(), Utils.stringToTimestamp("2001/01/01"));
		assertEquals(computer.getCompanyId(), 1);
		assertEquals(computer.getCompany(), company);
	}


	@Test
	public void testEquals1() {
		Computer c = new Computer();
		boolean t = computer.equals(c);
		assertTrue(t == false);
	}

	@Test
	public void testEquals2() {
		Object c = new ArrayList<Company>();
		boolean t = computer.equals(c);
		assertTrue(t == false);
	}

	@Test
	public void testEquals3() {
		Computer c = new Computer();
		c.setId(1);
		c.setName("Macbook");
		c.setIntroduced(Utils.stringToTimestamp("2000/01/01"));
		c.setDiscontinued(Utils.stringToTimestamp("2001/01/01"));
		c.setCompanyId(1);
		c.setCompany(company);
		boolean t = computer.equals(c);
		assertTrue(t == false);
	}

	@Test
	public void testEquals4() {
		Computer c = new Computer();
		c.setId(1);
		c.setName("MacBook");
		c.setIntroduced(Utils.stringToTimestamp("2000/01/01"));
		c.setDiscontinued(Utils.stringToTimestamp("2001/01/01"));
		c.setCompanyId(1);
		c.setCompany(company);
		boolean t = computer.equals(c);
		assertTrue(t);
	}

	@Test
	public void testEquals5() {
		Computer c = computer;
		boolean t = computer.equals(c);
		assertTrue(t);
	}

	@Test
	public void testToString() {
		String computerString = computer.toString();
		assertTrue(computerString.contains(computer.getName()));
		assertTrue(computerString.contains(computer.getIntroduced().toString()));
		assertTrue(computerString.contains(computer.getDiscontinued().toString()));
		assertTrue(computerString.contains(computer.getCompany().toString()));
	}

	@Test
	public void testIsValidComputer1() {
		assertTrue(computer.isValidComputer());
	}

	@Test
	public void testIsValidComputer2() {
		computer.setDiscontinued(Utils.stringToTimestamp("1990/01/01"));
		assertTrue(computer.isValidComputer() == false);
	}
}
