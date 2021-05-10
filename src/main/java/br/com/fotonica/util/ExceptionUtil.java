package br.com.fotonica.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

	/**
	 * Retorna a causa inicial da exceção
	 * 
	 * @param exception Exceção inicial
	 * @return Causa inicial
	 */

	public static Throwable getRootCauseMessage(Throwable exception) {
		Throwable rootException = exception;

		while (rootException.getCause() != null) {
			rootException = rootException.getCause();
		}
		return rootException;
	}

	/**
	 * Retorna a trilha de erro em formato string
	 * 
	 * @param exception Erro que quer retirar a trilha
	 * @return Trilha do erro em string
	 */
	public static String getStackTrace(Throwable exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		return sw.toString();
	}
}