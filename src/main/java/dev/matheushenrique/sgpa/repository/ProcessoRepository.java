package dev.matheushenrique.sgpa.repository;

import dev.matheushenrique.sgpa.dto.ProcessoResponseDTO;
import dev.matheushenrique.sgpa.models.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProcessoRepository extends JpaRepository<Processo, String> {
    @Query("SELECT new dev.matheushenrique.sgpa.dto.ProcessoResponseDTO(p.id, p.description, p.status, st.id, st.name, s.id, s.name, dpt.id, dpt.name) FROM Processo p JOIN p.setor st JOIN p.servico s JOIN p.departamento dpt")
    List<ProcessoResponseDTO> listProcessos();

    @Query("SELECT new dev.matheushenrique.sgpa.dto.ProcessoResponseDTO(p.id, p.description, p.status, st.id, st.name, s.id, s.name, dpt.id, dpt.name) FROM Processo p JOIN p.setor st JOIN p.servico s JOIN p.departamento dpt WHERE p.id = :idProcesso")
    ProcessoResponseDTO getProcesso(@Param("idProcesso") String idProcesso);
}
