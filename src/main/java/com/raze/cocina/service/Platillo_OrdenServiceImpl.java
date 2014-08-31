package com.raze.cocina.service;

import com.raze.cocina.domain.Platillo_Orden;
import com.raze.cocina.repository.Platillo_OrdenRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Platillo_OrdenServiceImpl implements Platillo_OrdenService {

	@Autowired
    Platillo_OrdenRepository platillo_OrdenRepository;

	public long countAllPlatillo_Ordens() {
        return platillo_OrdenRepository.count();
    }

	public void deletePlatillo_Orden(Platillo_Orden platillo_Orden) {
        platillo_OrdenRepository.delete(platillo_Orden);
    }

	public Platillo_Orden findPlatillo_Orden(Long id) {
        return platillo_OrdenRepository.findOne(id);
    }

	public List<Platillo_Orden> findAllPlatillo_Ordens() {
        return platillo_OrdenRepository.findAll();
    }

	public List<Platillo_Orden> findPlatillo_OrdenEntries(int firstResult, int maxResults) {
        return platillo_OrdenRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void savePlatillo_Orden(Platillo_Orden platillo_Orden) {
        platillo_OrdenRepository.save(platillo_Orden);
    }

	public Platillo_Orden updatePlatillo_Orden(Platillo_Orden platillo_Orden) {
        return platillo_OrdenRepository.save(platillo_Orden);
    }
}
