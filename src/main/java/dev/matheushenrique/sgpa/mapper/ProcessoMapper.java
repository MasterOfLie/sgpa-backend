package dev.matheushenrique.sgpa.mapper;

import dev.matheushenrique.sgpa.dto.ProcessoDTO;
import dev.matheushenrique.sgpa.dto.ProcessoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Departamento;
import dev.matheushenrique.sgpa.models.Processo;
import dev.matheushenrique.sgpa.models.Servico;
import dev.matheushenrique.sgpa.models.Setor;
import dev.matheushenrique.sgpa.repository.DepartamentoRepository;
import dev.matheushenrique.sgpa.repository.ProcessoRepository;
import dev.matheushenrique.sgpa.repository.ServicoRepository;
import dev.matheushenrique.sgpa.repository.SetorRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Data
@Component
@RequiredArgsConstructor
public class ProcessoMapper {
    private final ProcessoRepository processoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final ServicoRepository servicoRepository;
    private final SetorRepository setorRepository;
    
    public Processo toProcesso(ProcessoDTO processoDTO) throws EntityNotFoundException {
        return Processo.builder()
                .description(processoDTO.getDescription())
                .status(processoDTO.getStatus())
                .setor(setorRepository.findById(processoDTO.getIdSetor())
                        .orElseThrow(() -> new EntityNotFoundException("Setor com o id " + processoDTO.getIdSetor() + " não encontrado")))
                .servico(servicoRepository.findById(processoDTO.getIdServico())
                        .orElseThrow(() -> new EntityNotFoundException("Serviço com o id " + processoDTO.getIdServico() + " não encontrado")))
                .departamento(departamentoRepository.findById(processoDTO.getIdDepartamento())
                        .orElseThrow(() -> new EntityNotFoundException("Departamento com o id " + processoDTO.getIdDepartamento() + " não encontrado")))
                .build();
    }

    /**
     * Utilize o metodo getProcesso da classe ProcessoService e mais rapido e eficiente
     */
    public ProcessoResponseDTO toProcessoResponseDTO(Processo processo) {
        ProcessoResponseDTO processoResponseDTO = new ProcessoResponseDTO();
        processoResponseDTO.setId(processo.getId());
        processoResponseDTO.setDescription(processo.getDescription());
        processoResponseDTO.setStatus(processo.getStatus());
        processoResponseDTO.setIdSetor(processo.getSetor().getId());
        processoResponseDTO.setSetor(processo.getSetor().getName());
        processoResponseDTO.setIdServico(processo.getServico().getId());
        processoResponseDTO.setServico(processo.getServico().getName());
        processoResponseDTO.setIdDepartamento(processo.getDepartamento().getId());
        processoResponseDTO.setDepartamento(processo.getDepartamento().getName());
        return processoResponseDTO;
    }
}
