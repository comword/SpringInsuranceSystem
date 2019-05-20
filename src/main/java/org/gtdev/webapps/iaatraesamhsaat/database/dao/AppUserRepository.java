package org.gtdev.webapps.iaatraesamhsaat.database.dao;

import org.gtdev.webapps.iaatraesamhsaat.database.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findAppUserById(long id);
    AppUser findAppUserByDisplayName(String displayName);
    AppUser findAppUserByUserName(String userName);
    AppUser findAppUserByEmail(String email);

    @Override
    List<AppUser> findAll();

    boolean existsAppUserByUserName(String userName);
    boolean existsAppUserByEmail(String email);
}
