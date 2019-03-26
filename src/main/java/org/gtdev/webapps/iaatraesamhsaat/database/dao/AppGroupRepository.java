package org.gtdev.webapps.iaatraesamhsaat.database.dao;

import org.gtdev.webapps.iaatraesamhsaat.database.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppGroupRepository extends JpaRepository<AppUser, Long> {

}
