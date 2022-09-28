package com.example.tacocloud.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InMemorySecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // In memory user store that can be used instead of the registration form and controller
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        List<UserDetails> usersList = new ArrayList<>();
//
//        User buzz = new User(
//                "buzz",
//                encoder.encode("password"),
//                List.of(new SimpleGrantedAuthority("ROLE_USER"))
//        );
//
//        User woody = new User(
//                "woody",
//                encoder.encode("password"),
//                List.of(new SimpleGrantedAuthority("ROLE_USER"))
//        );
//
//        usersList.add(buzz);
//        usersList.add(woody);
//
//        return new InMemoryUserDetailsManager(usersList);
//    }
}
