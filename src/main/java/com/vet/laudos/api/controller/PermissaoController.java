package com.vet.laudos.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vet.laudos.api.assembler.PermissaoAssembler;
import com.vet.laudos.api.dto.ResponseDefault;
import com.vet.laudos.api.dto.output.PermissaoOutput;
import com.vet.laudos.domain.repository.PermissaoRepository;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {
	
	@Autowired
	private PermissaoRepository repository;
	
	@Autowired
	private PermissaoAssembler assembler;
	
	@GetMapping
	public ResponseDefault<PermissaoOutput> listar(){
		return new ResponseDefault<>(assembler.toCollectionModel(repository.findAll()));
	}
}
