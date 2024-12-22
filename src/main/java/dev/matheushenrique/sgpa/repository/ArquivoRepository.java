package dev.matheushenrique.sgpa.repository;

import dev.matheushenrique.sgpa.dto.ArquivoResponseDTO;
import dev.matheushenrique.sgpa.models.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArquivoRepository extends JpaRepository<Arquivo, String> {

    /**
     * Recupera uma lista de arquivos associados a um processo específico.
     * Cada arquivo é retornado com o seu ID, nome e o nome completo do usuário associado.
     *
     * @param idProcesso O ID do processo para o qual os arquivos são recuperados.
     * @return Lista de {@link ArquivoResponseDTO} contendo os arquivos do processo.
     */
    @Query("SELECT new dev.matheushenrique.sgpa.dto.ArquivoResponseDTO(ar.id, ar.name, CONCAT( ar.usuario.firstName, ' ' ,  ar.usuario.lastName)) FROM Arquivo ar LEFT JOIN ar.processo p WHERE p.id = :idProcesso")
    List<ArquivoResponseDTO> listArquivosResponse(@Param("idProcesso") String idProcesso);
}
