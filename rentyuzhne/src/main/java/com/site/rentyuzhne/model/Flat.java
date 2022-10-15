package com.site.rentyuzhne.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "flats")
public class Flat {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotEmpty(message = "Заполните поле заголовок")
    private String title;

    @Column(name = "amountRoom")
    private String amountRoom;



    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flat")
    private List<Image> images = new ArrayList<>();

    private Long PreviewImageId;

    @ManyToOne (cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn
    private Adress adress;

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn
    private Floor floor;

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn
    private LineSea lineSea;
    private LocalDateTime dateOfCreate;

    @PrePersist
    private void init(){
        dateOfCreate = LocalDateTime.now();
    }
    public void addImageToFlat(Image image) {
        image.setFlat(this);
        images.add(image);
    }

}
