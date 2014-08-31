package com.raze.cocina.service;
import com.raze.cocina.reference.CategoriaPlatillo;
import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.raze.cocina.reference.CategoriaPlatillo.class })
public interface CategoriaPlatilloService {

	public abstract long countAllCategoriaPlatilloes();


	public abstract void deleteCategoriaPlatillo(CategoriaPlatillo categoriaPlatillo);


	public abstract CategoriaPlatillo findCategoriaPlatillo(Long id);


	public abstract List<CategoriaPlatillo> findAllCategoriaPlatilloes();


	public abstract List<CategoriaPlatillo> findCategoriaPlatilloEntries(int firstResult, int maxResults);


	public abstract void saveCategoriaPlatillo(CategoriaPlatillo categoriaPlatillo);


	public abstract CategoriaPlatillo updateCategoriaPlatillo(CategoriaPlatillo categoriaPlatillo);

}
