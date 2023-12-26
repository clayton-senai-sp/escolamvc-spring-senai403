package br.senai.sp.escolamvc.controller;

import br.senai.sp.escolamvc.model.Responsavel;
import br.senai.sp.escolamvc.repository.ResponsavelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/responsavel")
public class ResponsavelController {

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @GetMapping
    public String listagem(Model model){

        // injetar uma lista de responsáveis

        List<Responsavel> listaResponsaveis = responsavelRepository.findAll();

        model.addAttribute("responsaveis",listaResponsaveis);

        return "responsavel/listagem";
    }

    @GetMapping("/novo")
    public String cadastrar(Model model){
        model.addAttribute("responsavel", new Responsavel());
        return "responsavel/inserir";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Responsavel responsavel,
                         BindingResult result,
                         RedirectAttributes redirectAttributes){

        if (result.hasErrors()){
            return "responsavel/inserir";
        }

        redirectAttributes.addFlashAttribute("mensagem",
                "Responsável cadastrado com sucesso!");

        responsavelRepository.save(responsavel);
        return "redirect:/responsavel/novo";
    }

    /*
     * Método que direciona para templates/responsal/alterar.html
     * carrega os dados do responsável a ser alterado
     */
    @GetMapping("/alterar/{id}")
    public String alterar(Model model, @PathVariable Long id) {

        // Busca o responsável no banco de dados pelo id
        Responsavel responsavel = responsavelRepository.findById(id).get();

        // Adiciona o responsável no objeto model para ser carregado no template
        model.addAttribute("responsavel", responsavel);

        // Retorna o template responsavel/alterar.html
        return "responsavel/alterar";
    }

    /*
    Método para deletar um responsável
     */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        // Busca o responsável no banco de dados pelo id
        Responsavel responsavel = responsavelRepository.findById(id).get();

        // Remove o responsável do banco de dados
        responsavelRepository.delete(responsavel);

        // Adiciona uma mensagem que será exibida no template
        redirectAttributes.addFlashAttribute("mensagem", "Responsável excluído com sucesso!");

        // Redireciona para a página de listagem de responsáveis
        return "redirect:/responsavel";
    }


}
