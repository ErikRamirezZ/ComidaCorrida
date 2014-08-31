package com.raze.cocina.service;
import com.raze.cocina.domain.Platillo;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.raze.cocina.domain.Platillo.class })
public interface PlatilloService {

	public abstract long countAllPlatilloes();


	public abstract void deletePlatillo(Platillo platillo);


	public abstract Platillo findPlatillo(Long id);


	public abstract List<Platillo> findAllPlatilloes();


	public abstract List<Platillo> findPlatilloEntries(int firstResult, int maxResults);


	public abstract void savePlatillo(Platillo platillo);


	public abstract Platillo updatePlatillo(Platillo platillo);

}
