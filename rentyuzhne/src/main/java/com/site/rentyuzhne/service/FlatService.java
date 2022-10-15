package com.site.rentyuzhne.service;

import com.site.rentyuzhne.model.*;
import com.site.rentyuzhne.repository.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@Data
public class FlatService {

    private FlatRepository flatRepository;
    private UserRepository userRepository;
    private AdressRepository adressRepository;

    private FloorRepository floorRepository;

    private LineSeaRepository lineSeaRepository;

    public FlatService(FlatRepository flatRepository, UserRepository userRepository, AdressRepository adressRepository, FloorRepository floorRepository, LineSeaRepository lineSeaRepository) {
        this.flatRepository = flatRepository;
        this.userRepository = userRepository;
        this.adressRepository = adressRepository;
        this.floorRepository = floorRepository;
        this.lineSeaRepository = lineSeaRepository;
    }
    @Transactional(readOnly = true)
    public List<Flat> findAll(String title) {

        if(title==null || title.equals(""))
            return flatRepository.findAll();
        return flatRepository.findByTitle(title);
    }
    @Transactional(readOnly = true)
    public Flat findById(Long id){

        return flatRepository.findById(id).orElse(null);
    }
    @Transactional
    public Flat saveFlat(Principal principal, Flat flat, MultipartFile file1, MultipartFile file2, MultipartFile file3, String street, String floor_, String lineSea_ ) throws IOException {
        flat.setUser(getUserByPrincipal(principal));
        Image img1;
        Image img2;
        Image img3;

        if(!file1.isEmpty()){
            img1= toImageEntity(file1);
            img1.setPreviewImage(true);
            flat.addImageToFlat(img1);
        }
        if(!file2.isEmpty()){
            img2= toImageEntity(file2);
            flat.addImageToFlat(img2);
        }
        if(!file3.isEmpty()){
            img3= toImageEntity(file3);
            flat.addImageToFlat(img3);
        }
        Adress adress = adressRepository.findByStreet(street).orElse(null);
        if(adress==null){
            adress = new Adress();
            adress.setStreet(street);
        }

        Floor floor = floorRepository.findByStorey(floor_).orElse(null);
        if(floor==null){
            floor = new Floor();
            floor.setStorey(floor_);
        }

        LineSea lineSea = lineSeaRepository.findByLinesea(lineSea_).orElse(null);
        if(lineSea==null){
            lineSea = new LineSea();
            lineSea.setLinesea(lineSea_);
        }

        flat.setAdress(adress);
        flat.setFloor(floor);
        flat.setLineSea(lineSea);
        Flat flatFromDb = flatRepository.save(flat);

        if(!file1.isEmpty()) {
            flatFromDb.setPreviewImageId(flatFromDb.getImages().get(0).getId());
        }
        return flatRepository.save(flat);
    }
    @Transactional
    public User getUserByPrincipal(Principal principal) {
        if(principal ==null)
            return new User();
        return userRepository.findByEmail(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
    @Transactional(readOnly = true)
    public void deleteFlat(Long id){

        flatRepository.deleteById(id);

    }
}