package br.senai.sp.escolamvc.controller;

import br.senai.sp.escolamvc.model.Aluno;
import br.senai.sp.escolamvc.model.EnderecoPessoa;
import br.senai.sp.escolamvc.model.Professor;
import br.senai.sp.escolamvc.model.Turma;
import br.senai.sp.escolamvc.repository.ProfessorRepository;
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
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TurmaRepository turmaRepository;
    /*
     * Método que direciona para templates/alunos/listagem.html
     */
    @GetMapping
    public String listagem(Model model) {

        // Busca a lista de alunos no banco de dados
        List<Professor> listaProfessores = professorRepository.findAll();

        // Adiciona a lista de alunos no objeto model para ser carregado no template
        model.addAttribute("professores", listaProfessores);

        // Retorna o template aluno/listagem.html
        return "professor/listagem";
    }

    /*
     * Método de acesso à página http://localhost:8080/professor/novo
     */
    @GetMapping("/novo")
    public String cadastrar(Model model){

        // Adiciona um objeto professor vazio para
        // ser carregado no formulário
        model.addAttribute("professor", new Professor());

        // Retorna o template professor/inserir.html
        return "professor/inserir";
    }

    @PostMapping("/salvar")
    public String salvarAluno(@Valid Professor professor, BindingResult result,
                              RedirectAttributes attributes) {


        // Se houver erro de validação, retorna para o template alunos/inserir.html
        if (result.hasErrors()) {
            return "professor/inserir";
        }

        // Verifica se existe erros personalizados
        if (errosPersonalizadosInsercao(professor, result).hasErrors()) {
            return "professor/inserir";
        }

        // Salva o aluno no banco de dados
        professorRepository.save(professor);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Professor salvo com sucesso!");

        // Redireciona para a página de listagem de professores
        return "redirect:/professor/novo";
    }

    public BindingResult errosPersonalizadosInsercao(Professor professor, BindingResult result) {

        // Verifica se o e-mail já está cadastrado
        if (professorRepository.findByEmail(professor.getEmail()) != null) {
            result.rejectValue("email", "email.existente",
                    "Já existe um aluno cadastrado com este e-mail");
        }

        // Verifica se o CPF já está cadastrado
        if (professorRepository.findByCpf(professor.getCpf()) != null) {
            result.rejectValue("cpf", "cpf.existente",
                    "Já existe um aluno cadastrado com este CPF");
        }
        return result;
    }
}
