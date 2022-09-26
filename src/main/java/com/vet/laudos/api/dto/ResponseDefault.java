package com.vet.laudos.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vet.laudos.domain.model.Info;

public class ResponseDefault <T extends Serializable> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Object content;
	private List<Info> infos = new ArrayList<>();
	
	public ResponseDefault (Object content) {
		this.content = content;
		Info info = new Info();
		info.setCodigo(0);
		info.setDescricao("Operação realizada com sucesso");
		infos.add(info);
	}
	
	public ResponseDefault (List<Info> infos) {
		this.infos = infos;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public List<Info> getInfos() {
		return infos;
	}

	public void setInfos(List<Info> infos) {
		this.infos = infos;
	}

}
