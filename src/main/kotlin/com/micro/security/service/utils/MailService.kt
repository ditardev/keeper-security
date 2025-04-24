package com.micro.security.service.utils

import com.micro.security.appconfig.exception.MessagingDataException
import com.micro.security.appconfig.utility.Messages
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.UnsupportedEncodingException

@Service
class MailService(
    private val mailSender: JavaMailSender
) {

    val from = "KeeperSupport@gmail.com"
    val personal = "Keeper Support"

    fun sendMail(email: String, subject: String, message: String) {
        try{
            val mimeMessage: MimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMessage, "utf-8")
            helper.setText(message, true)
            helper.setTo(email)
            helper.setSubject(subject)
            helper.setFrom(from, personal)
            mailSender.send(mimeMessage)
        } catch (exception: MessagingException) {
            throw MessagingDataException(Messages.RESTORE_MESSAGE_ERROR)
        } catch (exception: UnsupportedEncodingException) {
            throw MessagingDataException(Messages.RESTORE_MESSAGE_ERROR)
        }

    }
}