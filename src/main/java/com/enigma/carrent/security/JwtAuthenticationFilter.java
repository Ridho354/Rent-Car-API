package com.enigma.carrent.security;

import com.enigma.carrent.entity.UserAccount;
import com.enigma.carrent.service.JwtService;
import com.enigma.carrent.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static String AUTH_HEADER = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerTokenFromAuthHeader = request.getHeader(AUTH_HEADER);
            if (bearerTokenFromAuthHeader != null && jwtService.verifyJwtToken(bearerTokenFromAuthHeader)) {
                String userId = jwtService.getUserIdByToken(bearerTokenFromAuthHeader);
                UserAccount userAccount = userService.getByUserId(userId);

                if (userAccount != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAccount, null, userAccount.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request)); 
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}
