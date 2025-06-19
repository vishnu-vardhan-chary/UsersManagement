package com.example.runner;

import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataImporter implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) {
        userService.fetchAndStoreExternalUsers();
    }
}
