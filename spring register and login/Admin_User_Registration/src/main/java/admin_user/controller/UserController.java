package admin_user.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import admin_user.dto.UserDto;
import admin_user.service.UserService;
import admin_user.service.CustomUserDetailsService; // Ensure this import exists

@Controller
public class UserController {
 
    @Autowired
    private UserService userService;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;  // Inject CustomUserDetailsService

    // Register page
    @GetMapping("/register")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
        return "register";  // This should correspond to "register.html"
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("message", "Registered Successfully!");
        return "redirect:/login";  // Redirect to login page after successful registration
    }

    // Login page
    @GetMapping("/login")
    public String login() {
        return "login";  // Refers to "login.html"
    }

    // Home page after login
    @GetMapping("/home")
    public String homePage(Model model, Authentication authentication) {
        // Retrieve the current logged-in user and their roles
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Check if the user has ADMIN role
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        // Add user information and isAdmin attribute to the model
        model.addAttribute("user", customUserDetailsService.loadUserByUsername(username));
        model.addAttribute("isAdmin", isAdmin); // Pass the role (isAdmin) to Thymeleaf
        
        return "home";  // Corresponds to "home.html"
    }

    // Logout redirection: handled by Spring Security
    @GetMapping("/logout")
    public String logout() {
        return "login";  // Redirection will be automatically handled by Spring Security
    }

}
