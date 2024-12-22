package admin_user.service;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // Log to check the authentication success handler is triggered
        System.out.println("Authentication successful: " + authentication.getName());

        var authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        boolean isUser = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("USER"));

        if (isAdmin) {
            response.sendRedirect("/home");  // Redirect to the home page for admin
        } else if (isUser) {
            response.sendRedirect("/home");  // Redirect to the home page for user
        } else {
            response.sendRedirect("/error");  // Handle error cases if needed
        }
    }
}
