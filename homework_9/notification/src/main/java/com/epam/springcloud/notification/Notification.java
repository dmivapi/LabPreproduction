package com.epam.springcloud.notification;

import lombok.Data;

@Data
public class Notification {
    String user;
    Notifier notifyBy = Notifier.EMAIL;

    enum Notifier {
        EMAIL
    }
}
