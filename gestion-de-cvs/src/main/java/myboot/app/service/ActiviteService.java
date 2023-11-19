package myboot.app.service;

import myboot.app.dao.ActiviteRepository;
import myboot.app.model.Activite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActiviteService {

    @Autowired
    private ActiviteRepository activiteRepository;

    public List<Activite> getAllActivites() {
        return activiteRepository.findAll();
    }

    public Optional<Activite> getActiviteById(Long id) {
        return activiteRepository.findById(id);
    }

    public Activite createActivite(Activite activite) {
        return activiteRepository.save(activite);
    }

    public Activite updateActivite(Long id, Activite updatedActivite) {
        Optional<Activite> optAct=activiteRepository.findById(id);
        if(optAct.isPresent())
        {
            Activite act=optAct.get();
            act.setCv(updatedActivite.getCv());
            act.setNature(updatedActivite.getNature());
            act.setAnnee(updatedActivite.getAnnee());
            act.setTitre(updatedActivite.getTitre());
            act.setAdresse_WEB(updatedActivite.getAdresse_WEB());
            act.setTexte_descriptif(updatedActivite.getTexte_descriptif());
            act.setPersonne(updatedActivite.getPersonne());
            activiteRepository.save(act);
            return act;
        }
        else {
            throw new IllegalArgumentException("Activite avec ce id: "+id+" est introuvable !");
        }
    }

    public void deleteActivite(Long id) {
        activiteRepository.deleteById(id);
    }
}
