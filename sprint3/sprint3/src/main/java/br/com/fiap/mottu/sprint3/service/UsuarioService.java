package br.com.fiap.mottu.sprint3.service;

import br.com.fiap.mottu.sprint3.config.security.UtilsSecurity;
import br.com.fiap.mottu.sprint3.dto.usuario.UsuarioDto;
import br.com.fiap.mottu.sprint3.entity.Filial;
import br.com.fiap.mottu.sprint3.entity.Usuario;
import br.com.fiap.mottu.sprint3.exceptions.IdNaoEncontrado;
import br.com.fiap.mottu.sprint3.exceptions.UsuarioJaCadastrado;
import br.com.fiap.mottu.sprint3.repository.FilialRepository;
import br.com.fiap.mottu.sprint3.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.fiap.mottu.sprint3.dto.usuario.UpdateRoleDto;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final FilialRepository filialRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilsSecurity securityUtils;


    public Usuario register(UsuarioDto dto) throws UsuarioJaCadastrado, IdNaoEncontrado {
        if (usuarioRepository.findByNomeIgnoreCase(dto.userName()).isPresent()) {
            throw new UsuarioJaCadastrado();
        }

        Filial filial = filialRepository.findById(dto.filialId())
                .orElseThrow(IdNaoEncontrado::new);

        Usuario usuario = new Usuario();
        usuario.setNome(dto.userName());
        usuario.setPasswordHash(passwordEncoder.encode(dto.passwordHash()));
        usuario.setTipoPerfil(2);
        usuario.setFilial(filial);


        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getAll() {
        securityUtils.checkAdminAccess();
        return usuarioRepository.findAll();
    }

    public Usuario getById(Long id) throws IdNaoEncontrado {
        securityUtils.checkAdminOrOwnerAccess(id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IdNaoEncontrado("Usuário não encontrado com ID: " + id));
    }

    public Usuario getByName(String nome) {
        Usuario usuario = usuarioRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com nome: " + nome));
        securityUtils.checkAdminOrOwnerAccess(usuario.getId());
        return usuario;
    }

    public Usuario update(Long id, UsuarioDto dto) throws IdNaoEncontrado {
        securityUtils.checkAdminOrOwnerAccess(id);

        Usuario usuarioLogado = securityUtils.getUsuarioLogado();
        Usuario usuarioParaAtualizar = usuarioRepository.findById(id)
                .orElseThrow(() -> new IdNaoEncontrado("Usuário não encontrado com ID: " + id));

        usuarioParaAtualizar.setNome(dto.userName());

        // Atualiza a senha APENAS se uma nova senha for fornecida
        if (dto.passwordHash() != null && !dto.passwordHash().isBlank()) {
            usuarioParaAtualizar.setPasswordHash(passwordEncoder.encode(dto.passwordHash()));
        }

        // Apenas ADMINS podem alterar filial e tipo de perfil
        if (securityUtils.isAdmin(usuarioLogado)) {
            Filial filial = filialRepository.findById(dto.filialId())
                    .orElseThrow(() -> new IdNaoEncontrado("Filial não encontrada com ID: " + dto.filialId()));
            usuarioParaAtualizar.setFilial(filial);

            // Impede que um admin mude o próprio perfil nesta tela
            if (!usuarioLogado.getId().equals(id)) {
                usuarioParaAtualizar.setTipoPerfil(dto.tipoPerfil());
            }
        }

        return usuarioRepository.save(usuarioParaAtualizar);
    }

    public Usuario updateRole(Long userId, UpdateRoleDto dto) throws IdNaoEncontrado {
        securityUtils.checkAdminAccess();
        Usuario adminLogado = securityUtils.getUsuarioLogado();

        if (adminLogado.getId().equals(userId)) {
            throw new IllegalArgumentException("Administradores não podem alterar o próprio perfil através desta funcionalidade.");
        }
        if (dto.tipoPerfil() < 1 || dto.tipoPerfil() > 2) {
            throw new IllegalArgumentException("Tipo de perfil inválido. Use 1 para ADMIN ou 2 para USER.");
        }

        Usuario usuarioParaAtualizar = usuarioRepository.findById(userId)
                .orElseThrow(() -> new IdNaoEncontrado("Usuário não encontrado com ID: " + userId));
        usuarioParaAtualizar.setTipoPerfil(dto.tipoPerfil());

        return usuarioRepository.save(usuarioParaAtualizar);
    }

    public void delete(Long id) throws IdNaoEncontrado {
        securityUtils.checkAdminOrOwnerAccess(id);
        if (!usuarioRepository.existsById(id)) {
            throw new IdNaoEncontrado("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public long countTotal() {
        securityUtils.checkAdminAccess();

        return usuarioRepository.count();
    }

}
