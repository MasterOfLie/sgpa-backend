package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.ArquivoDTO;
import dev.matheushenrique.sgpa.dto.ErroResponse;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.service.ArquivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/arquivo")
@RequiredArgsConstructor
public class ArquivoController {

    private final ArquivoService arquivoService;

    @PostMapping
    public ResponseEntity<?> uploadTestFile(@RequestParam("file") MultipartFile file, @RequestParam("idProcesso") String idProcesso) throws EntityNotFoundException, EntityCreationException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(arquivoService.createArquivo(file, idProcesso));
        }catch (EntityCreationException | EntityNotFoundException e){
            ErroResponse error = ErroResponse.unauthorizedResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @DeleteMapping("{idArquivo}")
    public ResponseEntity<?> deleteFile(@PathVariable("idArquivo") String idArquivo) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(arquivoService.deleteArquivo(idArquivo));
        }catch (EntityErrorException | EntityNotFoundException e){
            ErroResponse error = ErroResponse.unauthorizedResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @GetMapping("{idArquivo}")
    public ResponseEntity<?> getUrlArquivo(@PathVariable("idArquivo") String idArquivo) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(arquivoService.getUrlArquivo(idArquivo));
        }catch (EntityErrorException | EntityNotFoundException e){
            ErroResponse error = ErroResponse.unauthorizedResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
}
