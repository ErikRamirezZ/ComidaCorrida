package com.raze.cocina.service;

import com.raze.cocina.reference.Estatus;
import com.raze.cocina.repository.EstatusRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EstatusServiceImpl implements EstatusService {

	@Autowired
    EstatusRepository estatusRepository;

	public long countAllEstatuses() {
        return estatusRepository.count();
    }

	public void deleteEstatus(Estatus estatus) {
        estatusRepository.delete(estatus);
    }

	public Estatus findEstatus(Long id) {
        return estatusRepository.findOne(id);
    }

	public List<Estatus> findAllEstatuses() {
        return estatusRepository.findAll();
    }

	public List<Estatus> findEstatusEntries(int firstResult, int maxResults) {
        return estatusRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveEstatus(Estatus estatus) {
        estatusRepository.save(estatus);
    }

	public Estatus updateEstatus(Estatus estatus) {
        return estatusRepository.save(estatus);
    }
}
