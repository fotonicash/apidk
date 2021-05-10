package br.com.fotonica.email;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailConnector{
	
	public static void run(Email email) {
		new Thread() {
			
			private MimeBodyPart addAttachments() throws MessagingException {
				MimeBodyPart mbpAnexo = new MimeBodyPart();
				
				for(File attach : email.getAttachments()) {
					DataSource fds = new FileDataSource(attach.getPath());
					mbpAnexo.setDisposition(Part.ATTACHMENT);
					mbpAnexo.setDataHandler(new DataHandler(fds));
					mbpAnexo.setFileName(attach.getAbsolutePath());	
				}
				
				return mbpAnexo;
			}
			
			@Override
			public void run() {
				Message message = new MimeMessage(EmailSession.get());
				// Envia email email com anexo
				if (email.getAttachments() != null) {
					MimeBodyPart mbpTexto = new MimeBodyPart();
					try {
						message.setSubject(email.getSubject());
						message.setFrom(new InternetAddress(email.getFrom()));
						message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
						message.saveChanges();
						mbpTexto.setText(email.getText());

						Multipart multipart = new MimeMultipart();
						multipart.addBodyPart(mbpTexto);
						multipart.addBodyPart(addAttachments());
						message.setContent(multipart);
						Transport.send(message);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				} else {
					try {
						message.setFrom(new InternetAddress(email.getFrom()));
						message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
						message.setSubject(email.getSubject());
						message.setContent(email.getText(), email.getContentType());
						
						Transport.send(message);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

}
