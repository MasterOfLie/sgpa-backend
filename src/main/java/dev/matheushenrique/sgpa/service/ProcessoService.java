package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.dto.processo.ProcessoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.models.Processo;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ProcessoService {
    Processo createProcesso(Processo processo) throws EntityCreationException;
    List<ProcessoResponseDTO> getAllProcessos();
    Processo updateProcesso(String idProcesso, Processo processo) throws EntityNotFoundException, EntityErrorException;
    void deleteProcesso(String idProcesso) throws EntityNotFoundException;
    ProcessoResponseDTO getProcesso(String idProcesso) throws EntityNotFoundException;
    List<ProcessoResponseDTO> getProcessosSolicitados();
    List<ProcessoResponseDTO> getProcessosSolicitados(String idSolicitante) throws EntityNotFoundException;
}
