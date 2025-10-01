package br.com.fiap.mottu.sprint3.config.security;

import br.com.fiap.mottu.sprint3.entity.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UtilsSecurity {
    public Usuario getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null  || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new IllegalStateException("Nenhum usuário autenticado encontrado");
        }

        return (Usuario) authentication.getPrincipal();
    }

    public boolean isAdmin(Usuario usuario) {
        return usuario.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public void checkAdminAccess() {
        if (!isAdmin(getUsuarioLogado())) {
            throw new AccessDeniedException("Acesso negado. Operação permitida apenas para administradores.");
        }
    }

    public void checkAdminOrOwnerAccess(Long resourceOwnerId) {
        Usuario usuarioLogado = getUsuarioLogado();
        if (!isAdmin(usuarioLogado) && !usuarioLogado.getId().equals(resourceOwnerId)) {
            throw new AccessDeniedException("Acesso negado. Você não tem permissão para modificar este recurso.");
        }
    }
}
