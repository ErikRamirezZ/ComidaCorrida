package com.raze.cocina.repository;
import com.raze.cocina.domain.Platillo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooJpaRepository(domainType = Platillo.class)
public interface PlatilloRepository extends JpaSpecificationExecutor<Platillo>, JpaRepository<Platillo, Long> {
}
