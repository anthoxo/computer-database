package validator;

import java.util.Optional;

public class GenericValidationResult {

	private boolean valid;

	public boolean isValid() {
		return valid;
	}

	private GenericValidationResult(boolean t) {
		valid = t;
	}

	public static GenericValidationResult ok() {
		return new GenericValidationResult(true);
	}

	public static GenericValidationResult fail() {
		return new GenericValidationResult(false);
	}

	public Optional<String> getFieldNameIfInvalid(String field) {
		return this.valid ? Optional.empty() : Optional.of(field);
	}
}
