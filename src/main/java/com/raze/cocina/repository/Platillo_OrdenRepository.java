package com.raze.cocina.repository;
import com.raze.cocina.domain.Platillo_Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooJpaRepository(domainType = Platillo_Orden.class)
public interface Platillo_OrdenRepository extends JpaSpecificationExecutor<Platillo_Orden>, JpaRepository<Platillo_Orden, Long> {
}
