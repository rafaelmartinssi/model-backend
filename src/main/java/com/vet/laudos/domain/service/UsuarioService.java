package com.vet.laudos.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vet.laudos.domain.exception.EntidadeNaoEncontradaException;
import com.vet.laudos.domain.exception.NegocioException;
import com.vet.laudos.domain.model.Grupo;
import com.vet.laudos.domain.model.Usuario;
import com.vet.laudos.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	private static final String MSG_USUARIO_JA_CADASTRADO = "Já existe um usuário cadastrado com o e-mail %s";
	private static final String MSG_USUARIO_NAO_ENCONTRADO = "Não existe um cadastro de usuário com código %d";
	
	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private GrupoService grupoService;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format(MSG_USUARIO_JA_CADASTRADO, usuario.getEmail()));
		}
		
		return repository.save(usuario);
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscar(usuarioId);
		Grupo grupo = grupoService.buscar(grupoId);
		
		usuario.removerGrupo(grupo);
	}
	
	@Transactional
	public void associarGrupo(Long id, Long grupoId) {
		Usuario usuario = buscar(id);
		Grupo grupo = grupoService.buscar(grupoId);
		
		usuario.adicionarGrupo(grupo);
	}
	
	public Usuario buscar(Long id) {
		return repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(MSG_USUARIO_NAO_ENCONTRADO, id)));
	}

}
