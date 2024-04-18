package br.senai.sp.escolamvc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotasAlunos {

    @Id
    private Long id;
    private String disciplina;
    private Double nota;
}
