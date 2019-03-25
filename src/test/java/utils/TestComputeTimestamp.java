package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class TestComputeTimestamp {

	@Test
	public void testComputeTimestampGood1() {
		Optional<Timestamp> ts = Utils.stringToTimestamp("2000/01/01");

		assertTrue(ts.isPresent());
		assertEquals(ts.get().toLocalDateTime().getYear(), 2000);
		assertEquals(ts.get().toLocalDateTime().getMonthValue(), 1);
		assertEquals(ts.get().toLocalDateTime().getDayOfMonth(), 1);
	}

	@Test
	public void testComputeTimestampGood2() {
		Optional<Timestamp> ts = Utils.stringToTimestamp("1948/4/25");
		assertTrue(ts.isPresent());
		assertEquals(ts.get().toLocalDateTime().getYear(), 1948);
		assertEquals(ts.get().toLocalDateTime().getMonthValue(), 4);
		assertEquals(ts.get().toLocalDateTime().getDayOfMonth(), 25);
	}

	@Test
	public void testComputeTimestampGood3() {
		Optional<Timestamp> ts = Utils.stringToTimestamp("2004/02/29");
		assertTrue(ts.isPresent());
		assertEquals(ts.get().toLocalDateTime().getYear(), 2004);
		assertEquals(ts.get().toLocalDateTime().getMonthValue(), 2);
		assertEquals(ts.get().toLocalDateTime().getDayOfMonth(), 29);
	}

	@Test
	public void testComputeTimestampBad1() {
		Optional<Timestamp> ts = Utils.stringToTimestamp("zkgfbnizg");
		assertFalse(ts.isPresent());
	}

	@Test
	public void testComputeTimestampBad2() {
		Optional<Timestamp> ts = Utils.stringToTimestamp("1948/14/14");
		assertFalse(ts.isPresent());
	}

	@Test
	public void testComputeTimestampBad3() {
		Optional<Timestamp> ts = Utils.stringToTimestamp("1949/02/29");
		assertFalse(ts.isPresent());
	}

	@Test
	public void testComputeString1() {
		Timestamp ts = null;
		assertEquals(Utils.timestampToString(ts), "");
	}

	@Test
	public void testComputeString2() {
		Timestamp ts = Utils.stringToTimestamp("2020/02/01").get();
		assertEquals(Utils.timestampToString(ts), "2020/02/01");
	}
}
