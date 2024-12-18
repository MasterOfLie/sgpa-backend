package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.ErroResponse;
import dev.matheushenrique.sgpa.dto.ProcessoDTO;
import dev.matheushenrique.sgpa.dto.ProcessoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.mapper.ProcessoMapper;
import dev.matheushenrique.sgpa.models.Processo;
import dev.matheushenrique.sgpa.repository.ProcessoRepository;
import dev.matheushenrique.sgpa.repository.ServicoRepository;
import dev.matheushenrique.sgpa.repository.SetorRepository;
import dev.matheushenrique.sgpa.service.ProcessoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/processo")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProcessoController {
    private final ProcessoService processoService;
    private final ProcessoMapper processoMapper;
    private final ProcessoRepository processoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> addProcesso(@Valid @RequestBody ProcessoDTO processoDTO) {
        try {
            Processo processo = processoService.save(processoMapper.toProcesso(processoDTO));
            return new ResponseEntity<>(processoService.getProcesso(processo.getId()), HttpStatus.CREATED);
        } catch (EntityCreationException | EntityNotFoundException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> findAll() {
        List<ProcessoResponseDTO> processoResponseDTOList = processoService.listProcessos();
        return ResponseEntity.status(HttpStatus.OK).body(processoResponseDTOList);
    }

    @PutMapping("/{idProcesso}")
    @Transactional
    public ResponseEntity<?> updateProcesso(@PathVariable("idProcesso") String idProcesso, @Valid @RequestBody ProcessoDTO processoDTO) {
        try {
            Processo processo = processoService.updateProcesso(idProcesso, processoMapper.toProcesso(processoDTO));
            return new ResponseEntity<>(processoService.getProcesso(processo.getId()), HttpStatus.OK);
        }catch (EntityNotFoundException | EntityErrorException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    //TODO E POSSIVEL DELETAR PROCESSO?

    @GetMapping("/{idProcesso}")
    @PreAuthorize("hasAuthority('ROLE_MESTRE')")
    public ResponseEntity<?> getProcesso(@PathVariable("idProcesso") String idProcesso) {
        try {
            ProcessoResponseDTO processo = processoService.getProcesso(idProcesso);
            return new ResponseEntity<>(processo, HttpStatus.OK);
        }catch (EntityNotFoundException | EntityErrorException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
}
