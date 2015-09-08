package com.sifast.cron;

import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.sifast.model.Reclamation;
import com.sifast.service.IReclamationService;

public class ReclamationTask extends TimerTask {

	static final Logger logger = Logger.getLogger(ReclamationTask.class);
	
	private IReclamationService reclamationService;

	private List<Reclamation> listReclamation;

	public ReclamationTask(IReclamationService reclamationService) {
		this.reclamationService = reclamationService;
	}

	public List<Reclamation> getListReclamation() {
		return listReclamation;
	}

	public void setListReclamation(List<Reclamation> listReclamation) {
		this.listReclamation = listReclamation;
	}

	@Override
	public void run() {
		logger.debug("Reclamation Task !");
		setListReclamation(reclamationService.getListReclamationToDispatcher());
		for (Reclamation reclamation : listReclamation) {
			logger.debug("Mail à envoyer");
			sendReclamationToInstitution(reclamation);
		}
		logger.debug("fin task!");
	}

	private void sendReclamationToInstitution(Reclamation reclamation)
	{
		String refReclamation = reclamation.getRefReclam();
		// Recipient's email ID needs to be mentioned.
		String to = reclamation.getTypeReclamation().getInstitution().getMailInstit();

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

			// Now set the actual message
			message.setText("Service: Autres Référence: Longitude: " + reclamation.getLongitude() + ", Latitude: " + reclamation.getLatitude()
					+ " Référence de la réclamation: " + refReclamation + "\nDemandeur: "
					+ reclamation.getClient().getPrenom() + " " + reclamation.getClient().getNom() + " E-mail: " + reclamation.getClient().getEmail()
					+ " Téléphone: " + reclamation.getClient().getNumTel() + "\nDemande: réclamation pour "
					+ reclamation.getTypeReclamation().getInstitution().getNomInstit() + ", type de réclamation: " + reclamation.getTypeReclamation().getType()
					+ " Date: " + reclamation.getDateReclam() + " Adresse de l'anomalie: " + reclamation.getAdresseAnomalie() + ", Autres précisions: "
					+ reclamation.getAutresPrecisions());

			// Send message
			Transport.send(message);
			reclamation.setEtatEnvoi(true);
			reclamationService.updateReclamation(reclamation);
			logger.debug("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} finally {
			logger.debug("fin send mail");
		}
	}
}