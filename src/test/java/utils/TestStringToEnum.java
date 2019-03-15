package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestStringToEnum {
	// ChoiceDatabase COMPANY, COMPUTER, QUIT, NULL
	
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


}
