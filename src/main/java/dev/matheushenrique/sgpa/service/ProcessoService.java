package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.dto.ProcessoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.models.Processo;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ProcessoService {
    Processo save(Processo processo) throws EntityCreationException;
    List<ProcessoResponseDTO> listProcessos();
    Processo updateProcesso(String idProcesso, Processo processo) throws EntityNotFoundException, EntityErrorException;
    void deleteProcesso(String idProcesso) throws EntityNotFoundException;
    /**
     * Retorna um ResponseDTO. Deve ser usado apenas para envio em requisições.
     */
    ProcessoResponseDTO getProcesso(String idProcesso) throws EntityNotFoundException;
    List<Processo> findAll();
    List<ProcessoResponseDTO> listarManual();
}
