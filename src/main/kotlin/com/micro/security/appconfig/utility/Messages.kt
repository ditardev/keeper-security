package com.micro.security.appconfig.utility

object Messages {
    const val USERNAME_IN_USE = " is already in use, please use different UserName."
    const val EMAIL_IN_USE = " is already in use, please use different Email."
    const val USER_WITH_USERNAME = "User with username: "
    const val USER_WITH_EMAIL = "User with email: "
    const val NOT_FOUND = " not found."
    const val BANNED = " was blocked"
    const val NOT_CONFIRMED = " not confirmed"
    const val ACCESS_DENIED = "Access denied"
    const val PASSWORD_MATCH = "the new password is identical to the previous one"

    const val JWT_MALFORMED = "JWT token is malformed."
    const val JWT_EXPIRED = "JWT token is expired."
    const val JWT_VALIDATION_FAILED = "JWT token validation failed."

    const val RESTORE_CODE_SUBJECT = "Keeper service password recovery"
    const val RESTORE_CODE_MESSAGE = "To complete the process of changing your password and confirming your email, enter the following code:" + "\n"
    const val RESTORE_CODE_EXPIRED = "The code is valid for several minutes." + "\n"
    const val RESTORE_CODE_INFO = "If you did not initiate registration, simply ignore this email." + "\n"
    const val RESTORE_CODE_NOT_MATCH = "Verification code does not match"

    const val RESTORE_MESSAGE_ERROR = "An error occurred while sending the letter"
    const val RESTORE_MESSAGE_SUCCESS = "The letter was sent successfully"

    const val RESTORE_ERROR_EMAIL_NOT_EXIST = "Email does not exist."



}