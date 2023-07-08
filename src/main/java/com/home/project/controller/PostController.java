package com.home.project.controller;


import com.home.project.entity.Message;
import com.home.project.entity.Notification;
import com.home.project.entity.PostEntity;
import com.home.project.repository.NotificationRepository;
import com.home.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class PostController {

    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    PostRepository postRepository;
    @Autowired
    NotificationRepository notificationRepository;
    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/posts")
    List<PostEntity> all() {
        return postRepository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/posts")
    PostEntity newPost(@RequestBody PostEntity newEmployee) {

        Notification notification = new Notification();
        notification.setUser(newEmployee.getTitle());
        notification.setMessage(newEmployee.getBody());
        notificationRepository.save(notification);
        template.convertAndSend("/topic/greeting", notification);

        return postRepository.save(newEmployee);
    }

    // Single item

    @GetMapping("/posts/{id}")
    PostEntity one(@PathVariable Long id) {
        Optional<PostEntity> postEntity =  postRepository.findById(id);
        return postEntity.get();
    }

    @PutMapping("/posts/{id}")
    PostEntity replaceEmployee(@RequestBody PostEntity postEntity, @PathVariable Long id) {

        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(postEntity.getTitle());
                    post.setBody(postEntity.getBody());
                    return postRepository.save(post);
                })
                .orElseGet(() -> {
                    postEntity.setId(id);
                    return postRepository.save(postEntity);
                });
    }

    @DeleteMapping("/posts/{id}")
    void deleteEmployee(@PathVariable Long id) {
        postRepository.deleteById(id);
    }
}

