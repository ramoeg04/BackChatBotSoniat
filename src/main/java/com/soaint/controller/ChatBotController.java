package com.soaint.controller;

import com.soaint.domain.Auth;
import com.soaint.service.ChatBotService;
import com.soaint.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/bot")
public class ChatBotController {

    private static final Logger logger = LoggerFactory.getLogger(ChatBotController.class);

    @Autowired
    private ChatBotService chatBotService;

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    public String login(@RequestBody Auth login) {
            String token = authService.login(login);
            return token;
    }

    @GetMapping(value = "/botQuestion", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    public String bot(@RequestParam String question){
        String x = chatBotService.chatbotService(question);
        return x;
    }
}
