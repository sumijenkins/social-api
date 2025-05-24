package com.example.social_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;
import com.example.social_api.repository.UserRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
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

        if (apiKey == null || userRepository.findByApiKey(apiKey).isEmpty()){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid or missing API key.");
            return;
        }
        chain.doFilter(request, response);

    }


}
