package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.component.UsuarioAuthenticated;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Setor;
import dev.matheushenrique.sgpa.repository.SetorRepository;
import dev.matheushenrique.sgpa.service.SetorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SetorServiceImpl implements SetorService {

    private final SetorRepository setorRepository;
    private final UsuarioAuthenticated usuarioAuthenticated;

    @Override
    public Setor createSetor(Setor setor) throws EntityCreationException {
        if (setorRepository.existsByName(setor.getName())) {
            throw new EntityCreationException("Já existe uma setor com o nome " + setor.getName());
        }
        setorRepository.save(setor);
        log.info("Setor {} com ID: {} foi criado com sucesso pelo usuário '{}'.", setor.getName(), setor.getId(), usuarioAuthenticated.getUsuarioAuthenticatedInfo());
        return setor;
    }

    @Override
    public List<Setor> getAllSetores() {
        return setorRepository.findAll();
    }

    @Override
    public Setor updateSetor(String idSetor, Setor setor) throws EntityNotFoundException , EntityErrorException{
        Setor optionalSetor = setorRepository.findById(idSetor)
                .orElseThrow(() -> new EntityNotFoundException("Setor com o id " + idSetor + " não encontrado"));
        Setor existName = setorRepository.findByName(setor.getName());
        if (existName != null && !existName.getId().equals(idSetor)) {
            throw new EntityErrorException("Já existe uma setor com o nome " + setor.getName());
        }
        optionalSetor.setDescription(setor.getDescription());
        optionalSetor.setName(setor.getName());
        return setorRepository.save(optionalSetor);
    }

    @Override
    public void deleteSetor(String idSetor) throws EntityNotFoundException {
        if (!setorRepository.existsById(idSetor)) {
            throw new EntityNotFoundException("Setor com o id " + idSetor + " não encontrado");
        }
        setorRepository.deleteById(idSetor);
    }

    @Override
    public Setor getSetor(String idSetor) throws EntityNotFoundException {
        return setorRepository.findById(idSetor)
                .orElseThrow(() -> new EntityNotFoundException("Setor com o id " + idSetor + " não encontrado"));
    }
}
