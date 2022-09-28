package com.vet.laudos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vet.laudos.domain.exception.EntidadeNaoEncontradaException;
import com.vet.laudos.domain.model.Permissao;
import com.vet.laudos.domain.repository.PermissaoRepository;

@Service
public class PermissaoService {
	@Autowired
	private PermissaoRepository repository;
	
	public Permissao buscar(Long id) {
		return repository.findById(id)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de permissão com código %d", id)));
	}
}
