package org.gtdev.webapps.iaatraesamhsaat.database.dao;

import org.gtdev.webapps.iaatraesamhsaat.database.entities.AppUser;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.Feedback;

import org.gtdev.webapps.iaatraesamhsaat.database.entities.InsuranceClaim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findByClaim(InsuranceClaim claim);

}
