package com.cyber.email.tool;

import com.cyber.core.tool.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.concurrent.Executor;

/**
 * @author cyber
 * @Description 邮箱相关自动配置
 * @date 2022年8月15日
 * @Version 1.0
 */
public class CyberMailServiceImpl implements CyberMailService {
    @Autowired
    public JavaMailSender javaMailSender;

    @Autowired
    @Qualifier("asyncExecutor")
    private Executor asyncExecutor;

    @Override
    public void send(EmailBO emailBO) {

        asyncExecutor.execute(() -> {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                handleEmailHelper(emailBO, mimeMessage);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void send(EmailBO emailBO, File[] file) {
        asyncExecutor.execute(() -> {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = handleEmailHelper(emailBO, mimeMessage);
                // 添加附件
                for (File f : file) {
                    InputStreamSource fileSystemResource = new FileSystemResource(f);
                    messageHelper.addAttachment(f.getName(), fileSystemResource);
                }
                javaMailSender.send(mimeMessage);
            } catch (Exception e) {
                //存入记录
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置基础邮件信息
     * @param emailBO emailBO
     * @param mimeMessage mimeMessage
     * @return MimeMessageHelper
     * @throws MessagingException MessagingException
     */
    private MimeMessageHelper handleEmailHelper(EmailBO emailBO, MimeMessage mimeMessage) throws MessagingException {
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true, "UTF-8");
        messageHelper.setFrom(emailBO.getFrom());
        messageHelper.setTo(emailBO.getTo());
        if (StringUtil.isNotBlank(emailBO.getCc())){
            messageHelper.setCc(emailBO.getCc());
        }
        messageHelper.setSubject(emailBO.getSubject());
        messageHelper.setText(emailBO.getContent(), true);
        return messageHelper;
    }
}
