package br.com.fiap.mottu.sprint3.controller;

import br.com.fiap.mottu.sprint3.dto.moto.MotoDto;
import br.com.fiap.mottu.sprint3.entity.Moto;
import br.com.fiap.mottu.sprint3.entity.enums.TipoMoto;
import br.com.fiap.mottu.sprint3.service.FilialService;
import br.com.fiap.mottu.sprint3.service.MotoService;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/motos")
@RequiredArgsConstructor
public class MotoController {

    private final MotoService motoService;
    private final FilialService filialService;

    @GetMapping
    public String listAll(Model model,
                          @RequestParam(required = false) String tipoBusca,
                          @RequestParam(required = false) String query) {

        List<Moto> motos;

        if (query != null && !query.isBlank()) {
            try {
                if ("PLACA".equals(tipoBusca)) {
                    motos = List.of(motoService.getByPlaca(query));
                } else {
                    motos = List.of(motoService.getByChassi(query));
                }
            } catch (Exception e) {
                motos = Collections.emptyList();
                model.addAttribute("errorMessage", "Moto não encontrada: " + e.getMessage());
            }
        } else {
            motos = motoService.getAll();
        }

        model.addAttribute("motos", motos);
        return "motos/lista";
    }

    @GetMapping("/nova")
    public String showCreateForm(Model model) {
        model.addAttribute("motoDto", new MotoDto(null, null, null, null, null, null, null, null, null, null));
        model.addAttribute("filiais", filialService.getAll());
        model.addAttribute("tiposMoto", TipoMoto.values());
        return "motos/form";
    }

    @PostMapping("/nova")
    public String createMoto(@Valid @ModelAttribute("motoDto") MotoDto dto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("filiais", filialService.getAll());
            model.addAttribute("tiposMoto", TipoMoto.values());
            return "motos/form";
        }
        try {
            motoService.save(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Moto criada com sucesso!");
            return "redirect:/motos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao criar moto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("motoDto", dto);
            return "redirect:/motos/nova";
        }
    }

    @GetMapping("/{id}/editar")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Moto moto = motoService.getById(id);
            MotoDto dto = new MotoDto(
                    moto.getTipoMoto().name(),
                    moto.getPlaca(),
                    moto.getChassi(),
                    moto.getIotInfo(),
                    moto.getRfidTag(),
                    moto.getLocalizacao(),
                    moto.getStatusAtual(),
                    moto.getMotolog() != null ? moto.getMotolog().getId() : null,
                    moto.getFilial() != null ? moto.getFilial().getId() : null,
                    moto.getTipoMoto()
            );
            model.addAttribute("motoDto", dto);
            model.addAttribute("motoId", id);
            model.addAttribute("filiais", filialService.getAll());
            model.addAttribute("tiposMoto", TipoMoto.values());
            return "motos/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Moto não encontrada: " + e.getMessage());
            return "redirect:/motos";
        }
    }

    @PostMapping("/{id}/editar")
    public String updateMoto(@PathVariable Long id,
                             @Valid @ModelAttribute("motoDto") MotoDto dto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("motoId", id);
            model.addAttribute("filiais", filialService.getAll());
            model.addAttribute("tiposMoto", TipoMoto.values());
            return "motos/form";
        }
        try {
            motoService.update(id, dto);
            redirectAttributes.addFlashAttribute("successMessage", "Moto atualizada com sucesso!");
            return "redirect:/motos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar moto: " + e.getMessage());
            return "redirect:/motos/" + id + "/editar";
        }
    }

    @PostMapping("/{id}/status")
    public String updateMotoStatus(@PathVariable Long id,
                                   @RequestParam("novoEstado")
                                   RedirectAttributes redirectAttributes) {
        try {
            motoService.updateStatus(id);
            redirectAttributes.addFlashAttribute("successMessage", "Status da moto atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar status: " + e.getMessage());
        }
        return "redirect:/motos";
    }

    @PostMapping("/{id}/deletar")
    public String deleteMoto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            motoService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Moto deletada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao deletar moto: " + e.getMessage());
        }
        return "redirect:/motos";
    }
}