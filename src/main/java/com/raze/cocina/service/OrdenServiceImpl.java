package com.raze.cocina.service;

import com.raze.cocina.domain.Orden;
import com.raze.cocina.repository.OrdenRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrdenServiceImpl implements OrdenService {

	@Autowired
    OrdenRepository ordenRepository;

	public long countAllOrdens() {
        return ordenRepository.count();
    }

	public void deleteOrden(Orden orden) {
        ordenRepository.delete(orden);
    }

	public Orden findOrden(Long id) {
        return ordenRepository.findOne(id);
    }

	public List<Orden> findAllOrdens() {
        return ordenRepository.findAll();
    }

	public List<Orden> findOrdenEntries(int firstResult, int maxResults) {
        return ordenRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveOrden(Orden orden) {
        ordenRepository.save(orden);
    }

	public Orden updateOrden(Orden orden) {
        return ordenRepository.save(orden);
    }
}
