package org.gtdev.webapps.iaatraesamhsaat.database.dao;

import org.gtdev.webapps.iaatraesamhsaat.database.entities.AppUser;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.InsuranceClaim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaim, Long> {
    Optional<InsuranceClaim> findById(Long id);
    List<InsuranceClaim> findAllByUser(AppUser user);
    List<InsuranceClaim> findAll();
}
