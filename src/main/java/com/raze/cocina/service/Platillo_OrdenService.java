package com.raze.cocina.service;
import com.raze.cocina.domain.Platillo_Orden;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.raze.cocina.domain.Platillo_Orden.class })
public interface Platillo_OrdenService {

	public abstract long countAllPlatillo_Ordens();


	public abstract void deletePlatillo_Orden(Platillo_Orden platillo_Orden);


	public abstract Platillo_Orden findPlatillo_Orden(Long id);


	public abstract List<Platillo_Orden> findAllPlatillo_Ordens();


	public abstract List<Platillo_Orden> findPlatillo_OrdenEntries(int firstResult, int maxResults);


	public abstract void savePlatillo_Orden(Platillo_Orden platillo_Orden);


	public abstract Platillo_Orden updatePlatillo_Orden(Platillo_Orden platillo_Orden);

}
