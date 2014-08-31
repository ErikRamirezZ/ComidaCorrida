package com.raze.cocina.service;
import com.raze.cocina.reference.CategoriaBebida;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.raze.cocina.reference.CategoriaBebida.class })
public interface CategoriaBebidaService {

	public abstract long countAllCategoriaBebidas();


	public abstract void deleteCategoriaBebida(CategoriaBebida categoriaBebida);


	public abstract CategoriaBebida findCategoriaBebida(Long id);


	public abstract List<CategoriaBebida> findAllCategoriaBebidas();


	public abstract List<CategoriaBebida> findCategoriaBebidaEntries(int firstResult, int maxResults);


	public abstract void saveCategoriaBebida(CategoriaBebida categoriaBebida);


	public abstract CategoriaBebida updateCategoriaBebida(CategoriaBebida categoriaBebida);

}
