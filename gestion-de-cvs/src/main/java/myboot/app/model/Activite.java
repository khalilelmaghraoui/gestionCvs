package myboot.app.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.springframework.lang.Nullable;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;
@Entity
@Data
@NoArgsConstructor
@Table(name = "activite")
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "L'année ne peut pas être nulle.")
    @Max(value = 2022, message = "L'année doit être inférieure ou égale à 2022.")
    private Integer annee;

    @Enumerated(EnumType.STRING)
    private NatureActivite nature;

    @NotBlank(message = "Le titre ne peut pas être vide.")
    private String titre;

    @Nullable
    private String texte_descriptif;

    @Nullable
    private String adresse_WEB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    @Exclude
    private CurriculumVitae cv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_id")
    @Exclude
    private Personne personne;

    public Activite(CurriculumVitae cv, Personne p) {
        super();
        this.cv = cv;
        this.personne = p;
    }
}



