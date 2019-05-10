package core.util;

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
		assertEquals(2000, ts.get().toLocalDateTime().getYear());
		assertEquals(1, ts.get().toLocalDateTime().getMonthValue());
		assertEquals(1, ts.get().toLocalDateTime().getDayOfMonth());
	}

	@Test
	public void testComputeTimestampGood2() {
		Optional<Timestamp> ts = Utils.stringToTimestamp("1948/04/25");
		assertTrue(ts.isPresent());
		assertEquals(1948, ts.get().toLocalDateTime().getYear());
		assertEquals(4, ts.get().toLocalDateTime().getMonthValue());
		assertEquals(25, ts.get().toLocalDateTime().getDayOfMonth());
	}

	@Test
	public void testComputeTimestampGood3() {
		Optional<Timestamp> ts = Utils.stringToTimestamp("2004/02/29");
		assertTrue(ts.isPresent());
		assertEquals(2004, ts.get().toLocalDateTime().getYear());
		assertEquals(2, ts.get().toLocalDateTime().getMonthValue());
		assertEquals(29, ts.get().toLocalDateTime().getDayOfMonth());
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
	public void testComputeString1() {
		Timestamp ts = null;
		assertEquals("", Utils.timestampToString(ts));
	}

	@Test
	public void testComputeString2() {
		Timestamp ts = Utils.stringToTimestamp("2020/02/01").get();
		assertEquals("2020/02/01", Utils.timestampToString(ts));
	}
}
