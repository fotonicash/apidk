package br.com.fotonica.apidk;

import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import br.com.fotonica.exception.NegocioException;
import br.com.fotonica.util.ExceptionUtil;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionAdapter extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable exCausa = ExceptionUtil.getRootCauseMessage(ex);
		exCausa.printStackTrace();
		
		String message = "Mensagem inválida.";
		String mensagemDev = exCausa.getMessage();
		Erro erro = new Erro(message);
		return handleExceptionInternal(ex, erro, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable exCausa = ExceptionUtil.getRootCauseMessage(ex);
		exCausa.printStackTrace();
		
		String message = "Recurso não suportado.";
		String mensagemDev = exCausa.getMessage();
		Erro erro = new Erro(message);
		return handleExceptionInternal(ex, erro, headers, HttpStatus.METHOD_NOT_ALLOWED, request);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		Throwable exCausa = ExceptionUtil.getRootCauseMessage(ex);
		exCausa.printStackTrace();
		
		String message = "Recurso não encontrado.";
		String mensagemDev = exCausa.getMessage();
		Erro erro = new Erro(message);
		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable exCausa = ExceptionUtil.getRootCauseMessage(ex);
		exCausa.printStackTrace();

		String message = "Campos Inválidos.";
		Erro erro = new Erro(message, ex.getBindingResult().getFieldErrors());
		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}	
	
	@org.springframework.web.bind.annotation.ExceptionHandler({ DataIntegrityViolationException.class } )
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		Throwable exCausa = ExceptionUtil.getRootCauseMessage(ex);
		exCausa.printStackTrace();

		String message = "Operação não permitida.";
		Erro erro = new Erro(message);
		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(NegocioException.class)
    protected ResponseEntity<Object> handleInvalidInput(NegocioException ex, WebRequest request){
		Throwable exCausa = ExceptionUtil.getRootCauseMessage(ex);
		exCausa.printStackTrace();

		String message = exCausa.getMessage();
		Erro erro = new Erro(message, ex.getErros());
		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
	
//	@org.springframework.web.bind.annotation.ExceptionHandler(UsernameNotFoundException.class)
//    protected ResponseEntity<Object> handleUserNotFound(NegocioException ex, WebRequest request){
//		Throwable exCausa = ExceptionUtil.getRootCauseMessage(ex);
//		exCausa.printStackTrace();
//
//		String mensagem = exCausa.getMessage();
//		String mensagemDev = exCausa.getMessage();
//		Erro erro = new Erro(mensagem, mensagemDev, ex.getErros());
//		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
	
	public static class Erro {
		private List<FieldError> erros;
		private String message;
		
		public Erro(String message) {
			this.message = message;
			this.erros = null;
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
