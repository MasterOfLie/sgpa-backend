package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.dto.ProcessoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Processo;
import dev.matheushenrique.sgpa.repository.ProcessoRepository;
import dev.matheushenrique.sgpa.service.ProcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessoServiceImpl implements ProcessoService {

    private final ProcessoRepository processoRepository;

    @Override
    public Processo save(Processo processo) throws EntityCreationException {
        return processoRepository.save(processo);
    }

    @Override
    public List<ProcessoResponseDTO> listProcessos() {
        return processoRepository.listProcessos();
    }

    @Override
    public Processo updateProcesso(String idProcesso, Processo processo) throws EntityNotFoundException, EntityErrorException {
        Processo updateProcesso = existProcesso(idProcesso);
        updateProcesso.setDescription(processo.getDescription());
        updateProcesso.setStatus(processo.getStatus());
        updateProcesso.setSetor(processo.getSetor());
        updateProcesso.setServico(processo.getServico());
        updateProcesso.setDepartamento(processo.getDepartamento());
        return processoRepository.save(updateProcesso);
    }

    @Override
    public void deleteProcesso(String idProcesso) throws EntityNotFoundException {

    }

    @Override
    public ProcessoResponseDTO getProcesso(String idProcesso) throws EntityNotFoundException {
        ProcessoResponseDTO processoResponseDTO = processoRepository.getProcesso(idProcesso);
        if (processoResponseDTO == null) {
            throw new EntityNotFoundException("Processo com o id " + idProcesso + " não encontrado");
        }
        return processoResponseDTO;
    }

    @Override
    public List<Processo> findAll() {
        return processoRepository.findAll();
    }

    private Processo existProcesso(String idProcesso) throws EntityNotFoundException {
        return processoRepository.findById(idProcesso).orElseThrow(() -> new EntityNotFoundException("Processo com o id " + idProcesso + " não encontrado"));
    }
}
