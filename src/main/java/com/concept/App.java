package com.concept;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.concept.email.GEmailSender;

public class App {
	public static void main(String[] args) {
		GEmailSender emailSender = new GEmailSender();
		List<String> toList = Arrays.asList("wswasim9292@gmail.com","pwasimshaikh551@gmail.com","wasimshaikhashraf000@gmail.com");

		String from = "wasimsk6061@gmail.com";
		String subject = "Application for Job Opportunity";
		String text = "Dear Hiring Manager, \n\nPlease find attached my resume and job descriptions. Thank you.";
		String resumePath = "F:\\Wasim Data\\CV\\skwasim.pdf";

		ExecutorService executorService = Executors.newFixedThreadPool(100); // You can adjust the thread pool size

		try {
			// ...

			for (String to : toList) {
				Future<Boolean> future = executorService.submit(() -> {
					List<String> toListForEmail = Arrays.asList(to);
					return emailSender.sendEmailWithAttachment(toListForEmail, from, subject, text, resumePath);
				});

				// rest of your code

				// ...

				// You can use the result if needed
				Boolean sendEmail = future.get();

				if (sendEmail) {
					System.out.println("Email to " + to + " has been submitted successfully...");
				} else {
					System.out.println("There is a problem in submitting email to " + to);
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
			try {
				if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
					executorService.shutdownNow();
				}
			} catch (InterruptedException ex) {
				executorService.shutdownNow();
				Thread.currentThread().interrupt();
			}
		}
	}
}
