package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.component.UsuarioAuthenticated;
import dev.matheushenrique.sgpa.dto.ArquivoDTO;
import dev.matheushenrique.sgpa.dto.ArquivoResponseDTO;
import dev.matheushenrique.sgpa.dto.processo.ProcessoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Processo;
import dev.matheushenrique.sgpa.models.utils.Protocolo;
import dev.matheushenrique.sgpa.repository.ArquivoRepository;
import dev.matheushenrique.sgpa.repository.ProcessoRepository;
import dev.matheushenrique.sgpa.repository.ProtocoloRepository;
import dev.matheushenrique.sgpa.repository.UsuarioRepository;
import dev.matheushenrique.sgpa.service.EmailService;
import dev.matheushenrique.sgpa.service.ProcessoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessoServiceImpl implements ProcessoService {

    private final ProcessoRepository processoRepository;
    private final ProtocoloRepository protocoloRepository;
    private final EmailService emailService;
    private final UsuarioAuthenticated usuarioAuthenticated;
    private final UsuarioRepository usuarioRepository;
    private final ArquivoRepository arquivoRepository;

    @Override
    @Transactional
    public Processo createProcesso(Processo processo) throws EntityCreationException {
        processo.setProtocolo(protocoloRepository.save(new Protocolo()));
        processo.setFuncionario(usuarioAuthenticated.getUsuarioAuthenticated());
        processoRepository.save(processo);
        log.info("Processo {} com ID: {} foi criado com sucesso pelo usuário '{}'.", processo.getProtocolo().getId() + "/" + processo.getProtocolo().getAnoVigencia(), processo.getId(), usuarioAuthenticated.getUsuarioAuthenticatedInfo());
        return processo;
    }

    @Override
    public List<ProcessoResponseDTO> getAllProcessos() {
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
        List<ArquivoResponseDTO> arquivoResponseDTO = arquivoRepository.listArquivosResponse(idProcesso);
        processoResponseDTO.setArquivos(arquivoResponseDTO);
        return processoResponseDTO;
    }

    @Override
    public List<ProcessoResponseDTO> getProcessosSolicitados() {
        log.info(usuarioAuthenticated.getUsuarioAuthenticated().getId());
        return processoRepository.listProcessosSolicitados(usuarioAuthenticated.getUsuarioAuthenticated().getId());
    }

    @Override
    public List<ProcessoResponseDTO> getProcessosSolicitados(String idSolicitante) throws jakarta.persistence.EntityNotFoundException {
        if (!usuarioRepository.existsById(idSolicitante)) {
            throw new EntityNotFoundException("Solicitante com o id " + idSolicitante + " não encontrado");
        }

        return processoRepository.listProcessosSolicitados(idSolicitante);
    }

    private Processo existProcesso(String idProcesso) throws EntityNotFoundException {
        return processoRepository.findById(idProcesso).orElseThrow(() -> new EntityNotFoundException("Processo com o id " + idProcesso + " não encontrado"));
    }
}
