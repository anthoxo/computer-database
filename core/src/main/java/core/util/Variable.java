package core.util;

public class Variable {

	private Variable() {
	}

	public static final String REGEX_DATE = "(^$|[1-9][0-9]{3}[/][0-9]{2}[/][0-9]{2}$)";
	public static final String REGEX_EMAIL = "(^[a-zA-Z0-9_.-]+[@][a-zA-Z]+[.][a-z]{2,6}$)";

}
