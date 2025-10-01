package br.com.fiap.mottu.sprint3.controller;

import br.com.fiap.mottu.sprint3.config.security.UtilsSecurity;
import br.com.fiap.mottu.sprint3.dto.usuario.UsuarioDto;
import br.com.fiap.mottu.sprint3.entity.Usuario;
import br.com.fiap.mottu.sprint3.repository.FilialRepository;
import br.com.fiap.mottu.sprint3.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final FilialRepository filialRepository;
    private final UtilsSecurity securityUtils;


    @GetMapping
    public String listAll(Model model, @RequestParam(required = false) String query) {
        List<Usuario> usuarios;
        if (query != null && !query.isBlank()) {
            try {
                usuarios = List.of(usuarioService.getByName(query));
            } catch (Exception e) {
                usuarios = Collections.emptyList();
                model.addAttribute("errorMessage", "Usuário '" + query + "' não encontrado.");
            }
        } else {
            usuarios = usuarioService.getAll();
        }
        model.addAttribute("usuarios", usuarios);
        return "usuarios/lista";
    }

    // Mostrar formulário de criação de usuário
    @GetMapping("/novo")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("usuarioDto")) {
            model.addAttribute("usuarioDto", new UsuarioDto(null, null, null, null, null, null, null));
        }
        model.addAttribute("filiais", filialRepository.findAll());
        return "usuarios/form";
    }

    // Criar novo usuário
    @PostMapping("/novo")
    public String createUsuario(@Valid @ModelAttribute("usuarioDto") UsuarioDto dto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        if (result.hasErrors()) {
            model.addAttribute("filiais", filialRepository.findAll());
            return "usuarios/form";
        }

        try {
            usuarioService.register(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário criado com sucesso!");
            return "redirect:/usuarios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao criar usuário: " + e.getMessage());
            redirectAttributes.addFlashAttribute("usuarioDto", dto);
            return "redirect:/usuarios/novo";
        }
    }

    // Mostrar formulário de edição
    @GetMapping("/{id}/editar")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.getById(id);
            UsuarioDto dto = new UsuarioDto(
                    usuario.getEmail(),
                    usuario.getCpf(),
                    usuario.getTelefone(),
                    usuario.getUsername(),
                    "", // não preenche a senha por segurança
                    usuario.getTipoPerfil(),
                    usuario.getFilial() != null ? usuario.getFilial().getId() : null
            );
            model.addAttribute("usuarioDto", dto);
            model.addAttribute("usuarioId", id);
            model.addAttribute("filiais", filialRepository.findAll());
            return "usuarios/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/usuarios";
        }
    }

    // Atualizar usuário
    @PostMapping("/{id}/editar")
    public String updateUsuario(@PathVariable Long id,
                                @Valid @ModelAttribute("usuarioDto") UsuarioDto dto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        if (result.hasErrors()) {
            model.addAttribute("usuarioId", id);
            model.addAttribute("filiais", filialRepository.findAll());
            return "usuarios/form";
        }

        try {
            usuarioService.update(id, dto);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário atualizado com sucesso!");
            return "redirect:/usuarios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar usuário: " + e.getMessage());
            redirectAttributes.addFlashAttribute("usuarioDto", dto);
            return "redirect:/usuarios/" + id + "/editar";
        }
    }

    // Deletar usuário
    @PostMapping("/{id}/deletar")
    public String deleteUsuario(@PathVariable Long id,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        try {
            Usuario usuarioLogado = securityUtils.getUsuarioLogado();
            boolean isSelfDelete = usuarioLogado.getId().equals(id);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            usuarioService.delete(id);

            if (isSelfDelete) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
                return "redirect:/login?logout";
            }

            redirectAttributes.addFlashAttribute("successMessage", "Usuário deletado com sucesso!");
            return "redirect:/usuarios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao deletar usuário: " + e.getMessage());
            return "redirect:/usuarios";
        }
    }
}