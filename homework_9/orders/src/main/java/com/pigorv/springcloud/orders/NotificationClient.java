package com.pigorv.springcloud.orders;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "notifications", fallback = NotificationClient.NotificationClientImpl.class)
public interface NotificationClient {

    @PostMapping("/{user}")
    void notify(@PathVariable String user);

    @Component
    class NotificationClientImpl implements NotificationClient {
        @Override
        public void notify(String user) {
        }
    }
}
