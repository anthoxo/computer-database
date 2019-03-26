package validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exception.ComputerException;
import model.Computer;
import utils.Utils;

public class TestComputerValidator {
	static ComputerValidator computerValidator;

	@BeforeAll
	public static void setUp() {
		computerValidator = new ComputerValidator();
	}

	@Test
	public void testGoodComputer1() {
		Computer computer = new Computer.Builder().withId(1).withName("Ordi").build();
		try {
			computerValidator.validate(computer);
			assertTrue(true);
		} catch (ComputerException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGoodComputer2() {
		Computer computer = new Computer.Builder().withId(1)
				.withName("Ordi")
				.withIntroducedDate(Utils.stringToTimestamp("2020/01/01").get())
				.build();
		try {
			computerValidator.validate(computer);
			assertTrue(true);
		} catch (ComputerException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGoodComputer3() {
		Computer computer = new Computer.Builder().withId(1)
				.withName("Ordi")
				.withIntroducedDate(Utils.stringToTimestamp("2020/01/01").get())
				.withDiscontinuedDate(Utils.stringToTimestamp("2030/01/01").get())
				.build();
		try {
			computerValidator.validate(computer);
			assertTrue(true);
		} catch (ComputerException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testBadComputer1() {
		Computer computer = new Computer.Builder().withId(1)
				.withIntroducedDate(Utils.stringToTimestamp("2020/01/01").get())
				.withDiscontinuedDate(Utils.stringToTimestamp("2030/01/01").get())
				.build();
		try {
			computerValidator.validate(computer);
			assertTrue(false);
		} catch (ComputerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testBadComputer2() {
		Computer computer = new Computer.Builder().withId(1)
				.withName("")
				.withIntroducedDate(Utils.stringToTimestamp("2020/01/01").get())
				.withDiscontinuedDate(Utils.stringToTimestamp("2030/01/01").get())
				.build();
		try {
			computerValidator.validate(computer);
			assertTrue(false);
		} catch (ComputerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testBadComputer3() {
		Computer computer = new Computer.Builder().withId(1)
				.withName("Ordi")
				.withIntroducedDate(Utils.stringToTimestamp("2035/01/01").get())
				.withDiscontinuedDate(Utils.stringToTimestamp("2030/01/01").get())
				.build();
		try {
			computerValidator.validate(computer);
			assertTrue(false);
		} catch (ComputerException e) {
			assertTrue(true);
		}
	}
}
