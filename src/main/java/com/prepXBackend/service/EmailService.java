package com.prepXBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);

            String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px;'>"
                    + "<h2 style='color: orange; text-align: center;'>PrepEx</h2>"
                    + "<p style='font-size: 16px; text-align: center;'>"
                    + "Thank you for coming back to us to enrich your capability in learning!"
                    + "</p>"
                    + "<div style='display: flex; justify-content: center; align-items: center; margin: 20px 0;'>"
                    + "<img src='cid:prepexImage' style='width: 80%; max-width: 500px; height: auto; border-radius: 12px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);' alt=''/>"
                    + "</div>"
                    + "<div style='text-align: center; background-color: #f9f9f9; padding: 20px; border-radius: 8px;'>"
                    + "<p style='font-size: 16px; color: #333;'>" + text + "</p>"
                    + "</div></div>";

            helper.setText(htmlContent, true);

            // ✅ Use ClassPathResource instead of File
            ClassPathResource image = new ClassPathResource("static/images/email.jpg");
            helper.addInline("prepexImage", image);

            mailSender.send(message);
            System.out.println("📩 Email sent successfully to " + to);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to send email!", e);
        }
    }



    public void sendOtp(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Your OTP Code");

            String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px;'>"
                    + "<h2 style='color: orange; text-align: center;'>PrepEx</h2>"
                    + "<p style='font-size: 16px; text-align: center; margin-bottom: 30px;'>"
                    + "Thank you for <b>Being Part Of Our Family</b> to enrich your capability!"
                    + "</p>"
                    + "<div style='text-align: center; background-color: #f5f5f5; padding: 20px; border-radius: 8px;'>"
                    + "<p style='font-size: 18px; font-weight: bold;'>Your OTP is:</p>"
                    + "<p style='font-size: 32px; font-weight: bold; color: #333;'>" + otp + "</p>"
                    + "</div></div>";

            helper.setText(htmlContent, true); // 'true' means HTML

            mailSender.send(message);
            System.out.println("📩 OTP sent successfully to " + email);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to send OTP email!", e);
        }
    }

}
