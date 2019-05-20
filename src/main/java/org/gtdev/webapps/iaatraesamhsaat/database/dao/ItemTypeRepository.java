package org.gtdev.webapps.iaatraesamhsaat.database.dao;


import org.gtdev.webapps.iaatraesamhsaat.database.entities.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
    Optional<ItemType> findById(Long id);
}
