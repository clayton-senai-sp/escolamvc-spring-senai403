package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    List<Turma> findByCursoContainingIgnoreCase(String curso);
}
