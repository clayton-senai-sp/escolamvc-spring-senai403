package br.senai.sp.escolamvc.model;
import br.senai.sp.escolamvc.enums.Funcao;
import jakarta.persistence.*;

@Entity
// D de Docente
@DiscriminatorValue(value = "D")
public class Professor extends Funcionario{
    public Professor() {
        this.setFuncao(Funcao.PROFESSOR);
    }
}
