package myboot.app.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
public class PersonneDTO {
    private Long id;

    @NotBlank(message = "{personne.nom.vide}")
    @Size(max = 255, message = "{personne.nom.long}")
    private String nom;

    @NotBlank(message = "{personne.prenom.vide}")
    @Size(max = 255, message = "{personne.prenom.long}")
    private String prénom;

    @Email(message = "{personne.email}")
    private String adresse_électronique;

    private String site_WEB;

    @Past(message = "{personne.date}")
    private Date date_de_naissance;
}
