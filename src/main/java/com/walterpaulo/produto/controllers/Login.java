package com.walterpaulo.produto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.walterpaulo.produto.config.JWTToken;
import com.walterpaulo.produto.config.TokenUtil;
import com.walterpaulo.produto.models.Usuario;

@RestController
public class Login {

	@Autowired
	UserDetailsService usuario;

	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	public JWTToken fazerLogin(@RequestBody Usuario user) {

		UserDetails userMemoria = usuario.loadUserByUsername(user.getLogin());

		if (user.getLogin().equals(userMemoria.getUsername())
				&& passwordEncoder.matches(user.getSenha(), userMemoria.getPassword())) {
			return TokenUtil.encode(user);
		}
		return null;
	}

}
