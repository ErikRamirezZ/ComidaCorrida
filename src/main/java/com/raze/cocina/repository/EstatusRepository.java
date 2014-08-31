package com.raze.cocina.repository;
import com.raze.cocina.reference.Estatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooJpaRepository(domainType = Estatus.class)
public interface EstatusRepository extends JpaSpecificationExecutor<Estatus>, JpaRepository<Estatus, Long> {
}
