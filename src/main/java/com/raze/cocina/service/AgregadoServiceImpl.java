package com.raze.cocina.service;

import com.raze.cocina.domain.Agregado;
import com.raze.cocina.repository.AgregadoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AgregadoServiceImpl implements AgregadoService {

	@Autowired
    AgregadoRepository agregadoRepository;

	public long countAllAgregadoes() {
        return agregadoRepository.count();
    }

	public void deleteAgregado(Agregado agregado) {
        agregadoRepository.delete(agregado);
    }

	public Agregado findAgregado(Long id) {
        return agregadoRepository.findOne(id);
    }

	public List<Agregado> findAllAgregadoes() {
        return agregadoRepository.findAll();
    }

	public List<Agregado> findAgregadoEntries(int firstResult, int maxResults) {
        return agregadoRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveAgregado(Agregado agregado) {
        agregadoRepository.save(agregado);
    }

	public Agregado updateAgregado(Agregado agregado) {
        return agregadoRepository.save(agregado);
    }
}
