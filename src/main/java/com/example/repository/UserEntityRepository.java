package com.example.repository;

import com.example.models.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    //Recuperar el usuario en base al email, comprueba si contraseña coincide
    Optional<UserEntity> findByEmail(String email);
}
