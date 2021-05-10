package br.com.fotonica.email;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public void send(Email email) {
		EmailConnector.run(email);
	}
	
}
