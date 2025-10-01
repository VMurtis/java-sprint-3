package br.com.fiap.mottu.sprint3.repository;

import br.com.fiap.mottu.sprint3.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNomeIgnoreCase(String nome);
}
