package com.excilys.computerdatabase.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

public class TestComputeTimestamp {

	@Test
	public void testComputeTimestampGood1() {
		Timestamp ts = Utils.computeTimestamp("2000/01/01");
		assertNotEquals(ts, null);
		assertEquals(ts.toLocalDateTime().getYear(), 2000);
		assertEquals(ts.toLocalDateTime().getMonthValue(), 1);
		assertEquals(ts.toLocalDateTime().getDayOfMonth(), 1);
	}

	@Test
	public void testComputeTimestampGood2() {
		Timestamp ts = Utils.computeTimestamp("1948/4/25");
		assertNotEquals(ts, null);
		assertEquals(ts.toLocalDateTime().getYear(), 1948);
		assertEquals(ts.toLocalDateTime().getMonthValue(), 4);
		assertEquals(ts.toLocalDateTime().getDayOfMonth(), 25);
	}

	@Test
	public void testComputeTimestampGood3() {
		Timestamp ts = Utils.computeTimestamp("2004/02/29");
		assertNotEquals(ts, null);
		assertEquals(ts.toLocalDateTime().getYear(), 2004);
		assertEquals(ts.toLocalDateTime().getMonthValue(), 2);
		assertEquals(ts.toLocalDateTime().getDayOfMonth(), 29);
	}

	@Test
	public void testComputeTimestampBad1() {
		Timestamp ts = Utils.computeTimestamp("zkgfbnizg");
		assertEquals(ts, null);
	}

	@Test
	public void testComputeTimestampBad2() {
		Timestamp ts = Utils.computeTimestamp("1948/14/14");
		assertEquals(ts, null);
	}

	@Test
	public void testComputeTimestampBad3() {
		Timestamp ts = Utils.computeTimestamp("1949/02/29");
		assertEquals(ts, null);
	}
}
