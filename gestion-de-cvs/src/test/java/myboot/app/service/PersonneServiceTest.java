package myboot.app.service;

import myboot.app.dao.PersonneRepository;
import myboot.app.model.Personne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PersonneServiceTest {

    @Mock
    private PersonneRepository personneRepository;

    @InjectMocks
    private PersonneService personneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPersonnes() {
        Personne personne1 = new Personne(1L, "John", "Doe", "john@example.com", "http://example.com", null, "password", null, null);
        Personne personne2 = new Personne(2L, "Jane", "Doe", "jane@example.com", "http://example.com", null, "password", null, null);
        List<Personne> personnes = Arrays.asList(personne1, personne2);
        when(personneRepository.findAll()).thenReturn(personnes);
        List<Personne> result = personneService.getAllPersonnes();
        assertEquals(personnes, result);
    }

    @Test
    void getPersonneById() {
        long id = 1L;
        Personne personne = new Personne(id, "John", "Doe", "john@example.com", "http://example.com", null, "password", null, null);
        when(personneRepository.findById(id)).thenReturn(Optional.of(personne));
        Optional<Personne> result = personneService.getPersonneById(id);
        assertTrue(result.isPresent());
        assertEquals(personne, result.get());
    }

    @Test
    void createPersonne() {
        Personne personneToCreate = new Personne(null, "John", "Doe", "john@example.com", "http://example.com", null, "password", null, null);
        Personne createdPersonne = new Personne(1L, "John", "Doe", "john@example.com", "http://example.com", null, "password", null, null);
        when(personneRepository.save(personneToCreate)).thenReturn(createdPersonne);
        Personne result = personneService.createPersonne(personneToCreate);
        assertEquals(createdPersonne, result);
    }

    @Test
    void updatePersonne() {
        long id = 1L;
        Personne existingPersonne = new Personne(id, "John", "Doe", "john@example.com", "http://example.com", null, "password", null, null);
        Personne updatedPersonne = new Personne(id, "UpdatedJohn", "Doe", "john@example.com", "http://example.com", null, "password", null, null);
        when(personneRepository.findById(id)).thenReturn(Optional.of(existingPersonne));
        when(personneRepository.save(updatedPersonne)).thenReturn(updatedPersonne);
        Personne result = personneService.updatePersonne(id, updatedPersonne);
        assertEquals(updatedPersonne, result);
    }

    @Test
    void deletePersonne() {
        long id = 1L;
        personneService.deletePersonne(id);
        verify(personneRepository, times(1)).deleteById(id);
    }


}
