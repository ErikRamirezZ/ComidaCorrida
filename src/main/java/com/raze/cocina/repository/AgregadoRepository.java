package com.raze.cocina.repository;
import com.raze.cocina.domain.Agregado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooJpaRepository(domainType = Agregado.class)
public interface AgregadoRepository extends JpaRepository<Agregado, Long>, JpaSpecificationExecutor<Agregado> {
}
