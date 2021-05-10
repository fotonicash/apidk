package br.com.fotonica.email;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class EmailSession {

	/**
	 * Parâmetros de e-mails e servidor SMTP.
	 */
	private static final String HOST = "smtp.gmail.com";
	private static final String SSL_TRUST = "*";
	private static final String PORT = "587";
	private static final Boolean TTLS_ENABLE = true;
	private static final String AUTH = "true";

	/**
	 * Credenciais do e-mail usadas para se autenticar no servidor SMTP.
	 */
	public static final String EMAIL = "softwarehousefotonica@gmail.com";
	private static final String SENHA = "Fotonica2020";

	private static Session mailSession;

	public static Session get() {
		if (mailSession == null) {
			return connect();
		}
		return mailSession;
	}

	/**
	 * Connect with e-mail server
	 */
	private static Session connect() {
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.ssl.trust", SSL_TRUST);
		props.put("mail.smtp.socketFactory.port", PORT);
		props.put("mail.smtp.starttls.enable", TTLS_ENABLE);
		props.put("mail.smtp.auth", AUTH);
		props.put("mail.smtp.port", PORT);

		mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL, SENHA);
			}
		});
		
		return mailSession;
	}
}
