package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Departamento;
import dev.matheushenrique.sgpa.repository.DepartamentoRepository;
import dev.matheushenrique.sgpa.service.DepartamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    @Override
    public Departamento save(Departamento departamento) throws EntityCreationException {
        if (departamentoRepository.existsByName(departamento.getName())) {
            throw new EntityCreationException("Já existe uma departamento com o nome " + departamento.getName());
        }
        return departamentoRepository.save(departamento);
    }

    @Override
    public List<Departamento> findAll() {
        return departamentoRepository.findAll();
    }

    @Override
    public Departamento updateDepartamentor(String idDepartamento, Departamento departamento) throws EntityNotFoundException, EntityErrorException {
        Departamento updatedDepartamento = departamentoRepository.findById(idDepartamento)
                .orElseThrow(() -> new EntityNotFoundException("Departamento com o nome " + idDepartamento + " não encontrado"));
        Departamento existName = departamentoRepository.findByName((departamento.getName()));
        if (existName != null && !existName.getId().equals(idDepartamento)) {
            throw new EntityErrorException("Já existe uma departamento com o nome " + departamento.getName());
        }
        updatedDepartamento.setName(departamento.getName());
        updatedDepartamento.setDescription(departamento.getDescription());
        return departamentoRepository.save(updatedDepartamento);
    }

    @Override
    public void deleteDepartamento(String idDepartamento) throws EntityNotFoundException {
        if (!departamentoRepository.existsById(idDepartamento)) {
            throw new EntityNotFoundException("Departamento com o id " + idDepartamento + " não encontrado");
        }
        departamentoRepository.deleteById(idDepartamento);
    }

    @Override
    public Departamento getDepartamento(String idDepartamento) throws EntityNotFoundException {
        return departamentoRepository.findById(idDepartamento).orElseThrow(() -> new EntityNotFoundException("Departamento com o id " + idDepartamento + " não encontrado"));
    }
}
