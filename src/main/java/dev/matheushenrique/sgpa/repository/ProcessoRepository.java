package dev.matheushenrique.sgpa.repository;

import dev.matheushenrique.sgpa.dto.processo.ProcessoResponseDTO;
import dev.matheushenrique.sgpa.models.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProcessoRepository extends JpaRepository<Processo, String> {
    /**
     * Recupera uma lista de processos com seus detalhes, incluindo informações sobre setor,
     * serviço, departamento, protocolo, solicitante e funcionário, ordenados por ID de protocolo.
     *
     * @return Lista de {@link ProcessoResponseDTO} com os processos e seus detalhes.
     */
    @Query("SELECT new dev.matheushenrique.sgpa.dto.processo.ProcessoResponseDTO(p.id, p.description, p.status, st.id, st.name, s.id, s.name, dpt.id, dpt.name, pt.id, pt.anoVigencia, CONCAT( sl.firstName, ' ' ,  sl.lastName), COALESCE(CONCAT(fc.firstName, ' ', fc.lastName), 'ONLINE')) " +
            "FROM Processo p JOIN p.setor st " +
            "JOIN p.servico s JOIN p.departamento dpt JOIN p.protocolo pt JOIN p.solicitante sl LEFT JOIN p.funcionario fc order by pt.id desc ")
    List<ProcessoResponseDTO> listProcessos();

    /**
     * Recupera um processo específico com seus detalhes, incluindo informações sobre setor,
     * serviço, departamento, protocolo, solicitante e funcionário, baseado no ID do processo.
     *
     * @param idProcesso O ID do processo a ser recuperado.
     * @return Um {@link ProcessoResponseDTO} com os detalhes do processo.
     */
    @Query("SELECT new dev.matheushenrique.sgpa.dto.processo.ProcessoResponseDTO(p.id, p.description, p.status, st.id, st.name, s.id, s.name, dpt.id, dpt.name, pt.id, pt.anoVigencia, CONCAT( sl.firstName , ' ' , sl.lastName), COALESCE(CONCAT(fc.firstName, ' ', fc.lastName), 'ONLINE')) " +
            "FROM Processo p JOIN p.setor st" +
            " JOIN p.servico s JOIN p.departamento dpt JOIN p.protocolo pt JOIN p.solicitante sl LEFT JOIN p.funcionario fc WHERE p.id = :idProcesso")
    ProcessoResponseDTO getProcesso(@Param("idProcesso") String idProcesso);

    /**
     * Recupera uma lista de processos solicitados por um solicitante específico, incluindo detalhes
     * sobre setor, serviço, departamento, protocolo, solicitante e funcionário, ordenados por ID de protocolo.
     *
     * @param idSolicitante O ID do solicitante para o qual os processos são recuperados.
     * @return Lista de {@link ProcessoResponseDTO} com os processos solicitados.
     */
    @Query("SELECT new dev.matheushenrique.sgpa.dto.processo.ProcessoResponseDTO(p.id, p.description, p.status, st.id, st.name, s.id, s.name, dpt.id, dpt.name, pt.id, pt.anoVigencia, CONCAT( sl.firstName, ' ' ,  sl.lastName), COALESCE(CONCAT(fc.firstName, ' ', fc.lastName), 'ONLINE')) " +
            "FROM Processo p JOIN p.setor st " +
            "JOIN p.servico s JOIN p.departamento dpt JOIN p.protocolo pt JOIN p.solicitante sl LEFT JOIN p.funcionario fc WHERE p.solicitante.id = :idSolicitante order by pt.id desc ")
    List<ProcessoResponseDTO> listProcessosSolicitados(@Param("idSolicitante") String idSolicitante);
}
