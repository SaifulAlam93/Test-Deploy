package com.home.project.controller;


import com.home.project.entity.Hello;
import com.home.project.entity.Message;
import com.home.project.entity.Notification;
import com.home.project.entity.User;
import com.home.project.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    NotificationRepository notificationRepository;




    @GetMapping(value = "/send/message")
    public void publishUpdates(@RequestParam(value = "message") String msg, @RequestParam(value = "user") String user) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(msg);
        notificationRepository.save(notification);
        template.convertAndSend("/topic/greeting", notification);
    }

    @GetMapping(value = "/send/allMessage")
    public List<Notification> getAll() {
        List<Notification> notification = new ArrayList<>();
        notification = notificationRepository.findAll();
        return notification;
    }
}
