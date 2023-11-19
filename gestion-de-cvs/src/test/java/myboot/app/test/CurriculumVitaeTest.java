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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CurriculumVitaeTest {

	@Autowired
	private CurriculumVitaeRepository cvRepository;

	@Autowired
	private PersonneRepository personneRepository;

	@Autowired
	private ActiviteRepository activiteRepository;

	CurriculumVitae cv;

	@BeforeEach
	public void setup() {
		cvRepository.deleteAll();
		personneRepository.deleteAll();
		Personne personne = new Personne();
		personne.setNom("elie");
		personne.setPrenom("nicolas");
		personne.setAdresseElectronique("elie.nicolas@etu.uni-amu.fr");
		personne.setMot_de_passe("elie1234");
		Personne savedPersonne = personneRepository.save(personne);
		cv = new CurriculumVitae(savedPersonne);
		cv.setSubject("CV Subject");
		cvRepository.save(cv);
	}
	
	@Test
    public void testAddActivite() {
        Activite activite = new Activite(cv, cv.getPersonne());
        activite.setAnnee(2022);
        activite.setNature(NatureActivite.FORMATION); 
        activite.setTitre("DEV");
        activite.setTexte_descriptif("Developpeur");
        activite.setAdresse_WEB("https://ontriya.com/activity");
        activiteRepository.save(activite);
        System.out.println(activite);
        assertNotNull(activite.getId());
        CurriculumVitae updatedCv = cvRepository.findById(cv.getId()).orElse(null);
        assertNotNull(updatedCv);
        cv.addActivite(activite);
        assertEquals(1, updatedCv.getActivites().size());
        assertEquals(activite.getId(), updatedCv.getActivites().get(0).getId());
    }

	@Test
	public void testFindById() {
		Optional<CurriculumVitae> optionalCv = cvRepository.findById(cv.getId());
		assertTrue(optionalCv.isPresent());
		CurriculumVitae cv = optionalCv.get();
		assertEquals("CV Subject", cv.getSubject());
	}

	@Test
	public void testFindAll() {
		Iterable<CurriculumVitae> cvList = cvRepository.findAll();
		List<CurriculumVitae> cvListAsList = new ArrayList<>();
		cvList.forEach(cvListAsList::add);
		assertEquals(1, cvListAsList.size());
	}

	@Test
	public void testUpdate() {
		Optional<CurriculumVitae> optionalCv = cvRepository.findById(cv.getId());
		assertTrue(optionalCv.isPresent());
		CurriculumVitae cv = optionalCv.get();
		cv.setSubject("Updated CV Subject");
		cvRepository.save(cv);
		Optional<CurriculumVitae> updatedCvOptional = cvRepository.findById(cv.getId());
		assertTrue(updatedCvOptional.isPresent());
		CurriculumVitae updatedCv = updatedCvOptional.get();
		assertEquals("Updated CV Subject", updatedCv.getSubject());
	}

	@Test
	public void testDelete() {
		cvRepository.deleteById(cv.getId());
		Optional<CurriculumVitae> optionalCv = cvRepository.findById(cv.getId());
		assertFalse(optionalCv.isPresent());
	}
}
