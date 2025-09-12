package com.coding.app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coding.app.exceptions.InvalidObjectException;
import com.coding.app.exceptions.NotFoundException;
import com.coding.app.models.User;
import com.coding.app.models.VerificationCode;
import com.coding.app.models.enums.EmailType;
import com.coding.app.models.enums.ServerRole;
import com.coding.app.repository.UserRepository;
import com.coding.app.repository.VerificationCodeRepository;

import jakarta.servlet.http.HttpServletRequest;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;

/**
 * Service for handling user authentication, registration, and verification.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final VerificationCodeRepository verificationCodeRepository;

    private final EmailService emailService;

    @Value("${server.dns}")
    private String serverDns;

    /**
     * Registers a new user, validates input, saves user, sends confirmation email, and authenticates.
     *
     * @param user    The user to register.
     * @param request The HTTP request for IP and context.
     * @throws InvalidObjectException if validation fails.
     */
    public void registerUser(final User user, final HttpServletRequest request) throws InvalidObjectException {

        final HashMap<String, String> errors = Utils.validate(user);
        if (usernameExists(user.getUsername())) {
            errors.put("username", "This username is already taken.");
        }
        if (errors.isEmpty()) {
            user.setEnabled(true);
            user.addRole(ServerRole.CLIENT);
            final String temporalPassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            final VerificationCode verificationCode = createConfirmationCode(user, request);
            user.getVerificationCodes().add(verificationCode);
            userRepository.save(user);
            sendConfirmationEmail(user, verificationCode);
            final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), temporalPassword));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new InvalidObjectException("The submitted data doesn't match the conventions specified.", errors);
        }
    }

    /**
     * Creates a confirmation code for email verification.
     *
     * @param user    The user to verify.
     * @param request The HTTP request for IP.
     * @return The generated VerificationCode.
     */
    private VerificationCode createConfirmationCode(final User user, final HttpServletRequest request) {
        final VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUser(user);
        verificationCode.setIp(request.getRemoteAddr());
        verificationCode.setType(EmailType.CONFIRMATION);
        verificationCode.setCode(generateCode());
        return verificationCode;
    }

    /**
     * Sends a confirmation email to the user with a verification link.
     *
     * @param user             The user to send email to.
     * @param verificationCode The verification code to include.
     */
    public void sendConfirmationEmail(final User user, final VerificationCode verificationCode) {
        final String confirmationUrl = serverDns + "/verification?code=" + verificationCode.getCode() + "&username=" + user.getUsername();
        final HashMap<String, String> bodyFields = new HashMap<>();
        bodyFields.put("name", user.getUsername());
        bodyFields.put("confirmationUrl", confirmationUrl);
        final String subject = "Complete your registration";
        final String to = user.getEmail();
        final EmailService.Email email = new EmailService.Email(to, subject, EmailType.CONFIRMATION, bodyFields);
        final Thread sendConfirmationEmail = new Thread(() -> {
            try {
                emailService.sendEmail(email);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });
        sendConfirmationEmail.start();
    }

    /**
     * Verifies a user's account using a code. If expired, sends a new code.
     *
     * @param username The username to verify.
     * @param code     The verification code.
     * @param request  The HTTP request for IP.
     * @throws NotFoundException if user or code is not found or expired.
     */
    public void verifyUser(final String username, final String code, final HttpServletRequest request) throws NotFoundException {
        final User user = userRepository.findById(username).orElseThrow(() -> new NotFoundException("User not found"));
        final List<VerificationCode> codes = verificationCodeRepository.findVerificationCodeByUser(user);
        VerificationCode codeObject = null;
        for (final VerificationCode vCode: codes) {
            if (vCode.getCode().equals(code)) {
                codeObject = vCode;
            }
        }
        if (codeObject == null){
            throw new NotFoundException("The code is invalid");
        }

        if(codeObject.isDead()){
            final VerificationCode newVerificationCode = createConfirmationCode(user, request);
            user.getVerificationCodes().add(newVerificationCode);
            userRepository.save(user);
            sendConfirmationEmail(user, newVerificationCode);
            verificationCodeRepository.delete(codeObject);
            throw new NotFoundException("The code is expired");
        }

        user.setValidated(true);
        userRepository.save(user);
        verificationCodeRepository.delete(codeObject);
    }

    // Privates Functions
    /**
     * Generates a random 4-digit code as a string.
     *
     * @return The generated code.
     */
    private String generateCode() {
        return String.valueOf(new Random().nextInt(9999));
    }

    /**
     * Checks if a username already exists.
     *
     * @param username The username to check.
     * @return true if exists, false otherwise.
     */
    private boolean usernameExists(final String username) {
        return userRepository.findById(username).isPresent();
    }
}
