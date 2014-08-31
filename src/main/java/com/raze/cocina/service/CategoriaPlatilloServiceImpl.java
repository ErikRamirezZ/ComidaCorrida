package com.raze.cocina.service;

import com.raze.cocina.reference.CategoriaPlatillo;
import com.raze.cocina.repository.CategoriaPlatilloRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoriaPlatilloServiceImpl implements CategoriaPlatilloService {

	@Autowired
    CategoriaPlatilloRepository categoriaPlatilloRepository;

	public long countAllCategoriaPlatilloes() {
        return categoriaPlatilloRepository.count();
    }

	public void deleteCategoriaPlatillo(CategoriaPlatillo categoriaPlatillo) {
        categoriaPlatilloRepository.delete(categoriaPlatillo);
    }

	public CategoriaPlatillo findCategoriaPlatillo(Long id) {
        return categoriaPlatilloRepository.findOne(id);
    }

	public List<CategoriaPlatillo> findAllCategoriaPlatilloes() {
        return categoriaPlatilloRepository.findAll();
    }

	public List<CategoriaPlatillo> findCategoriaPlatilloEntries(int firstResult, int maxResults) {
        return categoriaPlatilloRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveCategoriaPlatillo(CategoriaPlatillo categoriaPlatillo) {
        categoriaPlatilloRepository.save(categoriaPlatillo);
    }

	public CategoriaPlatillo updateCategoriaPlatillo(CategoriaPlatillo categoriaPlatillo) {
        return categoriaPlatilloRepository.save(categoriaPlatillo);
    }
}
