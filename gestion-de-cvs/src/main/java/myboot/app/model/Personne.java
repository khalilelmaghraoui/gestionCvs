package myboot.app.model;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "personne")
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @NotBlank(message = "{personne.nom.vide}")
    @Size(max = 255, message = "{personne.nom.long}")
    private String nom;

    @Basic
    @NotBlank(message = "{personne.prenom.vide}")
    @Size(max = 255, message = "{personne.prenom.long}")
    private String prenom;

    @Basic
    @Email(message = "{personne.email}")
    private String adresseElectronique;

    @Basic
    private String site_WEB;

    @Past(message = "{personne.date}")
    private Date date_de_naissance;

    @NotBlank(message = "{personne.motDePasse.blank}")
    @Size(min = 8, message = "{personne.motDePasse.taille}")
    private String mot_de_passe;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "personne")
    @ToString.Exclude
    private CurriculumVitae cv;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "personne")
    @Exclude
    private List<Activite> activites = new LinkedList<>();
}
