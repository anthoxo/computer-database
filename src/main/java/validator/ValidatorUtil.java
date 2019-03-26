package validator;

import model.Computer;

public class ValidatorUtil {
	public static GenericValidation<String> notNullString = GenericValidation.from((String s) -> s != null);
	public static GenericValidation<String> notEmptyString = GenericValidation.from((String s) -> !s.equals(""));
	public static GenericValidation<String> validDateString = GenericValidation
			.from((String s) -> s == null ? false : s.matches("(^$|[0-9]{4}[/][0-9]{2}[/][0-9]{2}$)"));
	public static GenericValidation<Computer> validDateComputer = GenericValidation.from((Computer c) -> {
		boolean validComputer = true;
		if (c.getIntroduced() != null && c.getDiscontinued() != null) {
			if (c.getDiscontinued().getTime() - c.getIntroduced().getTime() < 0) {
				validComputer = false;
			}
		}
		return validComputer;
	});
}
