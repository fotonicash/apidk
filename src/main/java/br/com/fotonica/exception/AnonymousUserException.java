package br.com.fotonica.exception;

public class AnonymousUserException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2891737750988078773L;

	public AnonymousUserException() {
		super("Missing token - anonymousUser not allowed.");
	}
	
}
