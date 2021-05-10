package br.com.fotonica.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

public class NegocioException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2088670385984479912L;

	/**
	 * Lista de erros por campo
	 */
	private List<FieldError> erros = new ArrayList<FieldError>();

	public NegocioException(String msg) {
		super(msg);
	}

	public List<FieldError> getErros() {
		return erros;
	}

	public void setErros(List<FieldError> erros) {
		this.erros = erros;
	}

	public void addFieldError(FieldError error) {
		this.erros.add(error);
	}

	public List<String> getDefaultMessages() {
		List<String> defaultMessages = new ArrayList<String>();
		for (FieldError err : erros) {
			defaultMessages.add(getDefaultMessage(err.toString()));
		}
		return defaultMessages;
	}

	public static String getDefaultMessage(String fieldErrorToString) {
		String defaultMessage = fieldErrorToString.substring(fieldErrorToString.indexOf("default message [") + 17);
		defaultMessage = defaultMessage.substring(0, defaultMessage.indexOf("]"));
		return defaultMessage;
	}

}