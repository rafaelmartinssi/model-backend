package com.vet.laudos.core.security.AuthorizationServer;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;

import com.vet.laudos.domain.model.Usuario;

public class AuthUser extends User {
	
	private static final long serialVersionUID = 1L;
	
	private String fullName;
	
	public AuthUser(Usuario usuario) {
		super(usuario.getEmail(), usuario.getSenha(), Collections.emptyList());
		
		this.fullName = usuario.getNome();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
