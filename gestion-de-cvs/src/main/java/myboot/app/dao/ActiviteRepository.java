package myboot.app.dao;

import myboot.app.model.Personne;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import myboot.app.model.Activite;

import java.util.List;

@Repository
@Transactional
public interface ActiviteRepository extends CrudRepository<Activite, Long> {
    List<Activite> findAll();

}