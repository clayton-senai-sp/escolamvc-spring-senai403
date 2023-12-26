package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
