package com.coding.app.services;

import com.coding.app.models.enums.EmailType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Service for sending emails, including templated emails for different types.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${server.dns}")
    public static String serverLink;

    private final JavaMailSender mailer;

    /**
     * Sends an email using the provided Email record.
     *
     * @param email The email to send.
     * @throws MessagingException if sending fails.
     */
    public void sendEmail(final Email email) throws MessagingException {
        final MimeMessage message = mailer.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message);


        helper.setFrom(fromEmail);
        helper.setTo(email.to);
        helper.setSubject(email.subject);
        helper.setText(email.generateMessage(), true);

        mailer.send(message);
    }

    /**
     * Record representing an email with templating support.
     */
    public record Email(String to, String subject, EmailType type, Map<String, String> bodyFields) {

        /**
         * Generates the email message by loading and filling the template.
         *
         * @return The generated HTML message.
         */
        public String generateMessage() {
            final ResourceLoader resourceLoader = new DefaultResourceLoader();
            try {
                final var resource = resourceLoader.getResource(type.getMailTemplate());
                try (final var in = resource.getInputStream()) {
                    String html = new String(in.readAllBytes());
                    for (final var entry : bodyFields.entrySet()) {
                        html = html.replace("${" + entry.getKey() + "}", entry.getValue());
                    }
                    return html;
                }
            } catch (final IOException e) {
                return "<p>Unable to load email template.</p>";
            }
        }
    }

}