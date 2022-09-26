package com.vet.laudos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vet.laudos.domain.exception.EntidadeEmUsoException;
import com.vet.laudos.domain.exception.EntidadeNaoEncontradaException;
import com.vet.laudos.domain.model.Usuario;
import com.vet.laudos.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Usuario salvar(Usuario usuario) {
		return repository.save(usuario);
	}
	
	public void excluir(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de usuário com código %d", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Usuário de código %d não pode ser removido, pois está em uso", id));
		}
	}
	
	public Usuario buscar(Long id) {
		return repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de usuário com código %d", id)));
	}

}
