package dev.matheushenrique.sgpa.mapper;


import dev.matheushenrique.sgpa.dto.servico.ServicoDTO;
import dev.matheushenrique.sgpa.dto.servico.ServicoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Departamento;
import dev.matheushenrique.sgpa.models.Servico;
import dev.matheushenrique.sgpa.models.Setor;
import dev.matheushenrique.sgpa.repository.DepartamentoRepository;
import dev.matheushenrique.sgpa.repository.SetorRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class ServicoMapper {
    private final SetorRepository setorRepository;
    private final DepartamentoRepository departamentoRepository;

    public Servico toServico(ServicoDTO servicoDTO) throws EntityNotFoundException {
        Setor setor = setorRepository.findById(servicoDTO.getIdSetor())
                .orElseThrow(() -> new EntityNotFoundException("Setor com o id " + servicoDTO.getIdSetor() + " não encontrado"));
        Departamento departamento = departamentoRepository.findById(servicoDTO.getIdDepartamento())
                .orElseThrow(() -> new EntityNotFoundException("Departamento com o id " + servicoDTO.getIdDepartamento() + " não encontrado"));
        Servico servico = new Servico();
        servico.setName(servicoDTO.getName());
        servico.setDescription(servicoDTO.getDescription());
        servico.setSetor(setor);
        servico.setDepartamento(departamento);
        return servico;
    }
    public ServicoResponseDTO toServicoResponseDTO(Servico servico) {
        ServicoResponseDTO servicoResponseDTO = new ServicoResponseDTO();
        servicoResponseDTO.setId(servico.getId());
        servicoResponseDTO.setName(servico.getName());
        servicoResponseDTO.setDescription(servico.getDescription());
        servicoResponseDTO.setSetor(servico.getSetor().getName());
        servicoResponseDTO.setIdSetor(servico.getSetor().getId());
        servicoResponseDTO.setDepartamento(servico.getDepartamento().getName());
        servicoResponseDTO.setIdDepartamento(servico.getDepartamento().getId());
        return servicoResponseDTO;
    }
}
