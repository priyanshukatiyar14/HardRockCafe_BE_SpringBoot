package com.hardrockbe.hardrock.controller;

import com.hardrockbe.hardrock.model.menu;
import com.hardrockbe.hardrock.model.MenuDTO;
import com.hardrockbe.hardrock.repos.menurepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.MediaType;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class menucontroller {
    @Autowired
    menurepository menuRepo;

    @RequestMapping("/menu")
    @ResponseBody
    public ResponseEntity<List<menu>> getAllItems(){
        List<menu> menu =  menuRepo.findAll();
        return new ResponseEntity<List<menu>>(menu, HttpStatus.OK);
    }

    @GetMapping("/menu/{menuId}")
    @ResponseBody
    public ResponseEntity<MenuDTO> getItem(@PathVariable Long menuId) {
        Optional<menu> menu = menuRepo.findById(menuId);

        if (menu.isPresent()) {
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setId(menu.get().getId());
            menuDTO.setName(menu.get().getName());
            menuDTO.setPrice(menu.get().getPrice());
            menuDTO.setDescription(menu.get().getDescription());

            menuDTO.setImageUrl("http://localhost:8080/api/v1/menu/" + menuId + "/image");

            return new ResponseEntity<>(menuDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }







    @GetMapping("/menu/{menuId}/image")
    public ResponseEntity<byte[]> getMenuImage(@PathVariable Long menuId) {
        Optional<menu> menuOptional = menuRepo.findById(menuId);

        if (menuOptional.isPresent()) {
            menu menu = menuOptional.get();
            byte[] imageData = menu.getImage();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); 

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }


    @PostMapping("/add")
    public ResponseEntity<String> addItem(
            @RequestPart("menu") menu menu,
            @RequestPart("imageFile") MultipartFile imageFile,
            UriComponentsBuilder builder
    ) {
        try {
            if (!imageFile.isEmpty()) {
                menu.setImage(imageFile.getBytes());
            }

            menuRepo.save(menu);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/addItem/{id}").buildAndExpand(menu.getId()).toUri());

            return ResponseEntity.ok("Menu item added successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add menu item.");
        }
    }


    // @PostMapping(value = "/add",consumes = {"application/json"},produces = {"application/json"})
    // @ResponseBody
    // public ResponseEntity<menu> addItem(@RequestBody menu menu, UriComponentsBuilder builder){
    //     menuRepo.save(menu);
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setLocation(builder.path("/addItem/{id}").buildAndExpand(menu.getId()).toUri());
    //     return new ResponseEntity<menu>(headers, HttpStatus.CREATED);
    // }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<menu> updateItem(@RequestBody menu menu){
        if(menu != null){
            menuRepo.save(menu);
        }
        return new ResponseEntity<menu>(menu, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        Optional<menu> menu = menuRepo.findById(id);
        menuRepo.delete(menu.get());
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}