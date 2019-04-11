package org.gtdev.webapps.iaatraesamhsaat.database.dao;

import org.gtdev.webapps.iaatraesamhsaat.database.entities.InsurancePolicyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InsurancePolicyRecordRepository extends JpaRepository<InsurancePolicyRecord, String> {
    Optional<InsurancePolicyRecord> findById(String id);
}
