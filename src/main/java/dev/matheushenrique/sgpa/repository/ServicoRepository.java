package dev.matheushenrique.sgpa.repository;

import dev.matheushenrique.sgpa.dto.ServicoResponseDTO;
import dev.matheushenrique.sgpa.models.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, String> {
    boolean existsByName(String name);
    @Query("SELECT new dev.matheushenrique.sgpa.dto.ServicoResponseDTO(" +
            "s.id, s.name, s.description, " +
            "st.name, st.id, " +
            "dpt.name, dpt.id) " +
            "FROM Servico s " +
            "JOIN s.setor st " +
            "JOIN s.departamento dpt")
    List<ServicoResponseDTO> listServico();
    Servico findByName(String name);
}
