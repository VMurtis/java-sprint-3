package br.com.fiap.mottu.sprint3.repository;

import br.com.fiap.mottu.sprint3.entity.LayoutsFilial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LayoutRepository extends JpaRepository<LayoutsFilial, Long> {
}
