package com.raze.cocina.service;
import com.raze.cocina.domain.Bebida_Orden;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.raze.cocina.domain.Bebida_Orden.class })
public interface Bebida_OrdenService {

	public abstract long countAllBebida_Ordens();


	public abstract void deleteBebida_Orden(Bebida_Orden bebida_Orden);


	public abstract Bebida_Orden findBebida_Orden(Long id);


	public abstract List<Bebida_Orden> findAllBebida_Ordens();


	public abstract List<Bebida_Orden> findBebida_OrdenEntries(int firstResult, int maxResults);


	public abstract void saveBebida_Orden(Bebida_Orden bebida_Orden);


	public abstract Bebida_Orden updateBebida_Orden(Bebida_Orden bebida_Orden);

}
