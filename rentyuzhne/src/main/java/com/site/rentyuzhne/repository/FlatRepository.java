package com.site.rentyuzhne.repository;

import com.site.rentyuzhne.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlatRepository extends JpaRepository<Flat, Long> {

    List<Flat> findByTitle(String title);

}
