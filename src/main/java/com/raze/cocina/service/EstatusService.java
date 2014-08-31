package com.raze.cocina.service;
import com.raze.cocina.reference.Estatus;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.raze.cocina.reference.Estatus.class })
public interface EstatusService {

	public abstract long countAllEstatuses();


	public abstract void deleteEstatus(Estatus estatus);


	public abstract Estatus findEstatus(Long id);


	public abstract List<Estatus> findAllEstatuses();


	public abstract List<Estatus> findEstatusEntries(int firstResult, int maxResults);


	public abstract void saveEstatus(Estatus estatus);


	public abstract Estatus updateEstatus(Estatus estatus);

}
