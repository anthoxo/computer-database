package validator;

import exception.CompanyException;
import model.Company;

public class CompanyValidator {
	public void validate(Company company) throws CompanyException {
		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append(ValidatorUtil.notNullString.and(ValidatorUtil.notEmptyString).test(company.getName())
				.getFieldNameIfInvalid(" Company name is not valid ").orElse(""));

		String errors = strBuilder.toString();

		if (!errors.isEmpty()) {
			throw new CompanyException(errors);
		}
	}
}
