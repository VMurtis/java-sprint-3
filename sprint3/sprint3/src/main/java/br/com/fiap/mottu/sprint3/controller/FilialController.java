package br.com.fiap.mottu.sprint3.controller;

import br.com.fiap.mottu.sprint3.dto.filial.FilialDto;
import br.com.fiap.mottu.sprint3.entity.Filial;
import br.com.fiap.mottu.sprint3.service.FilialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/filiais")
@RequiredArgsConstructor
public class FilialController {
    private  FilialService filialService;

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("filiais", filialService.getAll());
        return "filiais/lista";
    }

    @GetMapping("/nova")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("filialDto")) {
            model.addAttribute("filialDto", new FilialDto(null, null, null, null, null, null, null));
        }
        return "filiais/form";
    }

    @PostMapping("/nova")
    // Adicionamos @Valid e BindingResult
    public String createFilial(@Valid @ModelAttribute("filialDto") FilialDto dto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) { // Adicionamos o Model

        // Se houver erros de validação, retorna para o formulário
        if (result.hasErrors()) {
            return "filiais/form";
        }

        try {
            filialService.save(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Filial criada com sucesso!");
            return "redirect:/filiais"; // Redireciona em caso de sucesso
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao criar filial: " + e.getMessage());
            redirectAttributes.addFlashAttribute("filialDto", dto);
            return "redirect:/filiais/nova";
        }
    }

    @GetMapping("/{id}/editar")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Filial filial = filialService.getById(id);
            // Preenche o DTO com os dados existentes se não houver um no modelo (útil após erro de validação)
            if (!model.containsAttribute("filialDto")) {
                FilialDto dto = new FilialDto(
                        filial.getFilialName(),
                        filial.getEndereco(),
                        filial.getContato(),
                        filial.getHorarioFuncionamento(),
                        filial.getLayoutsFilial() != null ? filial.getLayoutsFilial().getId() : null,
                        filial.getUsuarios(),
                        filial.getMotos()

                );
                model.addAttribute("filialDto", dto);
            }
            model.addAttribute("filialId", id);
            return "filiais/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Filial não encontrada: " + e.getMessage());
            return "redirect:/filiais";
        }
    }

    @PostMapping("/{id}/editar")
    // Adicionamos @Valid e BindingResult aqui também
    public String updateFilial(@PathVariable Long id,
                               @Valid @ModelAttribute("filialDto") FilialDto dto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) { // Adicionamos o Model

        // Se houver erros de validação, retorna para a página de edição
        if (result.hasErrors()) {
            model.addAttribute("filialId", id);
            return "filiais/form";
        }

        try {
            filialService.update(id, dto);
            redirectAttributes.addFlashAttribute("successMessage", "Filial atualizada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar filial: " + e.getMessage());
        }
        return "redirect:/filiais";
    }

    @PostMapping("/{id}/deletar")
    public String deleteFilial(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            filialService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Filial deletada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao deletar filial: " + e.getMessage());
        }
        return "redirect:/filiais";
    }
}
