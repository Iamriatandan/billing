package com.payement.payment_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendInvoiceEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your_email@gmail.com");   // your verified Gmail
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    // Send email with PDF attachment
    public void sendInvoiceWithAttachment(String toEmail, String subject, String body, byte[] pdfBytes, String filename)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("your_email@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body);

        ByteArrayResource pdfResource = new ByteArrayResource(pdfBytes);
        helper.addAttachment(filename, pdfResource);

        mailSender.send(message);
    }
}
