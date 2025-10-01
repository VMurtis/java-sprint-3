package br.com.fiap.mottu.sprint3.service;

import br.com.fiap.mottu.sprint3.config.security.UtilsSecurity;
import br.com.fiap.mottu.sprint3.dto.moto.MotoDto;
import br.com.fiap.mottu.sprint3.entity.Filial;
import br.com.fiap.mottu.sprint3.entity.Moto;
import br.com.fiap.mottu.sprint3.entity.Usuario;
import br.com.fiap.mottu.sprint3.exceptions.FilialLotado;
import br.com.fiap.mottu.sprint3.exceptions.IdNaoEncontrado;
import br.com.fiap.mottu.sprint3.exceptions.PlacaNaoEncontrada;
import br.com.fiap.mottu.sprint3.repository.FilialRepository;
import br.com.fiap.mottu.sprint3.repository.MotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final MotoRepository motoRepository;
    private final FilialRepository filialRepository;
    private final UtilsSecurity securityUtils;


    public Moto save(MotoDto dto) {
        Moto moto = new Moto();
        moto.setPlaca(dto.placa());
        moto.setChassi(dto.chassi());
        moto.setIotInfo(dto.iotInfo());
        moto.setRfidTag(dto.rfidTag());
        moto.setLocalizacao(dto.localizacao());
        moto.setStatusAtual(dto.statusAtual());
        moto.setTipoMoto(dto.tipoMoto());

        if (dto.filialId() != null) {
            Filial filial = filialRepository.findById(dto.filialId())
                    .orElseThrow(() -> new RuntimeException("Filial não encontrada com ID: " + dto.filialId()));
            moto.setFilial(filial);
        }


        return motoRepository.save(moto);
    }
    public List<Moto> getAll() {
        Usuario usuarioLogado = securityUtils.getUsuarioLogado();
        if (securityUtils.isAdmin(usuarioLogado)) {
            return motoRepository.findAll();
        } else {
            return motoRepository.findAllByFilial(usuarioLogado.getFilial());
        }
    }

    public Moto getById(Long id) throws IdNaoEncontrado {
        Usuario usuarioLogado = securityUtils.getUsuarioLogado();
        if (securityUtils.isAdmin(usuarioLogado)) {
            return motoRepository.findById(id)
                    .orElseThrow(() -> new IdNaoEncontrado("Moto não encontrada com ID: " + id));
        } else {
            return motoRepository.findByIdAndFilial(id, usuarioLogado.getFilial())
                    .orElseThrow(() -> new IdNaoEncontrado("Moto não encontrada ou não pertence à sua filial. ID: " + id));
        }
    }

    public Moto getByPlaca(String placa) throws PlacaNaoEncontrada {
        Usuario usuarioLogado = securityUtils.getUsuarioLogado();
        if (securityUtils.isAdmin(usuarioLogado)) {
            return motoRepository.findByPlacaIgnoreCase(placa)
                    .orElseThrow(() -> new PlacaNaoEncontrada("Moto não encontrada com placa: " + placa));
        } else {
            return motoRepository.findByPlacaIgnoreCaseAndFilial(placa, usuarioLogado.getFilial())
                    .orElseThrow(() -> new PlacaNaoEncontrada("Moto não encontrada ou não pertence à sua filial. Placa: " + placa));
        }
    }

    public Moto getByChassi(String chassi) {
        Usuario usuarioLogado = securityUtils.getUsuarioLogado();
        if (securityUtils.isAdmin(usuarioLogado)) {
            return motoRepository.findByChassiIgnoreCase(chassi)
                    .orElseThrow(() -> new NoSuchElementException("Moto não encontrada com chassi: " + chassi));
        } else {
            return motoRepository.findByChassiIgnoreCaseAndFilial(chassi, usuarioLogado.getFilial())
                    .orElseThrow(() -> new NoSuchElementException("Moto não encontrada ou não pertence à sua filial. Chassi: " + chassi));
        }
    }

    public Moto update(Long id, MotoDto dto) throws IdNaoEncontrado {

        Moto moto = getById(id);



        moto.setPlaca(dto.placa());
        moto.setChassi(dto.chassi());
        moto.setTipoMoto(dto.tipoMoto());
        moto.setIotInfo(dto.iotInfo());
        moto.setRfidTag(dto.rfidTag());
        moto.setLocalizacao(dto.localizacao());
        moto.setStatusAtual(dto.statusAtual());
        moto.setTipoMoto(dto.tipoMoto());

        return motoRepository.save(moto);
    }

    public void delete(Long id) throws IdNaoEncontrado {
        Moto motoParaDeletar = getById(id);
        motoRepository.delete(motoParaDeletar);
    }

    public long countTotal() {
        securityUtils.checkAdminAccess();
        return motoRepository.count();
    }

    public List<Map<String, Object>> countByEstado() {
        securityUtils.checkAdminAccess();

        return motoRepository.countMotosByStatus();
    }

    public List<Map<String, Object>> countByModelo() {
        securityUtils.checkAdminAccess();
        return motoRepository.countMotosByModelo();
    }

    public Moto updateStatus(Long id) throws IdNaoEncontrado {
        Moto moto = getById(id);



        return motoRepository.save(moto);
    }



}