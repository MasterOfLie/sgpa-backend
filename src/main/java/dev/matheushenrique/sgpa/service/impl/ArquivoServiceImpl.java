package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.component.UsuarioAuthenticated;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Arquivo;
import dev.matheushenrique.sgpa.models.Processo;
import dev.matheushenrique.sgpa.models.Usuario;
import dev.matheushenrique.sgpa.repository.ArquivoRepository;
import dev.matheushenrique.sgpa.repository.ProcessoRepository;
import dev.matheushenrique.sgpa.service.ArquivoService;
import dev.matheushenrique.sgpa.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArquivoServiceImpl implements ArquivoService {
    private final UsuarioAuthenticated usuarioAuthenticated;
    private final StorageService storageService;
    private final ProcessoRepository processoRepository;
    private final ArquivoRepository arquivoRepository;

    @Override
    @Transactional
    public String createArquivo(MultipartFile file, String processoID) throws EntityCreationException, EntityNotFoundException {
        if (file.isEmpty()) {
            throw new EntityCreationException("Erro ao criar arquivo");
        }
        Usuario usuario = usuarioAuthenticated.getUsuarioAuthenticated();
        Processo processo = processoRepository.findById(processoID).orElseThrow(() -> new EntityNotFoundException("Processo com o id " + processoID + " não encontrado"));
        log.info(usuario.getAuthorities().toString());
        log.info(processo.getSolicitante().getEmail().toString());
        if (!processo.getSolicitante().getId().equals(usuario.getId()) && usuario.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USUARIO"))) {
            throw new EntityCreationException("Voce nao pode editar esse processo");
        }
        try {
            String originalFilename = file.getOriginalFilename();
            String fileName = processo.getProtocolo().getId() + "_" + UUID.randomUUID().toString() + "_" + originalFilename;
            storageService.uploadFile(file, fileName);
            Arquivo arquivo = new Arquivo();
            arquivo.setProcesso(processo);
            arquivo.setUsuario(usuario);
            arquivo.setName(originalFilename);
            arquivo.setNameStorage(fileName);
            arquivoRepository.save(arquivo);
        } catch (IOException e) {
            log.error("Erro ao gravar arquivo " + file.getOriginalFilename());
            throw new EntityCreationException("Erro ao gravar arquivo " + file.getOriginalFilename());
        }
        return "Arquivo criado com sucesso";
    }

    @Override
    public String deleteArquivo(String processoID) throws EntityNotFoundException, EntityErrorException {
        Arquivo arquivo = arquivoRepository.findById(processoID).orElseThrow(() -> new EntityNotFoundException("Arquivo com id: " + processoID + " não encontrado"));
        Usuario usuario = usuarioAuthenticated.getUsuarioAuthenticated();
        if (!arquivo.getUsuario().getId().equals(usuario.getId()) && usuario.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USUARIO"))) {
            throw new EntityErrorException("Voce nao excluir esse arquivo");
        }
        storageService.deleteFile(arquivo.getNameStorage());
        arquivoRepository.delete(arquivo);
        return "Arquivo deletado com sucesso";
    }

    @Override
    public String getUrlArquivo(String processoID) throws EntityNotFoundException, EntityErrorException {
        Arquivo arquivo = arquivoRepository.findById(processoID).orElseThrow(() -> new EntityNotFoundException("Arquivo com id: " + processoID + " não encontrado"));
        Usuario usuario = usuarioAuthenticated.getUsuarioAuthenticated();
        if (!arquivo.getUsuario().getId().equals(usuario.getId()) && usuario.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USUARIO"))) {
            throw new EntityErrorException("Voce não pode acessar esse arquivo");
        }
        return storageService.getUrlFile(arquivo.getNameStorage());
    }
}
