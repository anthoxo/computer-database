package core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestStringToEnum {

	@Test
	public void testChoiceDatabase1() {
		Utils.ChoiceDatabase cd = Utils.stringToEnum(Utils.ChoiceDatabase.class, "company");
		assertEquals(Utils.ChoiceDatabase.COMPANY, cd);
	}

	@Test
	public void testChoiceDatabase2() {
		Utils.ChoiceDatabase cd = Utils.stringToEnum(Utils.ChoiceDatabase.class, "cOmPuTeR");
		assertEquals(Utils.ChoiceDatabase.COMPUTER, cd);
	}

	@Test
	public void testChoiceDatabase3() {
		Utils.ChoiceDatabase cd = Utils.stringToEnum(Utils.ChoiceDatabase.class, "QUIT");
		assertEquals(Utils.ChoiceDatabase.QUIT, cd);
	}

	@Test
	public void testChoiceDatabase4() {
		Utils.ChoiceDatabase cd = Utils.stringToEnum(Utils.ChoiceDatabase.class, "zniofnz");
		assertEquals(Utils.ChoiceDatabase.NULL, cd);
	}

	@Test
	public void testChoiceActionComputer1() {
		Utils.ChoiceActionComputer cd = Utils.stringToEnum(Utils.ChoiceActionComputer.class, "get-all");
		assertEquals(Utils.ChoiceActionComputer.GET_ALL, cd);
	}

	@Test
	public void testChoiceActionComputer2() {
		Utils.ChoiceActionComputer cd = Utils.stringToEnum(Utils.ChoiceActionComputer.class, "get-ID");
		assertEquals(Utils.ChoiceActionComputer.GET_ID, cd);
	}
}
