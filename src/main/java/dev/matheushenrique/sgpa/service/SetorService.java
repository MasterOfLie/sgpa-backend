package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.models.Setor;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface SetorService {

    Setor save(Setor setor) throws EntityCreationException;
    List<Setor> findAll();
    Setor updateSetor(String idSetor, Setor setor) throws EntityNotFoundException, EntityErrorException;
    void deleteSetor(String idSetor) throws EntityNotFoundException;
    Setor getSetor(String idSetor) throws EntityNotFoundException;

}
