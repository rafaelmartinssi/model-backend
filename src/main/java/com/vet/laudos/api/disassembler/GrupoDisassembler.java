package com.vet.laudos.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vet.laudos.api.dto.input.GrupoInput;
import com.vet.laudos.domain.model.Grupo;

@Controller
public class GrupoDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainObject (GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}
	
}
