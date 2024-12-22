package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.ErroResponse;
import dev.matheushenrique.sgpa.dto.servico.ServicoDTO;
import dev.matheushenrique.sgpa.dto.servico.ServicoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.mapper.ServicoMapper;
import dev.matheushenrique.sgpa.models.Servico;
import dev.matheushenrique.sgpa.service.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/servico")
@RequiredArgsConstructor
public class ServicoController {
    private final ServicoService servicoService;
    private final ServicoMapper servicoMapper;


    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_VISUALIZAR_SERVICO') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ServicoResponseDTO>> findAll() {
        List<ServicoResponseDTO> servicos = servicoService.getAllServicos();
        return ResponseEntity.status(HttpStatus.OK).body(servicos);
    }

    @PostMapping
    @Transactional //TODO REMOVER
    @PreAuthorize("hasAuthority('ROLE_CRIAR_SERVICO') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createServico(@Valid @RequestBody ServicoDTO servicoDTO) {
        try {
            Servico servicoSalvo = servicoService.createServico(servicoMapper.toServico(servicoDTO));
            return new ResponseEntity<>(servicoMapper.toServicoResponseDTO(servicoSalvo), HttpStatus.CREATED);
        } catch (EntityCreationException | EntityNotFoundException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }

    @PutMapping("/{idServico}")
    @Transactional //TODO REMOVER
    @PreAuthorize("hasAuthority('ROLE_EDITAR_SERVICO') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateServico(@PathVariable("idServico") String idServico, @Valid @RequestBody ServicoDTO servicoDTO) {
        try {
            Servico servico = servicoService.updateServico(idServico, servicoMapper.toServico(servicoDTO));
            return new ResponseEntity<>(servicoMapper.toServicoResponseDTO(servico), HttpStatus.OK);
        }catch (EntityNotFoundException | EntityErrorException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @DeleteMapping("/{idServico}")
    @PreAuthorize("hasAuthority('ROLE_EDITAR_SERVICO') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteServico(@PathVariable("idServico") String idServico) {
        try {
            servicoService.deleteServico(idServico);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (EntityNotFoundException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @GetMapping("{idServico}")
    @PreAuthorize("hasAuthority('ROLE_VISUALIZAR_SERVICO') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getServico(@PathVariable("idServico") String idServico) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicoMapper.toServicoResponseDTO(servicoService.getServico(idServico)));
        }catch (EntityNotFoundException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }

}
