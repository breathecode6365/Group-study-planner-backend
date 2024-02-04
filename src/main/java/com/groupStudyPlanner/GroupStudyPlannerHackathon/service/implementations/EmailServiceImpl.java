package com.groupStudyPlanner.GroupStudyPlannerHackathon.service.implementations;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupInvitationDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.BasicResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.exception.ApiRuntimeException;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.EmailService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.utility.CommonConstants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    private final String senderEmailId;

    private final Configuration config;

    public EmailServiceImpl(JavaMailSender javaMailSender, @Value("${spring.mail.username}") String senderEmailId, Configuration config) {
        this.javaMailSender = javaMailSender;
        this.senderEmailId = senderEmailId;
        this.config = config;
    }
    public BasicResDTO sendInvitationEmail(GroupInvitationDAO invitationDAO, UserDAO Sender, UserDAO Receiver) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
//            // add attachment
//            helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

            Template t = config.getTemplate("InvitationMailTemplates.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, Map.of("name", Receiver.getName(),"group",invitationDAO.getInvitedTo().getName(), "senderName", Sender.getName(), "token", invitationDAO.getInvId()));

            helper.setTo(Receiver.getEmailId());
            helper.setText(html, true);
            helper.setSubject("<Test>Invitation to join Group");
            javaMailSender.send(message);
            return new BasicResDTO(CommonConstants.MAIL_SUCCESS, HttpStatus.OK);
            //this is not working
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            throw new ApiRuntimeException(CommonConstants.MAIL_ERROR, HttpStatus.BAD_REQUEST);
        }
    }
}
