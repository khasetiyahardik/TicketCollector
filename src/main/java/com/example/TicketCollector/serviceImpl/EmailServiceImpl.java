package com.example.TicketCollector.serviceImpl;

import com.example.TicketCollector.dto.PaymentDTO;
import com.example.TicketCollector.entity.BookTicketEntity;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.example.TicketCollector.entity.UserEntity;
import com.example.TicketCollector.entity.WaitingListEntity;
import com.example.TicketCollector.exception.RazorpayCustomException;
import com.example.TicketCollector.repository.WaitingListRepository;
import com.example.TicketCollector.service.EmailService;
import com.razorpay.QrCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {


    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    WaitingListRepository waitingListRepository;


    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    Session session;

    @Override
    public void sendVerifyEmail(UserEntity userEntity) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(email);
            helper.setTo(userEntity.getEmail());
            helper.setSubject("verify");
            helper.setText("Click here to verify " + "http://localhost:9999/user/verify?email=" + userEntity.getEmail() + "&password=" + userEntity.getPassword());
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("Problem occured while sending email" + e);
        }
    }

    @Override
    public void sendTicket(BookTicketEntity bookTicketEntity, TravellingModeEntity travellingModeEntity, String pdfPath, String QrCodeImagePath) throws MessagingException {
        File qrCodeFile = null;
        try {
            qrCodeFile = new File("qrCode.png");

            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(email);
            helper.setTo(bookTicketEntity.getEmail());
            helper.setSubject("ticket");
            helper.setText("Your ticket is confirmed");

            log.info("Path for QR ::" + QrCodeImagePath);
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Please find the attached QR code.");
            multipart.addBodyPart(textPart);
            MimeBodyPart attachmentPart = new MimeBodyPart();

            URL qrCodeUrl = new URL(QrCodeImagePath);
            FileUtils.copyURLToFile(qrCodeUrl, qrCodeFile);

            DataSource source = new FileDataSource(qrCodeFile);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName("qrcode.png");
            multipart.addBodyPart(attachmentPart);


            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            helper.addAttachment("ticket.pdf", new File(pdfPath));
            Transport.send(message);
        } catch (Exception e) {
            System.out.println("Problem occured while sending email" + e);
        } finally {
            new File(qrCodeFile.toURI()).delete();
        }
    }

    @Override
    public void sendAvailabilityEmail(WaitingListEntity waitingListEntity) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(email)
            ;
            helper.setTo(waitingListEntity.getEmail());
            helper.setSubject("Ticket Availability");
            helper.setText("Requested tickets now available please book now");
            ClassPathResource pdf = new ClassPathResource("D://excel/" + "ticket.pdf");
            helper.addAttachment("ticket.pdf", pdf);
            Transport.send(message);

        } catch (Exception e) {
            System.out.println("Problem occured while sending email");
        }
    }

    @Override
    public void sendRazorpayUpiQR(QrCode qrCode, PaymentDTO dto) throws MessagingException {
        File qrCodeFile = new File("qrCode.png");
        try {
            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(email);

            helper.setTo(dto.getEmail());
            helper.setSubject("ticket");
            helper.setText("Your ticket is confirmed");

            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Make payment using attached QR code.");
            multipart.addBodyPart(textPart);
            MimeBodyPart attachmentPart = new MimeBodyPart();

            URL qrCodeUrl = new URL(qrCode.get("image_url"));
            FileUtils.copyURLToFile(qrCodeUrl, qrCodeFile);

            DataSource source = new FileDataSource(qrCodeFile);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName("qrcode.png");
            multipart.addBodyPart(attachmentPart);

            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (Exception e) {
            throw new RazorpayCustomException("something went wrong while sending qr email" + e.getMessage());
        } finally {
            new File(qrCodeFile.toURI()).delete();
        }
    }

}
