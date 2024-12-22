package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.ErroResponse;
import dev.matheushenrique.sgpa.dto.processo.ProcessoDTO;
import dev.matheushenrique.sgpa.dto.processo.ProcessoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.mapper.ProcessoMapper;
import dev.matheushenrique.sgpa.models.Processo;
import dev.matheushenrique.sgpa.repository.ProcessoRepository;
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
            Processo processo = processoService.createProcesso(processoMapper.toProcesso(processoDTO));
            return new ResponseEntity<>(processoService.getProcesso(processo.getId()), HttpStatus.CREATED);
        } catch (EntityCreationException | EntityNotFoundException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> findAll() {
        List<ProcessoResponseDTO> processoResponseDTOList = processoService.getAllProcessos();
        return ResponseEntity.status(HttpStatus.OK).body(processoResponseDTOList);
    }

    @GetMapping("/solicitante")
    public ResponseEntity<?> allProcessoSolicitante() {
        List<ProcessoResponseDTO> processoResponseDTOList = processoService.getProcessosSolicitados();
        return ResponseEntity.status(HttpStatus.OK).body(processoResponseDTOList);
    }

    @GetMapping("/solicitante/{idSolicitante}")
    public ResponseEntity<?> allProcessoSolicitante(@PathVariable("idSolicitante") String idSolicitante) {
        try {
            List<ProcessoResponseDTO> processoResponseDTOList = processoService.getProcessosSolicitados(idSolicitante);
            return ResponseEntity.status(HttpStatus.OK).body(processoResponseDTOList);
        }catch (EntityNotFoundException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }

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
