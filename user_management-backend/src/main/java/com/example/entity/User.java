package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    private Long id;

    private String name;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phone;
    private String image;
    private String username;
    private String password;
    private String role;
    private Integer age;
}
