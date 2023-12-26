package com.concept.email;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class GEmailSender {

	public boolean sendEmailWithAttachment(List<String> toList, String from, String subject, String text,
			String attachmentPath) {
		boolean flag = false;

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.host", "smtp.gmail.com");

		final String userName = "wasimsk6061@gmail.com"; // Update with your Gmail email address
		final String password = "zvxt lsoh lklt bncg"; // Update with the App Password generated for your Gmail account

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setSubject(subject);

			// Create the email body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(text);

			// Create the file attachment part
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource("F:\\Wasim Data\\CV\\skwasim.pdf");
			attachmentBodyPart.setDataHandler(new DataHandler(source));
			attachmentBodyPart.setFileName(new File(attachmentPath).getName());

			// Create the multipart message and add parts
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textBodyPart);
			multipart.addBodyPart(attachmentBodyPart);

			// Set the multipart message to the main message
			message.setContent(multipart);
			for (String to : toList) {
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				Transport.send(message);
			}
			// Send the email

			flag = true;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return flag;
	}
}
