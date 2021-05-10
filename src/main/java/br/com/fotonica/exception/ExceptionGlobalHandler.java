package br.com.fotonica.exception;

import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import br.com.fotonica.util.ExceptionUtil;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionGlobalHandler {
	
	@ExceptionHandler(InsufficientAuthenticationException.class)
	protected ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
		Throwable exCausa = ExceptionUtil.getRootCauseMessage(ex);
		exCausa.printStackTrace();
		
		String message = exCausa.getMessage();
		Erro erro = new Erro(message);
		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(NegocioException.class)
    protected ResponseEntity<Object> handleInvalidInput(NegocioException ex, WebRequest request){
		Throwable exCausa = ExceptionUtil.getRootCauseMessage(ex);
		exCausa.printStackTrace();

		String mensagem = exCausa.getMessage();
		Erro erro = new Erro(mensagem, ex.getErros());
		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }	
	
	@ExceptionHandler(AnonymousUserException.class)
    protected ResponseEntity<Object> handleInvalidInput(AnonymousUserException ex, WebRequest request){
		Throwable exCausa = ExceptionUtil.getRootCauseMessage(ex);
		exCausa.printStackTrace();
		Erro erro = new Erro(ex.getMessage());
		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }	
	
	public static class Erro {
		private List<FieldError> erros;
		private String message;
		
		public Erro(String message) {
			this.message = message;
			this.erros = null;
		}

		public Erro(String message, BindingResult bindingResult) {
			this.message = message;
			this.erros = bindingResult.getFieldErrors();
		}

		public Erro(String message, List<FieldError> erros) {
			this.message = message;
			this.erros = erros;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public List<FieldError> getErros() {
			return erros;
		}

		public void setErros(List<FieldError> erros) {
			this.erros = erros;
		}		
	}
	
	/**
	 * A single place to customize the response body of all Exception types.
	 * <p>The default implementation sets the {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE}
	 * request attribute and creates a {@link ResponseEntity} from the given
	 * body, headers, and status.
	 * @param ex the exception
	 * @param body the body for the response
	 * @param headers the headers for the response
	 * @param status the response status
	 * @param request the current request
	 */
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		return new ResponseEntity<>(body, headers, status);
	}
	
}
