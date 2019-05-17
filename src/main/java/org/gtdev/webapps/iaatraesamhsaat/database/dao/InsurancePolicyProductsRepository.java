package org.gtdev.webapps.iaatraesamhsaat.database.dao;

import org.gtdev.webapps.iaatraesamhsaat.database.entities.InsurancePolicyProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface InsurancePolicyProductsRepository extends JpaRepository<InsurancePolicyProducts, String> {
    List<InsurancePolicyProducts> findAll();
}
