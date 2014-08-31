package com.raze.cocina.service;
import com.raze.cocina.domain.Bebida;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.raze.cocina.domain.Bebida.class })
public interface BebidaService {

	public abstract long countAllBebidas();


	public abstract void deleteBebida(Bebida bebida);


	public abstract Bebida findBebida(Long id);


	public abstract List<Bebida> findAllBebidas();


	public abstract List<Bebida> findBebidaEntries(int firstResult, int maxResults);


	public abstract void saveBebida(Bebida bebida);


	public abstract Bebida updateBebida(Bebida bebida);

}
