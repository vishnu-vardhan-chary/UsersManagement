package com.example.service.impl;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void fetchAndStoreExternalUsers() {
        String url = "https://dummyjson.com/users";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("users")) {
            List<Map<String, Object>> users = (List<Map<String, Object>>) response.get("users");

            List<User> userList = users.stream().map(u -> User.builder()
                    .id(Long.valueOf(u.get("id").toString()))
                    .firstName(u.get("firstName").toString())
                    .lastName(u.get("lastName").toString())
                    .name(u.get("firstName").toString() + " " + u.get("lastName").toString())
                    .gender(u.get("gender").toString())
                    .email(u.get("email").toString())
                    .phone(u.get("phone").toString())
                    .username(u.get("username").toString())
                    .password(u.get("password").toString())
                    .role(u.get("role").toString())
                    .image(u.get("image").toString())
                    .age(Integer.parseInt(u.get("age").toString()))
                    .build()).toList();

            userRepository.saveAll(userList);
            log.info("Users saved: " + userList.size());
        } else {
            log.warn("Failed to fetch users from external API");
        }
    }
}
