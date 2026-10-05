package com.dimdim.app.controller;

import com.dimdim.app.model.Categoria;
import com.dimdim.app.model.Transacao;
import com.dimdim.app.repository.CategoriaRepository;
import com.dimdim.app.repository.TransacaoRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoRepository transacaoRepository;
    private final CategoriaRepository categoriaRepository;

    public TransacaoController(
            TransacaoRepository transacaoRepository,
            CategoriaRepository categoriaRepository) {

        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "transacoes",
                transacaoRepository.findAllByOrderByDataDescIdDesc()
        );

        return "transacoes/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {

        Transacao transacao = new Transacao();
        transacao.setCategoria(new Categoria());

        model.addAttribute("transacao", transacao);

        carregarCategorias(model);

        return "transacoes/form";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute Transacao transacao,
            BindingResult result,
            Model model) {

        if (transacao.getCategoria() == null ||
                transacao.getCategoria().getId() == null) {

            result.rejectValue(
                    "categoria",
                    "categoria.obrigatoria",
                    "Selecione uma categoria"
            );

        } else {

            categoriaRepository
                    .findById(transacao.getCategoria().getId())
                    .ifPresentOrElse(
                            transacao::setCategoria,
                            () -> result.rejectValue(
                                    "categoria",
                                    "categoria.invalida",
                                    "Categoria inválida"
                            )
                    );
        }

        if (result.hasErrors()) {

            carregarCategorias(model);

            return "transacoes/form";
        }

        transacaoRepository.save(transacao);

        return "redirect:/transacoes";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Transacao transacao = transacaoRepository
                .findById(id)
                .orElseThrow();

        model.addAttribute("transacao", transacao);

        carregarCategorias(model);

        return "transacoes/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {

        transacaoRepository.deleteById(id);

        return "redirect:/transacoes";
    }

    private void carregarCategorias(Model model) {

        model.addAttribute(
                "categorias",
                categoriaRepository.findAll(
                        Sort.by("nome").ascending()
                )
        );
    }
}
