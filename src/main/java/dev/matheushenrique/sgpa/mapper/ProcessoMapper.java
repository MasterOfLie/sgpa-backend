package dev.matheushenrique.sgpa.mapper;

import dev.matheushenrique.sgpa.dto.processo.ProcessoDTO;
import dev.matheushenrique.sgpa.dto.processo.ProcessoResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Processo;
import dev.matheushenrique.sgpa.repository.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class ProcessoMapper {
    private final ProcessoRepository processoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final ServicoRepository servicoRepository;
    private final SetorRepository setorRepository;
    private final UsuarioRepository usuarioRepository;
    
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
                .solicitante(usuarioRepository.findById(processoDTO.getIdSolicitante())
                        .orElseThrow(() -> new EntityNotFoundException("Solicitante com o id " + processoDTO.getIdSolicitante() + " não encontrado")))
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
