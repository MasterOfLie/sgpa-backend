package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.dto.ServicoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Servico;
import dev.matheushenrique.sgpa.repository.ServicoRepository;
import dev.matheushenrique.sgpa.service.ServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoServiceImpl implements ServicoService {
    private final ServicoRepository servicoRepository;

    @Override
    public Servico save(Servico servico) throws EntityCreationException {
        if(servicoRepository.existsByName((servico.getName()))){
            throw new EntityCreationException("Já existe uma servico com o nome " + servico.getName());
        };
        return servicoRepository.save(servico);
    }

    @Override
    public List<ServicoResponseDTO> listServico() {
        return servicoRepository.listServico();
    }

    @Override
    public Servico updateServico(String idServico, Servico servico) throws EntityNotFoundException, EntityErrorException {
        Servico updateServico = existServico(idServico);
        existByNameAndId(idServico, servico);
        updateServico.setName(servico.getName());
        updateServico.setDescription(servico.getDescription());
        updateServico.setSetor(servico.getSetor());
        updateServico.setDepartamento(servico.getDepartamento());
        return servicoRepository.save(updateServico);
    }


    @Override
    public void deleteServico(String idServico) throws EntityNotFoundException {
        if (!servicoRepository.existsById(idServico)) {
            throw new EntityNotFoundException("Serviço com o id " + idServico + " não encontrado");
        }
        servicoRepository.deleteById(idServico);
    }

    @Override
    public Servico getServico(String idServico) throws EntityNotFoundException {
        return existServico(idServico);
    }

    @Override
    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    private void existByNameAndId(String idServico, Servico servico) {
        Servico existName = servicoRepository.findByName(servico.getName());
        if(existName != null && !existName.getId().equals(idServico)){
            throw new EntityErrorException("Já existe uma servico com o nome " + servico.getName());
        }
    }
    private Servico existServico(String idServico) {
        return servicoRepository.findById(idServico).orElseThrow(() -> new EntityNotFoundException("Serviço com o id " + idServico + " não encontrado"));
    }
}
