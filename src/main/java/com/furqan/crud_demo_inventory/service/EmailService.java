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

    public void sendVerificationEmail(User user, String siteURL) {
        String subject = "Verify your registration";
        String senderName = "Product Management Portal";
        String verifyURL = siteURL + "/verify?token=" + user.getVerificationToken();

        String content = "Dear " + user.getFirstName() + ",<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>"
                + "Thank you,<br>Product Management Portal";

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("itxmfurqan@email.com", senderName);
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

