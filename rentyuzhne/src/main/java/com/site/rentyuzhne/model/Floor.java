package com.site.rentyuzhne.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "floors")
@Data
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "floor")
    private String storey;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "floor")
    private List<Flat> flats = new ArrayList<>();
}
