package br.com.fiap.mottu.sprint3.controller;

import br.com.fiap.mottu.sprint3.service.FilialService;
import br.com.fiap.mottu.sprint3.service.MotoService;
import br.com.fiap.mottu.sprint3.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UsuarioService usuarioService;
    private final MotoService motoService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, RedirectAttributes redirectAttributes) {
        try {

            model.addAttribute("totalUsuarios", usuarioService.countTotal());
            model.addAttribute("totalMotos", motoService.countTotal());
            model.addAttribute("motosPorModelo", motoService.countByModelo());


            // Retorna o caminho para o template do dashboard
            return "admin/dashboard";

        } catch (Exception e) {
            // Em caso de um erro inesperado ao buscar os dados, redireciona para a home
            redirectAttributes.addFlashAttribute("errorMessage", "Não foi possível carregar os dados do dashboard.");
            return "redirect:/";
        }
    }
}