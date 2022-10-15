package com.site.rentyuzhne.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "adresses")
@Data
public class Adress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adress")
    private List<Flat> flats = new ArrayList<>();
}