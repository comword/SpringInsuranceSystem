package org.gtdev.webapps.iaatraesamhsaat.database.dao;

import org.gtdev.webapps.iaatraesamhsaat.database.entities.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProvincesRepository extends JpaRepository<Provinces, String> {
    @Query(value = "SELECT code, englishName, chineseName FROM Provinces WHERE code LIKE 'IE_%'")
    List<Provinces> findIrelandCountries();

    @Query(value = "SELECT code, englishName, chineseName FROM Provinces WHERE code LIKE 'CN_%'")
    List<Provinces> findChineseCountries();
}
