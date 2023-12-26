package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
