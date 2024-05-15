package com.semillero.ecosistema.controllers;

import com.semillero.ecosistema.dtos.PublicationDto;
import com.semillero.ecosistema.exceptions.ResourceNotFoundException;
import com.semillero.ecosistema.models.PublicationModel;
import com.semillero.ecosistema.models.UserModel;
import com.semillero.ecosistema.request.PublicationRequest;
import com.semillero.ecosistema.services.PublicationService;
import com.semillero.ecosistema.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.semillero.ecosistema.enums.Role.ADMINISTRADOR;

@RestController
@RequestMapping("/publication")
public class PublicationController {
    private final PublicationService publicationService;
    private UserService userService;

    @Value("origin.localhost")
    private String localhost;
    public PublicationController(PublicationService publicacionService, UserService userService) {
        this.publicationService = publicacionService;
        this.userService = userService;
    }
    @GetMapping
    public Page<PublicationDto> getAll(@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") int pageNumber){
       System.out.println(localhost);
        return publicationService.getAll(size, pageNumber);
    }

    @GetMapping("/getById/{id_publication}")
    public ResponseEntity<PublicationDto> getById(@PathVariable Long id_publication){
        try {
            PublicationDto publication = publicationService.getById(id_publication);
            return ResponseEntity.ok(publication);
        }catch (Exception e){
            System.err.println("An error occurred while fetching publication" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/increaseView/{idUser}/{idPublication}")
    public ResponseEntity<?> increaseViews(@PathVariable Long idUser, @PathVariable Long idPublication ){
        try {
            publicationService.increaseViews(idUser, idPublication);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.err.println("An error occurred when increasing views" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<PublicationDto> createPublication(@Valid @RequestBody PublicationRequest publicationRequest, @PathVariable Long id){

            UserModel user = userService.getById(id);
            PublicationDto publication = publicationService.createPublication(publicationRequest, user);
            return ResponseEntity.ok(publication);

    }

    @PostMapping("/edit/{id_publication}/{id_user}")
    public PublicationModel editPublicacion(@PathVariable Long id_publication, @PathVariable Long id_user, @RequestBody PublicationRequest publicacionRequest){
        return publicationService.updatePublication(id_publication, publicacionRequest);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PublicationDto> deletePublicacion(@PathVariable Long id){
        try {
            publicationService.deletePublication(id);
            return ResponseEntity.status(200).build();

        }catch (Exception e){
            System.err.println("Deleting the publication failed" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
