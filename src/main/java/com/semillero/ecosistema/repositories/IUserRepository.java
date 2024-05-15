package com.semillero.ecosistema.repositories;

import com.semillero.ecosistema.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<UserModel,Long> {
    boolean existsByEmail(String email);
    UserModel getUserModelByName(String name);

    UserModel findByEmail(String email);

    @Query("SELECT u.email FROM UserModel u")
    List<String> findAllEmails();
}
