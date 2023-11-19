package myboot.app.web;

import myboot.app.dto.PersonneDTO;
import javax.validation.constraints.*;
import myboot.app.model.Personne;
import myboot.app.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personnes")
public class PersonneController {

    @Autowired
    private PersonneService personneService;

    @GetMapping
    public ResponseEntity<List<PersonneDTO>> getAllPersonnes() {
        List<Personne> personnes = personneService.getAllPersonnes();
        List<PersonneDTO> personneDTOs = personnes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(personneDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneDTO> getPersonneById(@PathVariable Long id) {
        Optional<Personne> personne = personneService.getPersonneById(id);
        return personne.map(value -> new ResponseEntity<>(convertToDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PersonneDTO> createPersonne(@RequestBody PersonneDTO personneDTO) {
        Personne createdPersonne = personneService.createPersonne(convertToEntity(personneDTO));
        return new ResponseEntity<>(convertToDTO(createdPersonne), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonneDTO> updatePersonne(@PathVariable Long id, @RequestBody PersonneDTO personneDTO) {
        Personne updatedPersonne = personneService.updatePersonne(id, convertToEntity(personneDTO));
        return new ResponseEntity<>(convertToDTO(updatedPersonne), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PersonneDTO>> searchPersonnes(@RequestParam String keyword) {
        List<Personne> personnes = personneService.searchPersonnes(keyword);
        List<PersonneDTO> personneDTOs = personnes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(personneDTOs, HttpStatus.OK);
    }

    private PersonneDTO convertToDTO(Personne personne) {
        // Convert logic from Personne to PersonneDTO
        // For simplicity, you can manually set properties
        PersonneDTO personneDTO = new PersonneDTO();
        personneDTO.setId(personne.getId());
        personneDTO.setNom(personne.getNom());
        personneDTO.setNom(personne.getPrenom());
        // Set other properties

        return personneDTO;
    }

    private Personne convertToEntity(PersonneDTO personneDTO) {
        // Convert logic from PersonneDTO to Personne
        // For simplicity, you can manually set properties
        Personne personne = new Personne();
        personne.setId(personneDTO.getId());
        personne.setNom(personneDTO.getNom());
        personne.setPrenom(personneDTO.getPrénom());
        // Set other properties

        return personne;
    }
}
