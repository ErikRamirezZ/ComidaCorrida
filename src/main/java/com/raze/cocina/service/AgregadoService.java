package com.raze.cocina.service;
import com.raze.cocina.domain.Agregado;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.raze.cocina.domain.Agregado.class })
public interface AgregadoService {

	public abstract long countAllAgregadoes();


	public abstract void deleteAgregado(Agregado agregado);


	public abstract Agregado findAgregado(Long id);


	public abstract List<Agregado> findAllAgregadoes();


	public abstract List<Agregado> findAgregadoEntries(int firstResult, int maxResults);


	public abstract void saveAgregado(Agregado agregado);


	public abstract Agregado updateAgregado(Agregado agregado);

}
