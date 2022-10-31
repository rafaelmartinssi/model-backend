package com.vet.laudos.api.dto.input;

import java.io.Serializable;

public class SenhaInput implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String senhaAtual;
	private String novaSenha;
	public String getSenhaAtual() {
		return senhaAtual;
	}
	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

}
