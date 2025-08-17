package com.furqan.crud_demo_inventory.service;

import com.furqan.crud_demo_inventory.entity.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Existing method for verification
    public void sendVerificationEmail(User user, String siteURL) {
        String subject = "Verify your registration";
        String senderName = "Product Management Portal";
        String verifyURL = siteURL + "/verify?token=" + user.getVerificationToken();

        String content = "Dear " + user.getFirstName() + ",<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>"
                + "Thank you,<br>Product Management Portal";

        sendHtmlEmail(user.getEmail(), subject, content, senderName);
    }

    // ✅ New method for forgot password
    public void sendPasswordResetEmail(User user, String siteURL) {
        String subject = "Password Reset Request";
        String senderName = "Product Management Portal";
        String resetURL = siteURL + "/reset-password?token=" + user.getResetToken();

        String content = "Dear " + user.getFirstName() + ",<br>"
                + "You have requested to reset your password.<br>"
                + "Click the link below to reset it:<br>"
                + "<h3><a href=\"" + resetURL + "\">RESET PASSWORD</a></h3>"
                + "If you did not request this, ignore this email.<br><br>"
                + "Thank you,<br>Product Management Portal";

        sendHtmlEmail(user.getEmail(), subject, content, senderName);
    }

    // ✅ Common private method to avoid repeating code
    private void sendHtmlEmail(String to, String subject, String content, String senderName) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("itxmfurqan@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
