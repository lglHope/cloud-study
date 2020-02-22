package liu.hope.my_demo_boot.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class JavaMailUtil {

    private static final String PNG_PATH = "/Volumes/Backup Plus/workspace/ideaWorkSpace/cloud-study/mybaits-0/target/test-classes/static/images/friend.png";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 简单邮件
     * @throws Exception
     */
    public void sendSimpleMail() throws Exception {
        // 简单邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("l1911739019@qq.com");
        message.setTo("lglhope1995@163.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");

        mailSender.send(message);
    }

    /**
     * 附件邮件
     * @throws Exception
     */
    public void sendAttachmentsMail() throws Exception {

        // 可带附件的邮件发送
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("l1911739019@qq.com");
        helper.setTo("lglhope1995@163.com");
        helper.setSubject("主题：有附件");
        helper.setText("有附件的邮件");

        FileSystemResource file = new FileSystemResource(new File(PNG_PATH));
        helper.addAttachment("附件-1.png", file);
        helper.addAttachment("附件-2.png", file);

        mailSender.send(mimeMessage);
    }

    /**
     * 内嵌邮件
     * @throws Exception
     */
    public void sendInlineMail() throws Exception {

        // 邮件嵌入静态资源而不是附件的形式
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("l1911739019@qq.com");
        helper.setTo("lglhope1995@163.com");
        helper.setSubject("主题：嵌入静态资源");
        helper.setText("<html><body><img src=\"cid:friend\" ></body></html>", true);

        FileSystemResource file = new FileSystemResource(new File(PNG_PATH));
        // 这里需要注意的是addInline函数中资源名称friend需要与正文中cid:friend对应起来
        helper.addInline("friend", file);

        mailSender.send(mimeMessage);
    }

    /**
     * 模板邮件
     * @throws Exception
     */
    public void sendTemplateMail() throws Exception {

        // 通常我们使用邮件发送服务的时候，都会有一些固定的场景，
        // 比如重置密码、注册确认等，给每个用户发送的内容可能只有小部分是变化的。
        // 所以，很多时候我们会使用模板引擎来为各类邮件设置成模板，
        // 这样我们只需要在发送时去替换变化部分的参数即可。
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("l1911739019@qq.com");
        helper.setTo("lglhope1995@163.com");
        helper.setSubject("主题：模板邮件");

        Map<String, Object> model = new HashMap<>();
        model.put("username", "didi");
        Configuration contextBean = applicationContext.getBean(Configuration.class);
        Template template = contextBean.getTemplate("temp.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        helper.setText(text, true);

        mailSender.send(mimeMessage);
    }

}