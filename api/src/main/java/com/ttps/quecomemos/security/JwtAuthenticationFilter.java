package com.ttps.quecomemos.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttps.quecomemos.dto.ApiResponseDTO;
import com.ttps.quecomemos.services.JwtService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(filterName = "JwtFilter", urlPatterns = "*")
public class JwtAuthenticationFilter implements Filter {

  @Autowired
  private JwtService jwtService;

  private static final String[] PUBLIC_ENDPOINTS = { "/api/users/login",
      "/api/users/register", "/initialize-data" };

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String authHeader = httpRequest.getHeader("Authorization");
    try {

      if (Arrays.stream(PUBLIC_ENDPOINTS).anyMatch(httpRequest.getRequestURI()::contains)
          || HttpMethod.OPTIONS.matches(httpRequest.getMethod())) {
        chain.doFilter(httpRequest, response);
        return;
      }

      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        if (jwtService.validateToken(token)) {
          String dni = jwtService.extractDNI(token);
          if (dni != null) {
            request.setAttribute("dni", jwtService.extractDNI(token));
            request.setAttribute("dni", dni);
            chain.doFilter(request, response); // Continúa la cadena de filtros
            return;
          }
        } else {
          log.warn("Invalid token in request: {}", authHeader);
        }
      } else {
        log.warn("Missing or invalid Authorization header");
      }

      ApiResponseDTO<String> unauthorizedResponseDTO = new ApiResponseDTO<>(null,
          "Unauthorized: Invalid or missing token", HttpStatus.UNAUTHORIZED);

      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      httpResponse.setContentType("application/json");
      new ObjectMapper().writeValue(httpResponse.getWriter(), unauthorizedResponseDTO);

    } catch (ServletException | IOException e) {
      log.error("Error during JWT filtering: {}", e.getMessage());
      throw new RuntimeException("Failed to process JWT filter", e);
    }
  }

}
