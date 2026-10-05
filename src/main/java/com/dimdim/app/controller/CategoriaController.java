package com.dimdim.app.controller;

import com.dimdim.app.model.Categoria;
import com.dimdim.app.repository.CategoriaRepository;
import com.dimdim.app.repository.TransacaoRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    private final TransacaoRepository transacaoRepository;

    public CategoriaController(
            CategoriaRepository categoriaRepository,
            TransacaoRepository transacaoRepository) {

        this.categoriaRepository = categoriaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "categorias",
                categoriaRepository.findAll(Sort.by("nome").ascending())
        );

        return "categorias/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {

        model.addAttribute("categoria", new Categoria());

        return "categorias/form";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute Categoria categoria,
            BindingResult result) {

        if (result.hasErrors()) {
            return "categorias/form";
        }

        categoriaRepository.save(categoria);

        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Categoria categoria = categoriaRepository
                .findById(id)
                .orElseThrow();

        model.addAttribute("categoria", categoria);

        return "categorias/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        if (transacaoRepository.existsByCategoria_Id(id)) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Não é possível excluir uma categoria que possui transações."
            );

            return "redirect:/categorias";
        }

        categoriaRepository.deleteById(id);

        return "redirect:/categorias";
    }
}