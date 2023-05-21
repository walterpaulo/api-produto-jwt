package com.walterpaulo.produto.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walterpaulo.produto.controllers.Response;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WebFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getHeader("Authorization") != null) {
			Authentication auth = TokenUtil.decodeToken(request);
			if (auth != null)
				SecurityContextHolder.getContext().setAuthentication(auth);
			else {
				this.response(response, "Token Inválido");
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private HttpServletResponse response(HttpServletResponse response, String msg)
			throws JsonProcessingException, IOException {
		Response<Object> erro = new Response<>();
		erro.setContent(msg);
		erro.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setStatus(erro.getStatus());
		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().print(mapper.writeValueAsString(erro));
		response.getWriter().flush();
		return response;
	}
}
