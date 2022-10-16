package com.vet.laudos.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vet.laudos.api.dto.output.UsuarioOutput;
import com.vet.laudos.domain.model.Usuario;

@Component
public class UsuarioAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioOutput toModel (Usuario usuario) {
		return modelMapper.map(usuario, UsuarioOutput.class);
	}
	
	public List<UsuarioOutput> toCollectionModel (List<Usuario> usuarios) {
		return usuarios.stream()
				.map(usuario -> toModel(usuario))
				.collect(Collectors.toList());
	}

}
