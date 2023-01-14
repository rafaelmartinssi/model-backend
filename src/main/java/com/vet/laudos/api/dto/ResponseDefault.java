package com.vet.laudos.api.dto;

import java.io.Serializable;

import com.vet.laudos.domain.model.Info;

public class ResponseDefault <T extends Serializable> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Object content;
	private Info info = new Info();
	
	public ResponseDefault (Object content) {
		this.content = content;
		Info info = new Info();
		info.setCodigo(0);
		info.setDescricao("Operação realizada com sucesso");
	}
	
	public ResponseDefault (Info info) {
		this.info = info;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfos(Info info) {
		this.info = info;
	}

}
