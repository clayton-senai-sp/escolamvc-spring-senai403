package br.senai.sp.escolamvc.api;


import br.senai.sp.escolamvc.model.NotasAlunos;
import br.senai.sp.escolamvc.repository.NotasAlunosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notas-alunos")
public class NotasAlunosApiController {

    @Autowired
    private NotasAlunosRepository notasAlunosRepository;

    @GetMapping("/listar")
    public List<NotasAlunos> listar(){
        return notasAlunosRepository.findAll();
    }
}
