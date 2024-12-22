package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Departamento;

import java.util.List;

public interface DepartamentoService {

    Departamento createDepartamento(Departamento departamento) throws EntityCreationException;

    List<Departamento> getAllDepartamento();

    Departamento updateDepartamentor(String idDepartamento, Departamento departamento) throws EntityNotFoundException, EntityErrorException;

    void deleteDepartamento(String idDepartamento) throws EntityNotFoundException;

    Departamento getDepartamento(String idDepartamento) throws EntityNotFoundException;
}
