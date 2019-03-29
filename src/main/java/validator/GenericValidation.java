package validator;

import java.util.function.Predicate;

public class GenericValidation<T> {

	Predicate<T> predicate;

	private GenericValidation(Predicate<T> pred) {
		this.predicate = pred;
	}

	public static <T> GenericValidation<T> from(Predicate<T> pred) {
		return new GenericValidation<T>(pred);
	}

	public GenericValidation<T> and(GenericValidation<T> other) {
		return GenericValidation.from((T param) -> {
			return this.predicate.test(param) && other.predicate.test(param);
		});
	}

	public GenericValidationResult test(T param) {
		return predicate.test(param) ? GenericValidationResult.ok() : GenericValidationResult.fail();
	}
}
