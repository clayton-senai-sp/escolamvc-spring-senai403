package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.NotasAlunos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotasAlunosRepository extends JpaRepository<NotasAlunos,Long> {
}
