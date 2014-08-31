package com.raze.cocina.repository;
import com.raze.cocina.reference.CategoriaPlatillo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooJpaRepository(domainType = CategoriaPlatillo.class)
public interface CategoriaPlatilloRepository extends JpaSpecificationExecutor<CategoriaPlatillo>, JpaRepository<CategoriaPlatillo, Long> {
}
