package com.site.rentyuzhne.repository;

import com.site.rentyuzhne.model.Adress;
import com.site.rentyuzhne.model.Floor;
import com.site.rentyuzhne.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FloorRepository extends JpaRepository<Floor, Long> {
    Optional<Floor> findByStorey(String store);
}
