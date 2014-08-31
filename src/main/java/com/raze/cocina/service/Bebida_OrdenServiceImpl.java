package com.raze.cocina.service;

import com.raze.cocina.domain.Bebida_Orden;
import com.raze.cocina.repository.Bebida_OrdenRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Bebida_OrdenServiceImpl implements Bebida_OrdenService {

	@Autowired
    Bebida_OrdenRepository bebida_OrdenRepository;

	public long countAllBebida_Ordens() {
        return bebida_OrdenRepository.count();
    }

	public void deleteBebida_Orden(Bebida_Orden bebida_Orden) {
        bebida_OrdenRepository.delete(bebida_Orden);
    }

	public Bebida_Orden findBebida_Orden(Long id) {
        return bebida_OrdenRepository.findOne(id);
    }

	public List<Bebida_Orden> findAllBebida_Ordens() {
        return bebida_OrdenRepository.findAll();
    }

	public List<Bebida_Orden> findBebida_OrdenEntries(int firstResult, int maxResults) {
        return bebida_OrdenRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveBebida_Orden(Bebida_Orden bebida_Orden) {
        bebida_OrdenRepository.save(bebida_Orden);
    }

	public Bebida_Orden updateBebida_Orden(Bebida_Orden bebida_Orden) {
        return bebida_OrdenRepository.save(bebida_Orden);
    }
}
