package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.dto.ServicoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.models.Servico;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ServicoService {

    Servico save(Servico servico) throws EntityCreationException;
    List<ServicoResponseDTO> listServico();
    Servico updateServico(String idServico, Servico servico) throws EntityNotFoundException, EntityErrorException;
    void deleteServico(String idServico) throws EntityNotFoundException;
    Servico getServico(String idServico) throws EntityNotFoundException;
    List<Servico> findAll();
}
