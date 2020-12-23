package com.pigorv.springcloud.orders;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "users", fallback = UserClient.UserClientImpl.class)
public interface UserClient {
    @GetMapping("/{userName}")
    void getUser(@PathVariable String userName);

    @Component
    class UserClientImpl implements UserClient {
        @Override
        public void getUser(String userName) {
        }
    }
}
