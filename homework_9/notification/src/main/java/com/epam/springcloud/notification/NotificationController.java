package com.epam.springcloud.notification;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping
@RestController
public class NotificationController {
    private final Set<Notification> notifications = new HashSet<>();

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{user}")
    public Notification notify(@PathVariable String user) {
        Notification notification = new Notification();
        notification.setUser(user);
        notifications.add(notification);
        return notification;
    }

    @GetMapping
    public List<Notification> getNotifications() {
        return Arrays.asList(notifications.toArray(new Notification[0]).clone());
    }
}
