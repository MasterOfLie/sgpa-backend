package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.models.utils.Email;
import dev.matheushenrique.sgpa.dto.ProcessoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Processo;
import dev.matheushenrique.sgpa.models.utils.Protocolo;
import dev.matheushenrique.sgpa.repository.ProcessoRepository;
import dev.matheushenrique.sgpa.repository.ProtocoloRepository;
import dev.matheushenrique.sgpa.service.EmailService;
import dev.matheushenrique.sgpa.service.ProcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessoServiceImpl implements ProcessoService {

    private final ProcessoRepository processoRepository;
    private final ProtocoloRepository protocoloRepository;
    private final EmailService emailService;
    @Override
    @Transactional
    public Processo save(Processo processo) throws EntityCreationException {
        processo.setProtocolo(protocoloRepository.save(new Protocolo()));
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

    public List<Processo> findAll() {
        return processoRepository.findAll();
    }

    @Override
    @Transactional
    public List<ProcessoResponseDTO> listarManual() {
        return processoRepository.findAll().stream().map(
                processo -> (new ProcessoResponseDTO(processo.getId(), processo.getDescription(), processo.getStatus(),
                        processo.getSetor().getId(), processo.getSetor().getName(), processo.getServico().getId(), processo.getServico().getName(), processo.getDepartamento().getId(),
                        processo.getDepartamento().getName(), processo.getProtocolo().getId(), processo.getProtocolo().getAnoVigencia(), processo.getSolicitante().getFistName()
                ))
        ).collect(Collectors.toList());
    }

    private Processo existProcesso(String idProcesso) throws EntityNotFoundException {
        return processoRepository.findById(idProcesso).orElseThrow(() -> new EntityNotFoundException("Processo com o id " + idProcesso + " não encontrado"));
    }
}
