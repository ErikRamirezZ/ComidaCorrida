package com.raze.cocina.service;

import com.raze.cocina.domain.Platillo;
import com.raze.cocina.repository.PlatilloRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlatilloServiceImpl implements PlatilloService {

	@Autowired
    PlatilloRepository platilloRepository;

	public long countAllPlatilloes() {
        return platilloRepository.count();
    }

	public void deletePlatillo(Platillo platillo) {
        platilloRepository.delete(platillo);
    }

	public Platillo findPlatillo(Long id) {
        return platilloRepository.findOne(id);
    }

	public List<Platillo> findAllPlatilloes() {
        return platilloRepository.findAll();
    }

	public List<Platillo> findPlatilloEntries(int firstResult, int maxResults) {
        return platilloRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void savePlatillo(Platillo platillo) {
        platilloRepository.save(platillo);
    }

	public Platillo updatePlatillo(Platillo platillo) {
        return platilloRepository.save(platillo);
    }
}
