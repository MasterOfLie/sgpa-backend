package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.ErroResponse;
import dev.matheushenrique.sgpa.dto.ServicoDTO;
import dev.matheushenrique.sgpa.dto.ServicoResponseDTO;
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

    @GetMapping("/test")
    @Transactional(readOnly = true) // APENAS TEST, 200ms de resposta
    public ResponseEntity<List<ServicoResponseDTO>> listarServicos() {
        List<Servico> servicos = servicoService.findAll();
        List<ServicoResponseDTO> servicoList = servicos.stream().map(servicoMapper::toServicoResponseDTO).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(servicoList);
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> findAll() {
        List<ServicoResponseDTO> servicos = servicoService.listServico();
        return ResponseEntity.status(HttpStatus.OK).body(servicos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> addServico(@Valid @RequestBody ServicoDTO servicoDTO) {
        try {
            Servico servicoSalvo = servicoService.save(servicoMapper.toServico(servicoDTO));
            return new ResponseEntity<>(servicoMapper.toServicoResponseDTO(servicoSalvo), HttpStatus.CREATED);
        } catch (EntityCreationException | EntityNotFoundException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }

    @PutMapping("/{idServico}")
    @Transactional // TODO 200ms de resposta, criar uma QUERY para o metodo de verificação (existByNameAndId) pode melhorar
    // E APENAS UMA ENTIDADE POR REQUISIÇAO, TALVEZ NAO SEJA UM GRANDE PROBLEMA
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
    @Transactional(readOnly = true)
    public ResponseEntity<?> getServico(@PathVariable("idServico") String idServico) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicoMapper.toServicoResponseDTO(servicoService.getServico(idServico)));
        }catch (EntityNotFoundException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }

}
