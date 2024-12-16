package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.DepartamentoDTO;
import dev.matheushenrique.sgpa.dto.ErroResponse;
import dev.matheushenrique.sgpa.dto.SetorDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.mapper.DepartamentoMapper;
import dev.matheushenrique.sgpa.mapper.SetorMapper;
import dev.matheushenrique.sgpa.models.Departamento;
import dev.matheushenrique.sgpa.models.Setor;
import dev.matheushenrique.sgpa.service.DepartamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/departamento")
@RequiredArgsConstructor
public class DepartamentoController {
    private final DepartamentoService departamentoService;
    private final DepartamentoMapper departamentoMapper;

    @PostMapping
    public ResponseEntity<?> addDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDTO) {
        try {
            Departamento departamentoSalvo = departamentoService.save(departamentoMapper.toDepartamento(departamentoDTO));
            return new ResponseEntity<>(departamentoMapper.toDepartamentoDTO(departamentoSalvo), HttpStatus.CREATED);
        } catch (EntityCreationException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<List<DepartamentoDTO>> findAll() {
        List<Departamento> departamentos = departamentoService.findAll();
        List<DepartamentoDTO> departamentoDTOList = departamentos.stream().map(departamentoMapper::toDepartamentoDTO).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(departamentoDTOList);
    }

    @PutMapping("/{idDepartamento}")
    public ResponseEntity<?> updateDepartamento(@PathVariable("idDepartamento") String idDepartamento, @Valid @RequestBody DepartamentoDTO departamentoDTO) {
        try {
            Departamento departamentoAtualizado = departamentoService.updateDepartamentor(idDepartamento, departamentoMapper.toDepartamento(departamentoDTO));
            return new ResponseEntity<>(departamentoMapper.toDepartamentoDTO(departamentoAtualizado), HttpStatus.OK);
        }catch (EntityNotFoundException | EntityErrorException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @DeleteMapping("/{idDepartamento}")
    public ResponseEntity<?> deleteDepartamento(@PathVariable("idDepartamento") String idDepartamento) {
        try {
            departamentoService.deleteDepartamento(idDepartamento);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (EntityNotFoundException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @GetMapping("{idDepartamento}")
    public ResponseEntity<?> getDepartamento(@PathVariable("idDepartamento") String idDepartamento) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(departamentoMapper.toDepartamentoDTO(departamentoService.getDepartamento(idDepartamento)));
        }catch (EntityNotFoundException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
}
