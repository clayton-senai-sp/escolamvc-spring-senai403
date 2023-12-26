package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
