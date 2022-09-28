package com.vet.laudos.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vet.laudos.domain.exception.EntidadeEmUsoException;
import com.vet.laudos.domain.exception.EntidadeNaoEncontradaException;
import com.vet.laudos.domain.model.Grupo;
import com.vet.laudos.domain.model.Permissao;
import com.vet.laudos.domain.repository.GrupoRepository;

@Service
public class GrupoService {

	private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso";
	private static final String MSG_GRUPO_NAO_ENCONTRADO = "Não existe um cadastro de grupo com código %d";

	@Autowired
	private GrupoRepository repository;

	@Autowired
	private PermissaoService permissaoService;

	@Transactional
	public Grupo salvar(Grupo grupo) {
		return repository.save(grupo);
	}

	@Transactional
	public void excluir(Long grupoId) {
		try {
			repository.deleteById(grupoId);
			repository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_GRUPO_NAO_ENCONTRADO, grupoId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}

	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscar(grupoId);
		Permissao permissao = permissaoService.buscar(permissaoId);

		grupo.removerPermissao(permissao);
	}

	@Transactional
	public void associarPermissao(Long id, Long permissaoId) {
		Grupo grupo = buscar(id);
		Permissao permissao = permissaoService.buscar(permissaoId);

		grupo.adicionarPermissao(permissao);
	}

	public Grupo buscar(Long id) {
		return repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(MSG_GRUPO_NAO_ENCONTRADO, id)));
	}

}
