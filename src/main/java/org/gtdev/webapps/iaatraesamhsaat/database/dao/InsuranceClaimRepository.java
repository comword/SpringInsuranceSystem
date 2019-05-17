package org.gtdev.webapps.iaatraesamhsaat.database.dao;

import org.gtdev.webapps.iaatraesamhsaat.database.entities.AppUser;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.InsuranceClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaim, Long> {
    Optional<InsuranceClaim> findById(Long id);
    List<InsuranceClaim> findAllByUser(AppUser user);
    List<InsuranceClaim> findAll();
    @Query(value = "select * from insurance_claim c where c.time like ?1%", nativeQuery = true)
    List<InsuranceClaim> findByTimeMatch(@Param("time") String time);
    @Query(value = "select * from insurance_claim c where c.claim_step != ?1", nativeQuery = true)
    List<InsuranceClaim> findByClaimStepMatch(@Param("claimStep") String claimStep);

}
