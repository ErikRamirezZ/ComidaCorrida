package com.raze.cocina.service;
import com.raze.cocina.domain.Orden;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.raze.cocina.domain.Orden.class })
public interface OrdenService {

	public abstract long countAllOrdens();


	public abstract void deleteOrden(Orden orden);


	public abstract Orden findOrden(Long id);


	public abstract List<Orden> findAllOrdens();


	public abstract List<Orden> findOrdenEntries(int firstResult, int maxResults);


	public abstract void saveOrden(Orden orden);


	public abstract Orden updateOrden(Orden orden);

}
