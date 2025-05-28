package com.example.social_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.social_api.repository.UserRepository;
import com.example.social_api.model.User;

import java.io.IOException;
import java.util.Collections;

public class ApiKeyFilter extends OncePerRequestFilter {

    private final String HEADER_NAME = "X-API-Key";
    private final UserRepository userRepository;

    public ApiKeyFilter(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
            throws IOException, ServletException {

        String apiKey = request.getHeader(HEADER_NAME);
        System.out.println("API Key gelen: " + apiKey);

        String path = request.getRequestURI();
        if (path.startsWith("/api/users")) {
            chain.doFilter(request, response);
            return;
        }

        if (apiKey == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Missing API key.");
            return;
        }

        var userOpt = userRepository.findByApiKey(apiKey);
        boolean exists = userOpt.isPresent();
        System.out.println("Key veritabanında var mı? " + exists);

        if (!exists) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid API key.");
            return;
        }

        // API key geçerli, Authentication objesi oluştur ve SecurityContext'e ekle
        User user = userOpt.get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // basit rol ataması
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
