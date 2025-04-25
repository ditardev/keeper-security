package com.micro.security.service

import com.hadiyarajesh.spring_security_demo.app.exception.ResourceNotFoundException
import com.micro.security.appconfig.exception.ResourceAlreadyExistException
import com.micro.security.appconfig.exception.ResourceNotConfirmedException
import com.micro.security.appconfig.utility.Messages
import com.micro.security.model.dto.RestoreDto
import com.micro.security.model.entity.RestoreEntity
import com.micro.security.repository.RestoreRepository
import com.micro.security.repository.UserRepository
import com.micro.security.service.utils.MailService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class RestoreService(
    private val restoreRepository: RestoreRepository,
    private val userRepository: UserRepository,
    private val mailService: MailService,
    private val passwordEncoder: PasswordEncoder,

    @Value("\${server.jwt.expiration_code}")
    private val codeExpiration: Long
) {

    private val codeLength: Int = 6

    fun signRestore(restoreDto: RestoreDto): ResponseEntity<*>? {
        val userEntity = userRepository.findUserEntityByEmail(restoreDto.email)
            ?: throw ResourceAlreadyExistException(Messages.USER_WITH_EMAIL + restoreDto.email + Messages.NOT_FOUND)

        if (restoreRepository.existsByEmail(userEntity.email)) {
            restoreRepository.delete(restoreRepository.findRestoreEntityByEmail(userEntity.email))
        }
        val code: String = generateCode(codeLength)
        restoreRepository.save(
            RestoreEntity(
                uuid = userEntity.uuid,
                email = userEntity.email,
                code = code,
                created = Timestamp(System.currentTimeMillis()),
                expired = Timestamp(System.currentTimeMillis() + codeExpiration)
            )
        )

        mailService.sendMail(
            restoreDto.email,
            Messages.RESTORE_CODE_SUBJECT,
            generateMessage(code)
        )

        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun verifyRestoreCode(restoreDto: RestoreDto): ResponseEntity<*> {

        if (!restoreRepository.existsByEmail(restoreDto.email)) {
            throw ResourceNotFoundException(Messages.RESTORE_ERROR_EMAIL_NOT_EXIST)
        }

        val restoreEntity = restoreRepository.findRestoreEntityByEmail(restoreDto.email)

        if (!restoreEntity.code.equals(restoreDto.code)) {
            throw ResourceNotConfirmedException(Messages.RESTORE_CODE_NOT_MATCH)
        }
        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun passwordRestore(restoreDto: RestoreDto): ResponseEntity<*> {
        if (!restoreRepository.existsByEmail(restoreDto.email)) {
            throw ResourceNotFoundException(Messages.RESTORE_ERROR_EMAIL_NOT_EXIST)
        }
        val restoreEntity = restoreRepository.findRestoreEntityByEmail(restoreDto.email)

        val userEntity = userRepository.findUserEntityByEmail(restoreEntity.email)
            ?: throw ResourceAlreadyExistException(Messages.USER_WITH_EMAIL + restoreDto.email + Messages.NOT_FOUND)

        userEntity.password = passwordEncoder.encode(restoreDto.password)

        userRepository.save(userEntity)
        restoreRepository.delete(restoreEntity)

        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun generateCode(codeLength: Int): String {
        val code: StringBuilder = StringBuilder()
        repeat(codeLength) {
            code.append((0..9).random())
        }
        return code.toString()
    }

    fun generateMessage(code: String): String {
        return Messages.RESTORE_CODE_MESSAGE +
                code + "\n" +
                Messages.RESTORE_CODE_EXPIRED +
                Messages.RESTORE_CODE_INFO
    }

}