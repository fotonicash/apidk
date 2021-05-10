package br.com.fotonica.email;

import java.io.File;

public class Email {
	
	private String from;
	
	private String to;
	
	private String subject;
	
	private String text;
	
	private String contentType;
	
	private File[] attachments;

	public String getFrom() {
		if(this.from != null) {
		return from;
		}
		else {
			return EmailSession.EMAIL;
		}
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public File[] getAttachments() {
		return attachments;
	}

	public void setAttachments(File[] attachments) {
		this.attachments = attachments;
	}

	public String getContentType() {
		return contentType != null ? contentType : "text/html; charset=UTF-8";
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}