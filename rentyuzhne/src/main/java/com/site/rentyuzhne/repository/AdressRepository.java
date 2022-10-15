package com.site.rentyuzhne.repository;

import com.site.rentyuzhne.model.Adress;
import com.site.rentyuzhne.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdressRepository extends JpaRepository<Adress, Long> {
    Optional<Adress> findByStreet(String street);
}
