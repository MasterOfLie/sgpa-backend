package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface ArquivoService {

        String createArquivo(MultipartFile file, String processoID) throws EntityCreationException;
        String deleteArquivo(String processoID) throws EntityNotFoundException, EntityErrorException;
        String getUrlArquivo(String processoID) throws EntityNotFoundException, EntityErrorException;
}
