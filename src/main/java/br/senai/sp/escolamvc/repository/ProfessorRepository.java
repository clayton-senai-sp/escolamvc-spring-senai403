package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.Aluno;
import br.senai.sp.escolamvc.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    List<Professor> findByNomeContainingIgnoreCase(String nome);

    Professor findByEmail(String email);

    Professor findByCpf(String cpf);


    // Pesquisa o email e o id seja diferente
    // do id que está sendo alterado
    Professor findByEmailAndIdNot(String email, Long id);

    // Pesquisa o cpf e o id seja diferente
    // do id que está sendo alterado
    Professor findByCpfAndIdNot(String cpf, Long id);

}
