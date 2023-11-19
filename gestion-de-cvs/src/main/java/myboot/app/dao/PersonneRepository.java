package myboot.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import myboot.app.model.Personne;

import java.util.List;

@Repository
@Transactional
public interface PersonneRepository extends CrudRepository<Personne, Long> {
    List<Personne> findAll();
    List<Personne> findByAdresseElectroniqueContainingIgnoreCase(String keyword);

}