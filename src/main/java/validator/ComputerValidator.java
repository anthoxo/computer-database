package validator;

import java.sql.Timestamp;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dto.ComputerDTO;
import utils.Utils;

public class ComputerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ComputerDTO computerDTO = (ComputerDTO)target;
		if (computerDTO.getName() == null || computerDTO.getName().isEmpty()) {
			errors.rejectValue("name", "is null or empty");
		}
		boolean goodRegexIntroducedDate = true;
		boolean goodRegexDiscontinuedDate = true;
		if (computerDTO.getIntroducedDate() != null) {
			goodRegexIntroducedDate = computerDTO.getIntroducedDate().matches("(^$|[1-9][0-9]{3}[/][0-9]{2}[/][0-9]{2}$)");
		}
		if (computerDTO.getDiscontinuedDate() != null) {
			goodRegexDiscontinuedDate = computerDTO.getIntroducedDate().matches("(^$|[1-9][0-9]{3}[/][0-9]{2}[/][0-9]{2}$)");
		}

		if (!goodRegexIntroducedDate) {
			errors.rejectValue("introducedDate", "not in right form");
		}
		if (!goodRegexDiscontinuedDate) {
			errors.rejectValue("discontinuedDate", "not in right form");
		}

		if (goodRegexIntroducedDate && goodRegexDiscontinuedDate) {
			Timestamp introduced = Utils.stringToTimestamp(computerDTO.getIntroducedDate()).orElse(null);
			Timestamp discontinued = Utils.stringToTimestamp(computerDTO.getDiscontinuedDate()).orElse(null);
			if (introduced != null && discontinued != null) {
				if (discontinued.getTime() - introduced.getTime() < 0) {
					errors.rejectValue("introducedDate", "after discontinued");
					errors.rejectValue("discontinuedDate", "before introduced");
				}
			}
		}
	}
}
