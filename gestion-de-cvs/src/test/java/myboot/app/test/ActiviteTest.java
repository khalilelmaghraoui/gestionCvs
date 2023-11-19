package myboot.app.test;

import myboot.app.dao.ActiviteRepository;
import myboot.app.dao.CurriculumVitaeRepository;
import myboot.app.dao.PersonneRepository;
import myboot.app.model.Activite;
import myboot.app.model.CurriculumVitae;
import myboot.app.model.NatureActivite;
import myboot.app.model.Personne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ActiviteTest {

    @Autowired
    private ActiviteRepository activiteRepository;

    @Autowired
    private CurriculumVitaeRepository cvRepository;

    @Autowired
    private PersonneRepository personneRepository;

    private CurriculumVitae curriculumVitae;
    private Personne personne;
    Activite activite;

    @BeforeEach
    public void setup() {
        personne = new Personne();
        personne.setNom("elie");
        personne.setPrenom("nicolas");
        personne.setAdresseElectronique("elie.nicolas@etu.uni-amu.fr");
        personne.setMot_de_passe("elie1234");
        personneRepository.save(personne);
        
        curriculumVitae = new CurriculumVitae(personne);
        cvRepository.save(curriculumVitae);

        
        activite = new Activite(curriculumVitae, personne);
        activite.setAnnee(2022);
        activite.setNature(NatureActivite.EXPERIENCE_PROFESSIONNELLE);
        activite.setTitre("Docteur");
        activiteRepository.save(activite);
    }

    @Test
    public void testFindById() {
        Optional<Activite> optionalActivite = activiteRepository.findById(activite.getId());
        assertTrue(optionalActivite.isPresent());
        Activite activite = optionalActivite.get();
        assertEquals("Docteur", activite.getTitre());
    }

    @Test
    public void testFindAll() {
        Iterable<Activite> activiteList = activiteRepository.findAll();
        assertNotNull(activiteList);
        long count = activiteList.spliterator().getExactSizeIfKnown();
        assertEquals(1, count);
    }

    @Test
    public void testCreateActivite() {
        assertNotNull(curriculumVitae);

        Activite activite = new Activite(curriculumVitae, curriculumVitae.getPersonne());
        activite.setAnnee(2022);
        activite.setNature(NatureActivite.FORMATION);
        activite.setTitre("DEV");
        activite.setTexte_descriptif("Developpeur");
        activite.setAdresse_WEB("https://ontriya.com/activity");
        activiteRepository.save(activite);
        assertNotNull(activite.getId());
    }
    @Test
    public void testUpdate() {
        Optional<Activite> optionalActivite = activiteRepository.findById(activite.getId());
        assertTrue(optionalActivite.isPresent());
        Activite activite = optionalActivite.get();
        activite.setTitre("nouveau titre");
        activiteRepository.save(activite);

        Optional<Activite> updatedActiviteOptional = activiteRepository.findById(activite.getId());
        assertTrue(updatedActiviteOptional.isPresent());
        Activite updatedActivite = updatedActiviteOptional.get();
        assertEquals("nouveau titre", updatedActivite.getTitre());
    }

    @Test
    public void testDelete() {
        activiteRepository.deleteById(activite.getId());
        Optional<Activite> optionalActivite = activiteRepository.findById(activite.getId());
        assertFalse(optionalActivite.isPresent());
    }
}
