package validator;

import exception.ComputerException;
import model.Computer;

public class ComputerValidator {
	public void validate(Computer computer) throws ComputerException {
		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append(ValidatorUtil.notNullString.and(ValidatorUtil.notEmptyString).test(computer.getName())
				.getFieldNameIfInvalid(" Computer name is not valid ").orElse(""));
		strBuilder.append(ValidatorUtil.validDateComputer.test(computer)
				.getFieldNameIfInvalid(" Computer dates are not valid ").orElse(""));

		String errors = strBuilder.toString();

		if (!errors.isEmpty()) {
			throw new ComputerException(errors);
		}
	}
}
