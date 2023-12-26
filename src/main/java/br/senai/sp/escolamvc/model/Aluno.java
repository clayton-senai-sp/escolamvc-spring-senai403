package br.senai.sp.escolamvc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
// A de Aluno
@DiscriminatorValue(value = "A")
public class Aluno extends Pessoa{


    @NotNull(message = "A matrícula deve ser informada")
    private Long matricula;

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

}