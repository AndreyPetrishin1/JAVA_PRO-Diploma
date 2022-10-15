package com.site.rentyuzhne.controller;

import com.site.rentyuzhne.model.Flat;
import com.site.rentyuzhne.model.User;
import com.site.rentyuzhne.service.FlatService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Controller
@Data
public class FlatController {

    public FlatController(FlatService flatService) {
        this.flatService = flatService;
    }

    public FlatService flatService;

    @GetMapping("/")
    public String findAll(@RequestParam(required = false) String title,Principal principal ,Model model){
        List<Flat> flats  = flatService.findAll(title);
        model.addAttribute("flats", flats);
        model.addAttribute("user", flatService.getUserByPrincipal(principal));
        return"flat-list";
    }
    @GetMapping("/flat-info/{id}")
    public String flatInfo(@PathVariable Long id, Model model){
        Flat flat =  flatService.findById(id);
        model.addAttribute("flat" ,flat);
        model.addAttribute("images" ,flat.getImages());
        return "/flat-info";
    }

    @GetMapping("/flat-create")
    public String createFlatForm(@ModelAttribute Flat flat){
        return "flat-create";
    }

    @PostMapping("/flat-create")
    public String createFlat(@RequestParam MultipartFile file1,@RequestParam MultipartFile file2, @RequestParam MultipartFile file3, @ModelAttribute  @Valid Flat flat, BindingResult bildingResult,
                             Principal principal, @RequestParam String street, @RequestParam String storey,@RequestParam String linesea) throws IOException {

        if(bildingResult.hasErrors()){

            return "flat-create";
        }
        flatService.saveFlat(principal ,flat, file1, file2, file3, street,storey,linesea);
        return "redirect:/";
    }

    @PostMapping("/flat-delete/{id}")
    public String deleteFlat(@PathVariable Long id){
        flatService.deleteFlat(id);
        return "redirect:/";
    }

    @GetMapping("/my/flats")
    public String userFlat(Principal principal, Model model) {
        User user = flatService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("flats", user.getFlats());
        return "my-flats";
    }

}
