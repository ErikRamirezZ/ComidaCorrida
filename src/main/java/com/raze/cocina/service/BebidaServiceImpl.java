package com.raze.cocina.service;

import com.raze.cocina.domain.Bebida;
import com.raze.cocina.repository.BebidaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BebidaServiceImpl implements BebidaService {

	@Autowired
    BebidaRepository bebidaRepository;

	public long countAllBebidas() {
        return bebidaRepository.count();
    }

	public void deleteBebida(Bebida bebida) {
        bebidaRepository.delete(bebida);
    }

	public Bebida findBebida(Long id) {
        return bebidaRepository.findOne(id);
    }

	public List<Bebida> findAllBebidas() {
        return bebidaRepository.findAll();
    }

	public List<Bebida> findBebidaEntries(int firstResult, int maxResults) {
        return bebidaRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveBebida(Bebida bebida) {
        bebidaRepository.save(bebida);
    }

	public Bebida updateBebida(Bebida bebida) {
        return bebidaRepository.save(bebida);
    }
}
