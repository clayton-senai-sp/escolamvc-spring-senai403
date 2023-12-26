package br.senai.sp.escolamvc.controller;
import br.senai.sp.escolamvc.model.*;
import br.senai.sp.escolamvc.repository.AlunoRepository;
import br.senai.sp.escolamvc.repository.TurmaRepository;
import br.senai.sp.escolamvc.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
     * Método que direciona para templates/alunos/listagem.html
     */
    @GetMapping
    public String listagem(Model model) {

        // Busca a lista de alunos no banco de dados
        List<Aluno> listaAlunos = alunoRepository.findAll();

        // Adiciona a lista de alunos no objeto model para ser carregado no template
        model.addAttribute("alunos", listaAlunos);

        // Retorna o template aluno/listagem.html
        return "aluno/listagem";
    }

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nome") String nome) {
        if (nome == null) {
            return "redirect:/aluno";
        }
        List<Aluno> listaAlunos = alunoRepository.findByNomeContainingIgnoreCase(nome);
        model.addAttribute("alunos",listaAlunos);
        return "aluno/listagem";
    }


    /*
    * Método de acesso à página http://localhost:8080/aluno/novo
    */
    @GetMapping("/novo")
    public String cadastrar(Model model){

        // Adiciona um objeto aluno vazio para
        // ser carregado no formulário
        model.addAttribute("aluno", new Aluno());

        // Adiciona um objeto endereco vazio para
        // ser carregado no formulário
        model.addAttribute("endereco", new EnderecoPessoa());

        // Lista de turmas
        //List<Turma> listaTurmas = turmaRepository.findAll();
        //model.addAttribute("turmas", listaTurmas);



        // Retorna o template aluno/inserir.html
        return "aluno/inserir";
    }

    @PostMapping("/salvar")
    public String salvarAluno(@Valid Aluno aluno, BindingResult result,
                              RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template alunos/inserir.html
        if (result.hasErrors()) {
            return "aluno/inserir";
        }

        // Verifica se existe erros personalizados
        if (errosPersonalizadosInsercao(aluno, result).hasErrors()) {
            return "aluno/inserir";
        }

        // Verifica se turmas foram selecionadas
        if(!aluno.getTurmas().isEmpty()){

            // percorre a lista de turmas selecionadas
            for(Turma turma : aluno.getTurmas()){

                // busca a turma no banco de dados
                Turma turmaBanco = turmaRepository.findById(turma.getId()).orElseThrow(()
                        -> new IllegalArgumentException("ID inválido"));

                // remove as turmas preechidas pelo usuário
                aluno.getTurmas().remove(turma);

                // adiciona a turma na lista de turmas do aluno
                aluno.addTurma(turmaBanco);

                // adiciciona a turma ao aluno
                turmaBanco.addPessoa(aluno);
            }

        }

        // Seta a Collection de Roles
        Collection<Role> roles = new ArrayList<Role>();
        Role role = new Role();
        role.setName("ROLE_USER");
        roles.add(role);


        aluno.setSenha(bCryptPasswordEncoder.encode(aluno.getSenha()));

        // Seta os dados do usuário
        User user = new User();
        user.setFirstName(aluno.getNome());
        user.setLastName(aluno.getNome());
        user.setUsername(aluno.getEmail());
        user.setPassword(aluno.getSenha());
        user.setRoles(roles);

        // Seta o usuário no aluno
        aluno.setUser(user);




        // Salva o aluno no banco de dados
        alunoRepository.save(aluno);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/aluno/novo";
    }

    public BindingResult errosPersonalizadosInsercao(Aluno aluno, BindingResult result) {

        // Verifica se o e-mail já está cadastrado
        if (alunoRepository.findByEmail(aluno.getEmail()) != null) {
            result.rejectValue("email", "email.existente",
                    "Já existe um aluno cadastrado com este e-mail");
        }

        // Verifica se o CPF já está cadastrado
        if (alunoRepository.findByCpf(aluno.getCpf()) != null) {
            result.rejectValue("cpf", "cpf.existente",
                    "Já existe um aluno cadastrado com este CPF");
        }
        return result;
    }


    public BindingResult errosPersonalizadosAlteracao(Aluno aluno, BindingResult result) {

        // Verifica se o e-mail já está cadastrado
        if (alunoRepository.findByEmailAndIdNot(aluno.getEmail(), aluno.getId()) != null) {
            result.rejectValue("email", "email.existente",
                    "Já existe um aluno cadastrado com este e-mail");
        }

        // Verifica se o CPF já está cadastrado
        if (alunoRepository.findByCpfAndIdNot(aluno.getCpf(), aluno.getId()) != null) {
            result.rejectValue("cpf", "cpf.existente",
                    "Já existe um aluno cadastrado com este CPF");
        }

        return result;
    }




    /*
     * Método que direciona para templates/alunos/alterar.html
     */
    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o aluno no banco de dados
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o aluno no objeto model para ser carregado no formulário
        model.addAttribute("aluno", aluno);

        // Retorna o template aluno/alterar.html
        return "aluno/alterar";
    }


    /*
     * Método que é invocado ao clicar no botão "Salvar" do template alunos/alterar.html
     * O objeto aluno é carregado com os dados informados no formulário.
     * O objeto result contém o resultado da validação do formulário.
     * O objeto attributes é utilizado para enviar uma mensagem para o template.
     */
    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, @Valid Aluno aluno,
                          BindingResult result, RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template alunos/alterar.html
        if (result.hasErrors()) {
            return "aluno/alterar";
        }

        // Verifica se existe erros personalizados
        if (errosPersonalizadosAlteracao(aluno, result).hasErrors()) {
            return "aluno/alterar";
        }

        // Busca o aluno no banco de dados
        Aluno alunoAtualizado = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));


        // Seta os dados do aluno
        alunoAtualizado.setMatricula(aluno.getMatricula());
        alunoAtualizado.setNome(aluno.getNome());
        alunoAtualizado.setEmail(aluno.getEmail());
        alunoAtualizado.setCpf(aluno.getCpf());
        alunoAtualizado.setEnderecoPessoa(aluno.getEnderecoPessoa());
        alunoAtualizado.setTelefones(aluno.getTelefones());

        // Salva o aluno no banco de dados
        alunoRepository.save(alunoAtualizado);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Aluno atualizado com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/aluno";
    }

    /*
     * Método para excluir um aluno
     */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o aluno no banco de dados
        Aluno aluno = alunoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o aluno do banco de dados
        alunoRepository.delete(aluno);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Aluno excluído com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/aluno";
    }

    @PostMapping("/addTelefone")
    public String addTelefone(Aluno aluno) {
        aluno.addTelefone(new Telefone());
        return "aluno/inserir :: telefones";
    }

    @PostMapping("/removeTelefone")
    public String removeTelefone(Aluno aluno, @RequestParam("removeDynamicRow") Integer telefoneIndex) {
        aluno.getTelefones().remove(telefoneIndex.intValue());
        return "aluno/inserir :: telefones";
    }

    @PostMapping("/addTurma")
    public String addTurma(Aluno aluno, Model model){

        // Lista de turmas
        List<Turma> listaTurmas = turmaRepository.findAll();
        model.addAttribute("turmas", listaTurmas);

        aluno.addTurma(new Turma());
        return "aluno/inserir :: turmas";
    }

    @PostMapping("/removeTurma")
    public String removeTurma(Aluno aluno, @RequestParam("removeDynamicRowTurma") Integer turmaIndex) {
        aluno.getTurmas().remove(turmaIndex.intValue());
        return "aluno/inserir :: turmas";
    }

    // Recupera a lista de turmas e retorna um json
    @GetMapping("/turmas")
    public @ResponseBody List<Turma> getTurmas() {
        return turmaRepository.findAll();
    }


}
