package dev.amitkumar.portfolio.security;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Extension point for per-route rate limits (contact form, GitHub proxy).
 * Phase 1 is intentionally a pass-through. Do not introduce Redis here.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 20)
public class RateLimitFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // TODO(phase-2): enforce in-memory limits for POST /api/v1/contact and GitHub proxy reads.
        filterChain.doFilter(request, response);
    }
}
