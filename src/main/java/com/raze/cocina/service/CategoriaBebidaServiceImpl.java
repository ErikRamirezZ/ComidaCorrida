package com.raze.cocina.service;

import com.raze.cocina.reference.CategoriaBebida;
import com.raze.cocina.repository.CategoriaBebidaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoriaBebidaServiceImpl implements CategoriaBebidaService {

	@Autowired
    CategoriaBebidaRepository categoriaBebidaRepository;

	public long countAllCategoriaBebidas() {
        return categoriaBebidaRepository.count();
    }

	public void deleteCategoriaBebida(CategoriaBebida categoriaBebida) {
        categoriaBebidaRepository.delete(categoriaBebida);
    }

	public CategoriaBebida findCategoriaBebida(Long id) {
        return categoriaBebidaRepository.findOne(id);
    }

	public List<CategoriaBebida> findAllCategoriaBebidas() {
        return categoriaBebidaRepository.findAll();
    }

	public List<CategoriaBebida> findCategoriaBebidaEntries(int firstResult, int maxResults) {
        return categoriaBebidaRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveCategoriaBebida(CategoriaBebida categoriaBebida) {
        categoriaBebidaRepository.save(categoriaBebida);
    }

	public CategoriaBebida updateCategoriaBebida(CategoriaBebida categoriaBebida) {
        return categoriaBebidaRepository.save(categoriaBebida);
    }
}
