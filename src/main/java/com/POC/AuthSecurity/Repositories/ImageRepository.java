package com.POC.AuthSecurity.Repositories;

import com.POC.AuthSecurity.Entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findByName(String name);
}
