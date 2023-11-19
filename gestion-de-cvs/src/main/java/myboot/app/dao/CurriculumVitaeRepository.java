package myboot.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import myboot.app.model.CurriculumVitae;

import java.util.List;

@Repository
@Transactional
public interface CurriculumVitaeRepository extends CrudRepository<CurriculumVitae, Long> {
    List<CurriculumVitae> findAll();
}