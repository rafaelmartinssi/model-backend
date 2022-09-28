package com.vet.laudos.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vet.laudos.api.assembler.GrupoAssembler;
import com.vet.laudos.api.disassembler.GrupoDisassembler;
import com.vet.laudos.api.dto.GrupoInput;
import com.vet.laudos.api.dto.GrupoOutput;
import com.vet.laudos.api.dto.ResponseDefault;
import com.vet.laudos.domain.exception.EntidadeEmUsoException;
import com.vet.laudos.domain.exception.EntidadeNaoEncontradaException;
import com.vet.laudos.domain.exception.NegocioException;
import com.vet.laudos.domain.model.Grupo;
import com.vet.laudos.domain.model.Info;
import com.vet.laudos.domain.repository.GrupoRepository;
import com.vet.laudos.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository repository;
	
	@Autowired
	private GrupoService service;
	
	@Autowired
	private GrupoAssembler assembler;
	
	@Autowired
	private GrupoDisassembler disassembler;
	
	@GetMapping
	public ResponseDefault<GrupoOutput> listar(){
		return new ResponseDefault<>(assembler.toCollectionModel(repository.findAll()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDefault<GrupoOutput>> buscar(@PathVariable("id") Long id) {
		try {
			Grupo grupo = service.buscar(id);
			return new ResponseEntity<>(new ResponseDefault<>(assembler.toModel(grupo)), HttpStatus.OK);
		}catch (EntidadeNaoEncontradaException e) {
			List<Info> infos = new ArrayList<>();
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao(e.getMessage());
			infos.add(info);
			return new ResponseEntity<>(new ResponseDefault<>(infos), HttpStatus.OK);
		}
	}
	
	@PostMapping
	public ResponseEntity<ResponseDefault<GrupoOutput>> adicionar(@RequestBody GrupoInput grupoInput) {
		try {
			Grupo grupo = disassembler.toDomainObject(grupoInput);
			grupo = service.salvar(grupo);
			return new ResponseEntity<>(new ResponseDefault<>(assembler.toModel(grupo)), HttpStatus.OK);
		} catch (NegocioException e) {
			List<Info> infos = new ArrayList<>();
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao(e.getMessage());
			infos.add(info);
			return new ResponseEntity<>(new ResponseDefault<>(infos), HttpStatus.OK);
		} catch (Exception e) {
			List<Info> infos = new ArrayList<>();
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao("Não foi possível incluir este usuário");
			infos.add(info);
			return new ResponseEntity<>(new ResponseDefault<>(infos), HttpStatus.OK);
		}	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDefault<GrupoOutput>> atualizar(@PathVariable Long id,
			@RequestBody GrupoInput grupoInput){
		try {
			Grupo grupoAtual = service.buscar(id);
			disassembler.copyToDomainObject(grupoInput, grupoAtual);
			grupoAtual = service.salvar(grupoAtual);
			
			return new ResponseEntity<>(new ResponseDefault<>(assembler.toModel(grupoAtual)), HttpStatus.OK);
		}catch (Exception e) {
			List<Info> infos = new ArrayList<>();
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao("Não foi possível atualizar este usuário");
			infos.add(info);
			return new ResponseEntity<>(new ResponseDefault<>(infos), HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		try {
			service.excluir(id);
			
			List<Info> infos = new ArrayList<>();
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao("Operação realizada com sucesso");
			infos.add(info);
			return new ResponseEntity<>(new ResponseDefault<>(infos), HttpStatus.OK);
		} catch (EntidadeNaoEncontradaException e) {
			List<Info> infos = new ArrayList<>();
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao(e.getMessage());
			infos.add(info);
			return new ResponseEntity<>(new ResponseDefault<>(infos), HttpStatus.OK);
		}catch (EntidadeEmUsoException e) {
			List<Info> infos = new ArrayList<>();
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao(e.getMessage());
			infos.add(info);
			return new ResponseEntity<>(new ResponseDefault<>(infos), HttpStatus.OK);
		}	
	}

}
