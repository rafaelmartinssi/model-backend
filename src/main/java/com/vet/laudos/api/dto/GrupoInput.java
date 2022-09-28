package com.vet.laudos.api.dto;

import java.io.Serializable;

public class GrupoInput implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
}
