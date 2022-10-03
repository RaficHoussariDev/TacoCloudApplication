package com.example.tacocloud.configuration;

import com.example.tacocloud.models.User;
import com.example.tacocloud.repositories.user.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CustomUserAuthenticationSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if(user != null) return user;
            throw new UsernameNotFoundException("User " + username + " not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Method 1
//        return http
//                .authorizeRequests()
//                .antMatchers("/design", "/orders").hasRole("USER") //the role is ROLE_USER but the hasRole() function assumes the prefix ROLE
//                .antMatchers("/", "/**").permitAll()
//                .and()
//                .build();

        // Method 2
//        return http
//                .authorizeRequests()
//                .antMatchers("/design", "/orders").access("hasRole('USER')") //the role is ROLE_USER but the hasRole() function assumes the prefix ROLE
//                .antMatchers("/", "/**").access("permitAll()")
//                .and()
//                .build();

        // Allow users to create tacos only on Tuesdays
//        String tacoTuesday = "hasRole('User') && T(java.util.Calendar).getInstance()"
//                + ".get(T(java.util.Calendar).DAY_OF_WEEK) == T(java.util.Calendar).TUESDAY";
//
//        return http
//                .authorizeRequests()
//                .antMatchers("/design", "/orders").access(tacoTuesday) //the role is ROLE_USER but the hasRole() function assumes the prefix ROLE
//                .antMatchers("/", "/**").access("permitAll()")
//                .and()
//                .build();

        // Create customized login page
        // Add OAuth2 login
        return http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/design", "/orders").access("hasRole('USER')") //the role is ROLE_USER but the hasRole() function assumes the prefix ROLE
                .antMatchers("/", "/**").access("permitAll()")
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/design", true) // true here means that we are forcing the user to be redirected to the /design page after successful login even if he was trying to navigate elsewhere before being redirected to /login
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .build();
    }
}
