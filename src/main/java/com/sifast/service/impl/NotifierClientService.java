package com.sifast.service.impl;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
import com.sifast.model.Client;
import com.sifast.model.Reclamation;

public class NotifierClientService {

	static final Logger logger = Logger.getLogger(NotifierClientService.class);
	
	private NotifierClientService() {

	}

	public static void sendRefReclamationToClient(Reclamation reclamation, Client client, boolean isNewReclamation) {

		String refReclamation = reclamation.getRefReclam();

		// Recipient's email ID needs to be mentioned.
		String to = client.getEmail();

		// Sender's email ID needs to be mentioned
		String from = "service.reclamation.tn@gmail.com";
		final String username = "service.reclamation.tn@gmail.com";// change accordingly
		final String password = "ServiceReclamation2015";// change accordingly

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		// session.setDebug(true);
		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Réclamation: " + refReclamation);

			// Corps du message.
			Multipart mpart = new MimeMultipart();

			// Première partie du message
			BodyPart htmlPart = new MimeBodyPart();

			// le contenu du message sous format HTML
			if (isNewReclamation)
			{
				htmlPart.setContent(
						"Votre réclamation est enregistrée avec la référence: "
								+ refReclamation
								+ "<br> Vous pouvez suivre l'état de votre réclamation en accédant au page web suivante: <a href='http://localhost:8088/reclamation/f/vue/etatReclamation.xhtml'>Cliquer ici</a><br>Merci bien.",
						"text/html");
			}
			else
			{
				htmlPart.setContent(
						"Cette réclamation a été déjà enregistrée par un autre concitoyen le "
								+ reclamation.getDateReclam()
								+ "avec la référence: "
								+ refReclamation
								+ "<br> Vous pouvez suivre l'état de cette réclamation en accédant au page web suivante: <a href='http://localhost:8088/reclamation/f/vue/etatReclamation.xhtml'>Cliquer ici</a><br>Merci bien.",
						"text/html");
			}

			// Mettre toutes les parties dans le MultiPart.
			mpart.addBodyPart(htmlPart);

			// Mettre le MultiPart dans le Message.
			message.setContent(mpart);

			// Send message
			Transport.send(message);
			logger.debug("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} finally {
			logger.debug("fin send mail");
		}
	}

	public static void notifyClientThatClaimFenced(Reclamation reclamation) {
		String refReclamation = reclamation.getRefReclam();
		// Recipient's email ID needs to be mentioned.
		String to = reclamation.getClient().getEmail();

		// Sender's email ID needs to be mentioned
		String from = "service.reclamation.tn@gmail.com";
		final String username = "service.reclamation.tn@gmail.com";// change accordingly
		final String password = "ServiceReclamation2015";// change accordingly

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		session.setDebug(true);
		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Réclamation: " + refReclamation);

			// Now set the actual message
			message.setText("Service: Votre réclamation avec la référence: " + refReclamation + " faite le " + reclamation.getDateReclam()
					+ " a été résolu.\nMerci bien.");

			// Send message
			Transport.send(message);
			logger.debug("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} finally {
			logger.debug("fin send mail");
		}
	}
}