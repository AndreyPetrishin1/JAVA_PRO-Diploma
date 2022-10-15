package com.site.rentyuzhne.repository;

import com.site.rentyuzhne.model.Adress;
import com.site.rentyuzhne.model.LineSea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LineSeaRepository extends JpaRepository<LineSea, Long> {
    Optional<LineSea> findByLinesea(String lineSea);
}
