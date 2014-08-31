package com.raze.cocina.repository;
import com.raze.cocina.reference.CategoriaBebida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooJpaRepository(domainType = CategoriaBebida.class)
public interface CategoriaBebidaRepository extends JpaRepository<CategoriaBebida, Long>, JpaSpecificationExecutor<CategoriaBebida> {
}
