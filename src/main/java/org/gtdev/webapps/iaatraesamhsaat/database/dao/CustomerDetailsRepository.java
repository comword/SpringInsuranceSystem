package org.gtdev.webapps.iaatraesamhsaat.database.dao;

import org.gtdev.webapps.iaatraesamhsaat.database.entities.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {
    boolean existsByIdNumber(String idNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByIdNumberOrPhoneNumber(String idNumber, String phoneNumber);
}
