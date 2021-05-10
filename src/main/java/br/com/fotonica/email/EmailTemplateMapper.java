package br.com.fotonica.email;

import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class EmailTemplateMapper {

	/**
	 * Textos de assuntos do e-mail.
	 */
	private static final String ASSUNTO_CADASTRO = "[SIGNUPLAM] - Confirmação de cadastro";
	private static final String ASSUNTO_RESET_SENHA = "[SIGNUPLAM] - Redefinição de senha";
	private static final String ASSUNTO_ALERTA_EXPIRACAO_SENHA = "[SIGNUPLAM] - Alerta sobre expiração de senha";
	private static final String ASSUNTO_EXPIRACAO_SENHA = "[SIGNUPLAM] - Expiração de senha";
	private static final String ASSUNTO_NOTIFICACAO = "[SIGNUPLAM] - Notificação";

	/**
	 * Função de enviar e-mail (com o parâmetro de sessão omitido).
	 * 
	 * @param from    Remetente
	 * @param to      Destinatário
	 * @param assunto Assunto
	 * @param texto   Texto
	 */
//	public static void enviarEmail(String from, String to, String assunto, String texto) {
//		enviarEmail(from, to, assunto, texto);
//	}
//
//	/**
//	 * Função de enviar e-mail padrão para novo usuário no sistema (com o parâmetro
//	 * de sessão omitido).
//	 * 
//	 * @param account Destinatário.
//	 */
//	public static void enviarEmailNovoUsuario(AccountDetails account, String login, String senha, String serverUrl) {
//		enviarEmailNovoUsuario(account.getEmail(), senha, login, account.getName(), serverUrl);
//	}
//
//	/**
//	 * Envia e-mail sobre reset de senha.
//	 * 
//	 * @param account account que receberá o e-mail
//	 */
//	public static void enviarEmailResetSenha(AccountDetails account, String senha, String serverUrl) {
//		enviarEmailResetSenha(account.getEmail(), senha, serverUrl);
//	}
//
//	/**
//	 * Envia e-mail sobre aviso de expiração de senha.
//	 * 
//	 * @param account account que receberá o e-mail
//	 */
//	public static void enviarEmailAvisoExpiracaoSenha(AccountDetails account, String serverUrl) {
//		enviarEmailAvisoExpiracaoSenha(account.getEmail(), account.getName(), serverUrl);
//	}
//
//	/**
//	 * Envia e-mail sobre expiração de senha.
//	 * 
//	 * @param account account que receberá o e-mail
//	 */
//	public static void enviarEmailExpiracaoSenha(AccountDetails account, String serverUrl) {
//		enviarEmailExpiracaoSenha(account.getEmail(), account.getName(), serverUrl);
//	}
//
//	/**
//	 * Envia e-mail de notificação do sistema ao usuário.
//	 * 
//	 * @param account      account que receberá o e-mail
//	 */
//	public static void enviarEmailNotificacao(AccountDetails account, String titulo, String conteudo, String serverUrl) {
//		enviarEmailNotificacao(account.getEmail(), account.getName(), titulo, conteudo, null);
//	}
//
//	/**
//	 * Função de enviar e-mail com anexo (com o parâmetro de sessão omitido).
//	 * 
//	 * @param from      Remetente
//	 * @param to        Destinatário
//	 * @param assunto   Assunto
//	 * @param texto     Texto
//	 * @param tipoTexto Tipo de texto do e-mail
//	 * @param attach    Arquivo a ser anexado
//	 */
//	public static void enviarEmailComAnexo(String from, String to, String assunto, String texto, String tipoTexto,
//			File attach) {
//		enviarEmailComAnexo(from, to, assunto, texto, tipoTexto, attach);
//	}

//	/**
//	 * Método que efetua o envio de e-mail de acordo com os parametros recebidos
//	 * 
//	 * @param from, to, assunto, texto
//	 */
//	private static void enviarEmail(String from, String to, String assunto, String texto, Session mailSession) {
//		Email email = new Email(from, to, assunto, texto, null);
//		email.start();
//	}
//
//	/**
//	 * Efetua o envio de e-mail para um novo usuário.
//	 */
//	private static void enviarEmailNovoUsuario(String to, String senha, String login, String nomeUsuario, String serverUrl) {
//		String template = getMailStringFromTemplate(login, senha, null, null, nomeUsuario, "email_confirmacao", serverUrl);
//		Email email = new Email(null, to, ASSUNTO_CADASTRO, template, null);
//		email.start();
//	}
//
//	/**
//	 * Efetua o envio de e-mail para um novo usuário.
//	 */
//	private static void enviarEmailResetSenha(String to, String senha, String serverUrl) {
//		String template = "Prezado usuário\nSua nova senha é: " + senha;
//		Email email = new Email(null, to, ASSUNTO_RESET_SENHA, template, null);
//		email.start();
//	}
//
//	private static void enviarEmailAvisoExpiracaoSenha(String to, String nomeUsuario, String serverUrl) {
//		String template = getSimpleMailStringFromTemplate(nomeUsuario, "email_aviso_expiracao_senha", serverUrl);
//		Email email = new Email(null, to, ASSUNTO_ALERTA_EXPIRACAO_SENHA, template, null);
//		email.start();
//	}
//
//	private static void enviarEmailExpiracaoSenha(String to, String nomeUsuario, String serverUrl) {
//		String template = getSimpleMailStringFromTemplate(nomeUsuario, "email_expiracao_senha", serverUrl);
//		Email email = new Email(null, to, ASSUNTO_EXPIRACAO_SENHA, template, null);
//		email.start();
//	}
//
//	private static void enviarEmailNotificacao(String to, String nomeUsuario, String titulo, String conteudo, String serverUrl) {
//		String template = getNotificacaoMailStringFromTemplate(titulo, conteudo, nomeUsuario, serverUrl);
//		Email email = new Email(null, to, ASSUNTO_NOTIFICACAO, template, null);
//		email.start();
//	}
//
//	/**
//	 * Envia e-mail com anexo.
//	 */
//	private static void enviarEmailComAnexo(String from, String to, String assunto, String texto, String tipoTexto,
//			File attach, Session session) {
//		Email email = new Email(from, to, assunto, texto, null);
//		email.start();
//	}

//	private static String getNotificacaoMailStringFromTemplate(String titulo, String notificacao, String nomeUsuario, String serverUrl) {
//		return getMailStringFromTemplate(null, null, titulo, notificacao, nomeUsuario, "email_notificacao", serverUrl);
//	}
//
//	private static String getSimpleMailStringFromTemplate(String nomeUsuario, String template, String serverUrl) {
//		return getMailStringFromTemplate(null, null, null, null, nomeUsuario, template, serverUrl);
//	}

	/**
	 * Gera um texto HTML, correspondente ao template de e-mail, com as informações
	 * do usuário.
	 * 
	 * @param login       Login do usuário
	 * @param senha       Senha do usuário
	 * @param titulo      Título da notificação que gerou o e-mail
	 * @param conteudo    Conteúdo da notificação que gerou o e-mail
	 * @param nomeUsuario Nome do usuário
	 * @param template    Nome do template Thymeleaf
	 * @return String correspondente ao e-mail.
	 */
	public static String getMailStringFromTemplate(String template, Map<String, Object> variables) {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(1);
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		Context context = new Context();
		context.setVariables(variables);
		return templateEngine.process(template, context);
	}

}
