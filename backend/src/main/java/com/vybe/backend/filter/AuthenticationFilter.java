package com.vybe.backend.filter;

import com.vybe.backend.exception.UsernameTakenException;
import com.vybe.backend.model.entity.User;
import com.vybe.backend.util.JwtTokenUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasLength;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private HandlerExceptionResolver handlerExceptionResolver;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.contains("/auth");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Get jwt token
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (!hasLength(header) || !header.startsWith("Bearer ")) {
                throw new UsernameTakenException("No token found");
            }
            final String token = header.split(" ")[1].trim();

            // Validate token & get user
            User user = jwtTokenUtil.getUserFromToken(token);
            if (user == null) {
                throw new AuthenticationCredentialsNotFoundException("");
            }

            if (!user.isEnabled()) {
                throw new AuthenticationCredentialsNotFoundException("");
            }

            // Set security context
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user, null,
                    user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception ignore) {
            // It is enough not to set the security context. There is no need to do anything here.
        }

        filterChain.doFilter(request, response);
    }
}
