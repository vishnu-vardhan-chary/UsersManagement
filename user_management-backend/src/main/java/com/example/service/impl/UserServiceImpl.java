package com.example.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.entity.User;
import com.example.exception.ExternalApiException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserRepository;
import com.example.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void fetchAndStoreExternalUsers() {
        final String url = "https://dummyjson.com/users";
        RestTemplate restTemplate = new RestTemplate();

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("users")) {
                throw new ExternalApiException("No 'users' key found in response from external API");
            }

            List<Map<String, Object>> users = (List<Map<String, Object>>) response.get("users");

            List<User> userList = users.stream().map(u -> {
                try {
                    return User.builder()
                            .id(Long.parseLong(u.get("id").toString()))
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
                            .build();
                } catch (Exception e) {
                    log.error("Error mapping user: {}", u, e);
                    return null;
                }
            }).filter(user -> user != null).toList();

            userRepository.saveAll(userList);
            log.info("Users saved successfully. Total: {}", userList.size());

        } catch (ExternalApiException e) {
            log.error("External API Error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch or store users", e);
            throw new ExternalApiException("Failed to fetch or store users: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> searchUsers(String query) {
        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }
}
