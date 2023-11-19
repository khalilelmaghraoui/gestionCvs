package myboot.app.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import myboot.app.dao.PersonneRepository;
import myboot.app.model.Personne;

@SpringBootTest
public class PersonneTest {


    @Autowired
    private LocalValidatorFactoryBean validator;
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private PersonneRepository personneRepository;

    private Personne personne;

    @BeforeEach
    public void setUp() {
    	personneRepository.deleteAll(); 
        personne = new Personne();
        personne.setNom("elie");
        personne.setPrenom("nicolas");
        personne.setAdresseElectronique("elie.nicolas@etu.uni-amu.fr");
        personne.setMot_de_passe("elie1234");
    }

    @Test
    public void testCreatePersonne() {
    	personneRepository.save(personne);
        assertNotNull(personne.getId());
    }

    @Test
    public void testReadPersonne() { 
    	personneRepository.save(personne);
        Optional<Personne> personneFromDB = personneRepository.findById(personne.getId());
        assertTrue(personneFromDB.isPresent());
        assertEquals("elie", personneFromDB.get().getNom());
        assertEquals("nicolas", personneFromDB.get().getPrenom());
    }

    @Test
    public void testUpdatePersonne() {
    	personneRepository.save(personne);
    	Optional<Personne> personneFromDB = personneRepository.findById(personne.getId());
    	assertEquals("elie", personneFromDB.get().getNom());
        assertEquals("nicolas", personneFromDB.get().getPrenom());

        personne.setNom("UpdatedName");
        personneRepository.save(personne);

        Optional<Personne> updatedPersonne = personneRepository.findById(personne.getId());
        assertTrue(updatedPersonne.isPresent());
        assertEquals("UpdatedName", updatedPersonne.get().getNom());
    }

    @Test
    public void testDeletePersonne() {
    	personneRepository.save(personne);
        personneRepository.delete(personne);
        assertFalse(personneRepository.existsById(personne.getId()));
    }
    
 
    @Test
    public void testNomVide() {
        personne.setNom(""); 
        Set<ConstraintViolation<Personne>> violations = validator.validate(personne);
        assertEquals(1, violations.size());
        ConstraintViolation<Personne> violation = violations.iterator().next();
        String expectedMessage = messageSource.getMessage("personne.nom.vide", null, Locale.getDefault());
        assertEquals(expectedMessage, violation.getMessage());
    }

    @Test
    public void testNomLong() {

        StringBuilder longNomBuilder = new StringBuilder();
        int desiredLength = 300; 
        for (int i = 0; i < desiredLength; i++) { longNomBuilder.append("A");  }
        String longNom = longNomBuilder.toString();

        personne.setNom(longNom);
        Set<ConstraintViolation<Personne>> violations = validator.validate(personne);
        assertEquals(1, violations.size());
        ConstraintViolation<Personne> violation = violations.iterator().next();
        String expectedMessage = messageSource.getMessage("personne.nom.long", null, Locale.getDefault());
        assertEquals(expectedMessage, violation.getMessage());
    }
    
    @Test
    public void testPrenomVide() {
        personne.setPrenom("");
        Set<ConstraintViolation<Personne>> violations = validator.validate(personne);
        assertEquals(1, violations.size());
        ConstraintViolation<Personne> violation = violations.iterator().next();
        String expectedMessage = messageSource.getMessage("personne.prenom.vide", null, Locale.getDefault());
        assertEquals(expectedMessage, violation.getMessage());
    }

    @Test
    public void testPrenonLong() {
        StringBuilder longNomBuilder = new StringBuilder();
        int desiredLength = 300; 
        for (int i = 0; i < desiredLength; i++) { longNomBuilder.append("A");  }
        String longNom = longNomBuilder.toString();

        personne.setPrenom(longNom);
        Set<ConstraintViolation<Personne>> violations = validator.validate(personne);
        assertEquals(1, violations.size());
        ConstraintViolation<Personne> violation = violations.iterator().next();
        String expectedMessage = messageSource.getMessage("personne.prenom.long", null, Locale.getDefault());
        assertEquals(expectedMessage, violation.getMessage());
    }
    
    @Test
    public void testAdresseElectroniqueValide() {
        Set<ConstraintViolation<Personne>> violations = validator.validate(personne);
        assertTrue(violations.isEmpty(), "Aucune violation de contrainte attendue.");
    }

    @Test
    public void testAdresseElectroniqueInvalide() {
        personne.setAdresseElectronique("adresse_invalide"); // Adresse invalide
        Set<ConstraintViolation<Personne>> violations = validator.validate(personne);
        assertEquals(1, violations.size(), "Une violation de contrainte attendue.");
        ConstraintViolation<Personne> violation = violations.iterator().next();
        String expectedMessage = messageSource.getMessage("personne.email", null, Locale.getDefault());
        assertEquals(expectedMessage, violation.getMessage());
    }
    
    @Test
    public void testDateDeNaissanceValide() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(); 
        personne.setDate_de_naissance(date);
        Set<ConstraintViolation<Personne>> violations = validator.validate(personne);
        assertTrue(violations.isEmpty(), "Aucune violation de contrainte attendue.");
    }

    @Test
    public void testDateDeNaissanceFuture() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date futureDate = new Date(System.currentTimeMillis() + 1000000); 
        personne.setDate_de_naissance(futureDate);
        Set<ConstraintViolation<Personne>> violations = validator.validate(personne);
        assertEquals(1, violations.size(), "Une violation de contrainte attendue.");
        ConstraintViolation<Personne> violation = violations.iterator().next();
        String expectedMessage = messageSource.getMessage("personne.date", null, Locale.getDefault());
        assertEquals(expectedMessage, violation.getMessage());
    }

    @Test
    public void testMotDePasseValide() {
        personne.setMot_de_passe("elie1234"); 
        Set<ConstraintViolation<Personne>> violations = validator.validate(personne);
        assertTrue(violations.isEmpty(), "Aucune violation de contrainte attendue.");
    }

    @Test
    public void testMotDePasseTropCourt() {
        personne.setMot_de_passe("pwd"); 
        Set<ConstraintViolation<Personne>> violations = validator.validate(personne);
        assertEquals(1, violations.size(), "Une violation de contrainte attendue.");
        ConstraintViolation<Personne> violation = violations.iterator().next();
        String expectedMessage = messageSource.getMessage("personne.motDePasse.taille", null, Locale.getDefault());
        assertEquals(expectedMessage, violation.getMessage());
    }

}
