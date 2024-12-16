package dev.matheushenrique.sgpa.controller;
import dev.matheushenrique.sgpa.dto.ErroResponse;
import dev.matheushenrique.sgpa.dto.SetorDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.mapper.SetorMapper;
import dev.matheushenrique.sgpa.models.Setor;
import dev.matheushenrique.sgpa.service.SetorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/setor")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService setorService;
    private final SetorMapper setorMapper;

    @GetMapping
    public ResponseEntity<List<SetorDTO>> findAll() {
        List<Setor> setors = setorService.findAll();
        List<SetorDTO> setorDTOList = setors.stream().map(setorMapper::toSetorDTO).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(setorDTOList);
    }

    @PostMapping
    public ResponseEntity<?> addSetor(@Valid @RequestBody SetorDTO setorDTO) {
        try {
            Setor setorSalvo = setorService.save(setorMapper.toSetor(setorDTO));
            return new ResponseEntity<>(setorMapper.toSetorDTO(setorSalvo), HttpStatus.CREATED);
        } catch (EntityCreationException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @PutMapping("/{idSetor}")
    public ResponseEntity<?> updateSetor(@PathVariable("idSetor") String idSetor, @Valid @RequestBody SetorDTO setorDTO) {
        try {
            Setor setorAtualizado = setorService.updateSetor(idSetor, setorMapper.toSetor(setorDTO));
            return new ResponseEntity<>(setorMapper.toSetorDTO(setorAtualizado), HttpStatus.OK);
        }catch (EntityNotFoundException | EntityErrorException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @DeleteMapping("/{idSetor}")
    public ResponseEntity<?> deleteSetor(@PathVariable("idSetor") String idSetor) {
        try {
            setorService.deleteSetor(idSetor);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (EntityNotFoundException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @GetMapping("{idSetor}")
    public ResponseEntity<?> getSetor(@PathVariable("idSetor") String idSetor) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(setorMapper.toSetorDTO(setorService.getSetor(idSetor)));
        }catch (EntityNotFoundException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
}
