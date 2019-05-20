package org.gtdev.webapps.iaatraesamhsaat.database.dao;

import org.gtdev.webapps.iaatraesamhsaat.database.entities.AppUser;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.CustomerDetails;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.InsurancePolicyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InsurancePolicyRecordRepository extends JpaRepository<InsurancePolicyRecord, String> {
    Optional<InsurancePolicyRecord> findById(String id);
    InsurancePolicyRecord findInsurancePolicyRecordById(String id);
    List<InsurancePolicyRecord> findAllByCustomer(CustomerDetails customerDetails);
}
