package com.vet.laudos.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vet.laudos.api.dto.output.GrupoOutput;
import com.vet.laudos.domain.model.Grupo;

@Component
public class GrupoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoOutput toModel (Grupo grupo) {
		return modelMapper.map(grupo, GrupoOutput.class);
	}
	
	public List<GrupoOutput> toCollectionModel (List<Grupo> grupos) {
		return grupos.stream()
				.map(grupo -> toModel(grupo))
				.collect(Collectors.toList());
	}
	
}
