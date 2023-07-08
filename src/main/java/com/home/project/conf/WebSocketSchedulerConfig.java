package com.home.project.conf;

//import com.home.project.controller.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class WebSocketSchedulerConfig {

    @Autowired
    private SimpMessagingTemplate template;

//    @Autowired
//    private GreetingService greetingService;

//    @Scheduled(fixedRate = 3000)
//    public void publishUpdates() {
//        System.out.println("Message: " + greetingService.getMessage().getMsg());
//        template.convertAndSend("/topic/greeting", greetingService.getMessage());
//    }

}
