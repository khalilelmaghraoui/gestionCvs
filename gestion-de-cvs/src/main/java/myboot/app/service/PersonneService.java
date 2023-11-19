package myboot.app.service;

import myboot.app.dao.PersonneRepository;
import myboot.app.model.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    public List<Personne> getAllPersonnes() {
        return personneRepository.findAll();
    }

    public Optional<Personne> getPersonneById(Long id) {
        return personneRepository.findById(id);
    }

    public Personne createPersonne(Personne personne) {
        return personneRepository.save(personne);
    }

    public Personne updatePersonne(Long id, Personne updatedPersonne) {
        Optional<Personne> OptPersonne=personneRepository.findById(id);
        if(OptPersonne.isPresent())
        {
            Personne p=OptPersonne.get();
            p.setNom(updatedPersonne.getNom());
            p.setPrenom(updatedPersonne.getPrenom());
            p.setCv(updatedPersonne.getCv());
            p.setActivites(updatedPersonne.getActivites());
            p.setDate_de_naissance(updatedPersonne.getDate_de_naissance());
            p.setSite_WEB(updatedPersonne.getSite_WEB());
            p.setAdresseElectronique(updatedPersonne.getAdresseElectronique());
            p.setMot_de_passe(updatedPersonne.getMot_de_passe());

            personneRepository.save(p);
            return p;
        }
        else
        {
            throw new IllegalArgumentException("Personne avec l'id " + id + " est introuvable");
        }
    }

    public void deletePersonne(Long id) {
        personneRepository.deleteById(id);
    }
    public List<Personne> searchPersonnes(String keyword) {
        return personneRepository.findByAdresseElectroniqueContainingIgnoreCase(keyword);
    }
}