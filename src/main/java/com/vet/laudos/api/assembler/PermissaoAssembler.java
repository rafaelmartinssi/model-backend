package com.vet.laudos.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vet.laudos.api.dto.output.PermissaoOutput;
import com.vet.laudos.domain.model.Permissao;

@Component
public class PermissaoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PermissaoOutput toModel (Permissao permissao) {
		return modelMapper.map(permissao, PermissaoOutput.class);
	}
	
	public List<PermissaoOutput> toCollectionModel (List<Permissao> permissoes) {
		return permissoes.stream()
				.map(permissao -> toModel(permissao))
				.collect(Collectors.toList());
	}
	
}
