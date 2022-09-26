package com.vet.laudos.domain.model;

import java.io.Serializable;

public class Info implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	private String descricao;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
