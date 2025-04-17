package com.micro.security.controller

import lombok.AllArgsConstructor
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@AllArgsConstructor
@RequestMapping("\${server.endpoint.user}")
class UserController {
}