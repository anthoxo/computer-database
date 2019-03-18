package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestStringToEnum {

	@Test
	public void testChoiceDatabase1() {
		Utils.ChoiceDatabase cd = Utils.stringToEnum(Utils.ChoiceDatabase.class, "company");
		assertEquals(cd, Utils.ChoiceDatabase.COMPANY);
	}

	@Test
	public void testChoiceDatabase2() {
		Utils.ChoiceDatabase cd = Utils.stringToEnum(Utils.ChoiceDatabase.class, "cOmPuTeR");
		assertEquals(cd, Utils.ChoiceDatabase.COMPUTER);
	}

	@Test
	public void testChoiceDatabase3() {
		Utils.ChoiceDatabase cd = Utils.stringToEnum(Utils.ChoiceDatabase.class, "QUIT");
		assertEquals(cd, Utils.ChoiceDatabase.QUIT);
	}

	@Test
	public void testChoiceDatabase4() {
		Utils.ChoiceDatabase cd = Utils.stringToEnum(Utils.ChoiceDatabase.class, "zniofnz");
		assertEquals(cd, Utils.ChoiceDatabase.NULL);
	}

	@Test
	public void testChoiceActionComputer1() {
		Utils.ChoiceActionComputer cd = Utils.stringToEnum(Utils.ChoiceActionComputer.class, "get-all");
		assertEquals(cd, Utils.ChoiceActionComputer.GET_ALL);
	}

	@Test
	public void testChoiceActionComputer2() {
		Utils.ChoiceActionComputer cd = Utils.stringToEnum(Utils.ChoiceActionComputer.class, "get-ID");
		assertEquals(cd, Utils.ChoiceActionComputer.GET_ID);
	}

	@Test
	public void testChoiceActionComputer3() {
		Utils.ChoiceActionComputer cd = Utils.stringToEnum(Utils.ChoiceActionComputer.class, "GET-name");
		assertEquals(cd, Utils.ChoiceActionComputer.GET_NAME);
	}

}
