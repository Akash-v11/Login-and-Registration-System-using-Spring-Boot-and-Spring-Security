package admin_user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import admin_user.model.User;
import admin_user.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;  // Ensure this is your User repository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user from the database based on email
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Get user roles and convert them into Spring Security authorities
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        // Return user details with username, password, and authorities
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of(authority));
    }
} 

