package org.login.service;

import android.telephony.SmsManager;
import org.login.entity.LogReg;
import org.login.repository.LogregRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class LogRegServiceImpl implements LogRegService{

    @Autowired
    private LogregRepo lre;

    @Override
    public LogReg saveUser(LogReg lr) {
        return lre.save(lr);
    }

    @Override
    public List<LogReg> checkUser(String username, String emailID, Long number) {
        List<LogReg> res = lre.findByUsername(username, emailID, number);
        return res;
    }

    @Override
    public List<LogReg> getUserByUsernamePassword(String user, String pass) {
        List<LogReg> res = lre.getUserByUsernamePassword(user, pass);
        return res;
    }

    @Override
    public LogReg getVerificationDetails(String username) {
        List<LogReg> res = lre.findByUsernameOnly(username);
        return res.get(0);
    }

    @Override
    public void sendOTPByEmail(String email, Long otp) {
        final String username = "examnotes007@gmail.com"; // Your email address
        final String password = "vibfgobhcefmkjor"; // Your email password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("OTP Verification");
            message.setText("Your OTP code is: " + otp);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Replace these values with your actual Twilio credentials

    @Override
    public void sendOTPBySMS(String phoneNumber, Long otp) {
        String recipientNumber = "+91"+phoneNumber;

        // Get the message to be sent
        String message = "Your OTP to login is : "+otp;

        // Create a new instance of the SmsManager class
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(recipientNumber, "ExamNotes", message, null, null);
    }

}
