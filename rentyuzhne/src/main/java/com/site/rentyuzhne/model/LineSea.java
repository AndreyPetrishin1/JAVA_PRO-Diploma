package com.site.rentyuzhne.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lineseas")
@Data
public class LineSea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "linesea")
    private String linesea;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lineSea")
    private List<Flat> flats = new ArrayList<>();
}
