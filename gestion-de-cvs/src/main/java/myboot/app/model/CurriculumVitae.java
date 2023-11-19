package myboot.app.model;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Entity
@Data
@Table(name = "curriculumVitae")
@NoArgsConstructor
public class CurriculumVitae {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_id")
    @Exclude
    private Personne personne;

    @OneToMany(mappedBy = "cv", fetch = FetchType.LAZY)
    @Exclude
    private List<Activite> activites = new LinkedList<>();

    public CurriculumVitae(Personne p) {
        super();
        this.personne = p;
    }

    public void addActivite(Activite a) {
        this.activites.add(a);
        a.setCv(this);
    }
}

