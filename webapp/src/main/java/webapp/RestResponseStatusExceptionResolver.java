package webapp;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import persistence.exception.ItemBadCreatedException;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;
import persistence.exception.ItemNotUpdatedException;

@ControllerAdvice
public class RestResponseStatusExceptionResolver extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { ItemNotFoundException.class })
	protected ResponseEntity<Object> handleItemNotFound(ItemNotFoundException ex, WebRequest request) {
		String bodyOfResponse = "Don't find your object.";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = { ItemBadCreatedException.class })
	protected ResponseEntity<Object> handleItemBadCreated(ItemBadCreatedException ex, WebRequest request) {
		String bodyOfResponse = "Can't create your object.";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler(value = { ItemNotDeletedException.class })
	protected ResponseEntity<Object> handleItemNotDeleted(ItemNotDeletedException ex, WebRequest request) {
		String bodyOfResponse = "Can't delete your object.";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler(value = { ItemNotUpdatedException.class })
	protected ResponseEntity<Object> handleItemNotUpdated(ItemNotUpdatedException ex, WebRequest request) {
		String bodyOfResponse = "Can't update your object.";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_MODIFIED, request);
	}

	@ExceptionHandler(value = { AccessDeniedException.class })
	protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
		String bodyOfResponse = "You are not allowed to go here.";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}

	@ExceptionHandler(value = { AuthenticationException.class })
	protected ResponseEntity<Object> handleBadAuthentication(AuthenticationException ex, WebRequest request) {
		String bodyOfResponse = "Bad credentials.";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}
}
