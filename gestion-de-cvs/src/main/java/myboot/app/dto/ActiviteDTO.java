package myboot.app.dto;

import lombok.Data;
import myboot.app.model.NatureActivite;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;


@Data
public class ActiviteDTO {
    private Long id;

    @NotNull(message = "L'année ne peut pas être nulle.")
    @Max(value = 2022, message = "L'année doit être inférieure ou égale à 2022.")
    private Integer annee;

    private NatureActivite nature;

    @NotBlank(message = "Le titre ne peut pas être vide.")
    private String titre;

    @Nullable
    private String texte_descriptif;

    @Nullable
    private String adresse_WEB;
}


