package myboot.app.dto;

import lombok.Data;
import java.util.List;
import javax.validation.constraints.*;
@Data
public class CurriculumVitaeDTO {
    private Long id;

    @NotBlank(message = "Le sujet ne peut pas être vide.")
    private String subject;
}