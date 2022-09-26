package com.vet.laudos.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vet.laudos.api.dto.UsuarioInput;
import com.vet.laudos.domain.model.Usuario;

@Component
public class UsuarioDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject (UsuarioInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
}
