package com.raze.cocina.repository;
import com.raze.cocina.domain.Bebida_Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooJpaRepository(domainType = Bebida_Orden.class)
public interface Bebida_OrdenRepository extends JpaSpecificationExecutor<Bebida_Orden>, JpaRepository<Bebida_Orden, Long> {
}
