package br.com.fiap.mottu.sprint3.service;

import br.com.fiap.mottu.sprint3.config.security.UtilsSecurity;
import br.com.fiap.mottu.sprint3.exceptions.IdNaoEncontrado;
import br.com.fiap.mottu.sprint3.repository.FilialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import br.com.fiap.mottu.sprint3.entity.Filial;
import br.com.fiap.mottu.sprint3.dto.filial.FilialDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilialService {

    private final FilialRepository filialRepository;
    private final UtilsSecurity security;

    public Filial save(FilialDto dto) {
        security.checkAdminAccess();

        Filial filial = new Filial(dto);

        return filialRepository.save(filial);
    }

    public List<Filial> getAll() {
        security.checkAdminAccess();
        return filialRepository.findAll();
    }

    public Filial getById(Long id) throws IdNaoEncontrado {
        security.checkAdminAccess();
        return filialRepository.findById(id)
                .orElseThrow(() -> new IdNaoEncontrado("Filial não encontrada com ID: " + id));
    }

    public Filial update(Long id, FilialDto dto) throws IdNaoEncontrado {
        security.checkAdminAccess();
        Filial filial = getById(id);

        filial.setFilialName(dto.filialName());
        filial.setEndereco(dto.endereco());
        filial.setContato(dto.contato());
        filial.setHorarioFuncionamento(dto.horario_funcionamento());

        return filialRepository.save(filial);
    }

    public void delete(Long id) throws IdNaoEncontrado{
        security.checkAdminAccess();
        Filial filial = getById(id);

        filialRepository.delete(filial);
    }
}
