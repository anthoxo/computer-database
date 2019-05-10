package binding.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import binding.dto.UserDTO;
import core.util.Variable;

public class UserDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDTO userDTO = (UserDTO)target;
		String isNullOrEmpty = "is null or empty";
		if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
			errors.rejectValue("email", isNullOrEmpty);
		} else if (!userDTO.getEmail().matches(Variable.REGEX_EMAIL)) {
			errors.rejectValue("email", "not valid email");
		}

		if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
			errors.rejectValue("hashPassword", isNullOrEmpty);
		}

		if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
			errors.rejectValue("username", isNullOrEmpty);
		}
	}

}
