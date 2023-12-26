package br.senai.sp.escolamvc.controller;

import br.senai.sp.escolamvc.model.Turma;
import br.senai.sp.escolamvc.repository.TurmaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping
    public String listagem(Model model){

        // Busca a lista as turmas no banco de dados
        List<Turma> listaTurmas = turmaRepository.findAll();

        // Adiciona a lista de turmas no objeto model para ser carregado no template
        model.addAttribute("turmas", listaTurmas);

        return "turma/listagem";
    }

    @PostMapping("/buscar")
    public String buscar(Model model, String nome) {
        if (nome == null) {
            return "redirect:/turma";
        }
        List<Turma> listaTurmas = turmaRepository.findByCursoContainingIgnoreCase(nome);
        model.addAttribute("turmas",listaTurmas);
        return "turma/listagem";
    }

    @GetMapping("/novo")
    public String cadastrar(Model model){
        model.addAttribute("turma", new Turma());
        return "turma/inserir";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Turma turma, BindingResult result,
                         RedirectAttributes attributes){

        if (result.hasErrors()){
            return "turma/inserir";
        }

        turmaRepository.save(turma);
        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Turma inserida com sucesso!");
        return "redirect:/turma/novo";
    }


}
