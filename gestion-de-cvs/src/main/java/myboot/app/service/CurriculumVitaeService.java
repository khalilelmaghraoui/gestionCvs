package myboot.app.service;
import myboot.app.dao.CurriculumVitaeRepository;
import myboot.app.model.CurriculumVitae;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CurriculumVitaeService {

    @Autowired
    private CurriculumVitaeRepository cvRepository;

    public List<CurriculumVitae> getAllCurriculumVitaes() {
        return cvRepository.findAll();
    }

    public Optional<CurriculumVitae> getCurriculumVitaeById(Long id) {
        return cvRepository.findById(id);
    }

    public CurriculumVitae createCurriculumVitae(CurriculumVitae cv) {
        return cvRepository.save(cv);
    }

    public CurriculumVitae updateCurriculumVitae(Long id, CurriculumVitae updatedCv) {
        Optional<CurriculumVitae> optCV=cvRepository.findById(id);
        if(optCV.isPresent())
        {
            CurriculumVitae cv=optCV.get();
            cv.setSubject(updatedCv.getSubject());
            cv.setActivites(updatedCv.getActivites());
            cv.setPersonne(updatedCv.getPersonne());

            cvRepository.save(cv);
            return cv;
        }
        else {
            throw new IllegalArgumentException("le CurriculumVitae avec id "+id+" est introuvable!");
        }
    }

    public void deleteCurriculumVitae(Long id) {
        cvRepository.deleteById(id);
    }
}
