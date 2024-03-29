package com.vet.laudos.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
import com.vet.laudos.api.dto.input.SenhaInput;
import com.vet.laudos.api.dto.input.UsuarioInput;
import com.vet.laudos.api.dto.output.UsuarioOutput;
import com.vet.laudos.core.security.SecurityUtils;
import com.vet.laudos.domain.exception.EntidadeNaoEncontradaException;
import com.vet.laudos.domain.exception.NegocioException;
import com.vet.laudos.domain.model.Info;
import com.vet.laudos.domain.model.Usuario;
import com.vet.laudos.domain.repository.UsuarioRepository;
import com.vet.laudos.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private UsuarioService service;
	@Autowired
	private UsuarioAssembler assembler;
	@Autowired
	private UsuarioDisassembler disassembler;
	@Autowired
	private SecurityUtils utils;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseDefault<UsuarioOutput> listar(){
		return new ResponseDefault<>(assembler.toCollectionModel(repository.findAll()));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDefault<UsuarioOutput>> buscar(@PathVariable("id") Long id) {
		try {
			Usuario usuario = service.buscar(id);
			return new ResponseEntity<>(new ResponseDefault<>(assembler.toModel(usuario)), HttpStatus.OK);
		}catch (EntidadeNaoEncontradaException e) {
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao(e.getMessage());
			return new ResponseEntity<>(new ResponseDefault<>(info), HttpStatus.OK);
		}
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/autenticado")
	public ResponseEntity<ResponseDefault<UsuarioOutput>> getUsuarioAutenticado() {
		try {
			Usuario usuario = service.buscar(utils.getUserId());
			return new ResponseEntity<>(new ResponseDefault<>(assembler.toModel(usuario)), HttpStatus.OK);
		}catch (EntidadeNaoEncontradaException e) {
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao(e.getMessage());
			return new ResponseEntity<>(new ResponseDefault<>(info), HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAuthority('CREATE_USER')")
	@PostMapping
	public ResponseEntity<ResponseDefault<UsuarioOutput>> adicionar(@RequestBody UsuarioInput usuarioInput) {
		try {
			Usuario usuario = disassembler.toDomainObject(usuarioInput);
			usuario = service.salvar(usuario);
			return new ResponseEntity<>(new ResponseDefault<>(assembler.toModel(usuario)), HttpStatus.OK);
		} catch (NegocioException e) {
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao(e.getMessage());
			return new ResponseEntity<>(new ResponseDefault<>(info), HttpStatus.OK);
		} catch (Exception e) {
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao("Não foi possível incluir este usuário");
			return new ResponseEntity<>(new ResponseDefault<>(info), HttpStatus.OK);
		}	
	}
	
	@PreAuthorize("hasAuthority('EDIT_USER')")
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDefault<UsuarioOutput>> atualizar(@PathVariable Long id,
			@RequestBody UsuarioInput usuarioInput){
		try {
			Usuario usuarioAtual = service.buscar(id);
			disassembler.copyToDomainObject(usuarioInput, usuarioAtual);
			usuarioAtual = service.salvar(usuarioAtual);
			
			return new ResponseEntity<>(new ResponseDefault<>(assembler.toModel(usuarioAtual)), HttpStatus.OK);
		}catch (Exception e) {
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao("Não foi possível atualizar este usuário");
			return new ResponseEntity<>(new ResponseDefault<>(info), HttpStatus.OK);
		}
	}
	
	@PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/senha")
    public ResponseEntity<?> alterarSenha(@PathVariable Long id, @RequestBody SenhaInput senha) {
		try {
			service.alterarSenha(id, senha.getSenhaAtual(), senha.getNovaSenha());
			
			Info info = new Info();
			info.setCodigo(0);
			info.setDescricao("Operação realizada com sucesso");
			return new ResponseEntity<>(new ResponseDefault<>(info), HttpStatus.OK);
		} catch (NegocioException e) {
			Info info = new Info();
			info.setCodigo(1);
			info.setDescricao(e.getMessage());
			return new ResponseEntity<>(new ResponseDefault<>(info), HttpStatus.OK);
		}
    } 
}
