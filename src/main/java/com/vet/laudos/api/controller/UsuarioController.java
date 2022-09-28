package com.vet.laudos.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vet.laudos.api.assembler.UsuarioAssembler;
import com.vet.laudos.api.disassembler.UsuarioDisassembler;
import com.vet.laudos.api.dto.ResponseDefault;
import com.vet.laudos.api.dto.UsuarioInput;
import com.vet.laudos.api.dto.UsuarioOutput;
import com.vet.laudos.domain.exception.EntidadeNaoEncontradaException;
import com.vet.laudos.domain.exception.NegocioException;
import com.vet.laudos.domain.model.Info;
import com.vet.laudos.domain.model.Usuario;
import com.vet.laudos.domain.repository.UsuarioRepository;
import com.vet.laudos.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private UsuarioService service;
	@Autowired
	private UsuarioAssembler assembler;
	@Autowired
	private UsuarioDisassembler disassembler;
	
	@GetMapping
	public ResponseDefault<UsuarioOutput> listar(){
		return new ResponseDefault<>(assembler.toCollectionModel(repository.findAll()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDefault<UsuarioOutput>> buscar(@PathVariable("id") Long id) {
		try {
			Usuario usuario = service.buscar(id);
			return new ResponseEntity<>(new ResponseDefault<>(assembler.toModel(usuario)), HttpStatus.OK);
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
	public ResponseEntity<ResponseDefault<UsuarioOutput>> adicionar(@RequestBody UsuarioInput usuarioInput) {
		try {
			Usuario usuario = disassembler.toDomainObject(usuarioInput);
			usuario = service.salvar(usuario);
			return new ResponseEntity<>(new ResponseDefault<>(assembler.toModel(usuario)), HttpStatus.OK);
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
	public ResponseEntity<ResponseDefault<UsuarioOutput>> atualizar(@PathVariable Long id,
			@RequestBody UsuarioInput usuarioInput){
		try {
			Usuario usuarioAtual = service.buscar(id);
			disassembler.copyToDomainObject(usuarioInput, usuarioAtual);
			usuarioAtual = service.salvar(usuarioAtual);
			
			return new ResponseEntity<>(new ResponseDefault<>(assembler.toModel(usuarioAtual)), HttpStatus.OK);
		}catch (Exception e) {
			List<Info> infos = new ArrayList<>();
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao("Não foi possível atualizar este usuário");
			infos.add(info);
			return new ResponseEntity<>(new ResponseDefault<>(infos), HttpStatus.OK);
		}
	}
}
