package com.micro.security.appconfig

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class AppInfo {
    @Value("\${spring.application.name}")
    private val appName: String? = null

    @Value("\${spring.profiles.active}")
    private val activeProfile: String? = null

    @Value("\${server.port}")
    private val serverPort: String? = null

    @Value("\${server.ip}")
    private val serverIp: String? = null

    private val startDelimiter = "=============^_^============="
    private val stopDelimiter = "============================="

    private val ln = "\n"
    private val startTime = Date()

    @EventListener(ApplicationReadyEvent::class)
    fun printAppData() {
        val builder = startDelimiter + ln +
                "Application: " + appName + ln +
                "Configuration: " + activeProfile + ln +
                "Server port: " + serverPort + ln +
                "Server ip: " + serverIp + ln +
                "Start date time: " + Date() + ln +
                stopDelimiter
        println(builder)
    }

    @EventListener(ContextClosedEvent::class)
    fun onContextClosedEvent(contextClosedEvent: ContextClosedEvent) {
        val builder = StringBuilder()
        builder.append(startDelimiter).append(ln)
        builder.append("Start date time: ").append(Date(contextClosedEvent.timestamp)).append(ln)
        builder.append("Configuration: ").append(activeProfile).append(ln)
        builder.append(calculateUpTime(contextClosedEvent.timestamp)).append(ln)
        builder.append(stopDelimiter)
        println(builder.toString())
    }

    private fun calculateUpTime(endMilisec: Long): StringBuilder {
        var fullUpTime = (endMilisec - startTime.time) / 1000
        val days = fullUpTime / 60 / 60 / 24
        fullUpTime = fullUpTime - (days * 24 * 60 * 60)
        val hours = fullUpTime / 60 / 60
        fullUpTime = fullUpTime - (hours * 60 * 60)
        val minutes = fullUpTime / 60
        fullUpTime = fullUpTime - (hours * 60)
        val seconds = fullUpTime
        val builder = StringBuilder()
        builder.append("Full up time : ")
        builder.append(days).append(" days, ")
        builder.append(hours).append(" hours, ")
        builder.append(minutes).append(" minutes, ")
        builder.append(seconds).append(" seconds, ")
        return builder
    }
}
