package com.walterpaulo.produto.config;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.walterpaulo.produto.models.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class TokenUtil {
	private static final int SEGUNDOS = 1000;
	private static final int MINUTOS = 60 * SEGUNDOS;
	private static final int HORAS = 60 * MINUTOS;
	private static final int DIAS = 24 * HORAS;
	private static final int EXPIRATION = 3 * DIAS;
	private static final String SECRET_KEY = "7014a79eb7a81cf37542a62b75defa99427580e6612f956d47caa0fe0ec5d05e";

	private static final String PREFIX = "Bearer ";

	public static JWTToken encode(Usuario usuario) {
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		String jws = Jwts.builder().setSubject(usuario.getLogin()).setIssuer("walter")
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(key, SignatureAlgorithm.HS256).compact();
		return new JWTToken(PREFIX + jws);
	}

	public static UsernamePasswordAuthenticationToken decodeToken(HttpServletRequest request) {
		try {
			String token = request.getHeader("Authorization");
			token = token.replace(PREFIX, "");

			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build()
					.parseClaimsJws(token);
			String subject = claims.getBody().getSubject();
			String issuer = claims.getBody().getIssuer();
			Date exp = claims.getBody().getExpiration();

			if (isValid(subject, issuer, exp)) {
				return new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return null;
	}

	private static boolean isValid(String subject, String issuer, Date exp) {
		return subject != null && issuer.equals("walter") && exp.after(new Date(System.currentTimeMillis()));
	}
}
