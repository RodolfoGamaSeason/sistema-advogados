package dev.rodolfo.sistema_advogados.controller;

import dev.rodolfo.sistema_advogados.service.ProcessoService;
import dev.rodolfo.sistema_advogados.entity.Processo;
import dev.rodolfo.sistema_advogados.repository.ProcessoRepositoty;
import dev.rodolfo.sistema_advogados.response.ProcessosAdvogadoResponse;
import dev.rodolfo.sistema_advogados.viewModel.ProcessoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    @Autowired
    private ProcessoRepositoty processoRepositoty;

    @Autowired
    private ProcessoService processoService;

    @PostMapping("adicionar")
    public ResponseEntity adicionarProcesso(
            @RequestBody ProcessoViewModel viewModel
    ) {
        processoService.adicionarProcesso(viewModel);

        return ResponseEntity.ok().build();
    }

    @GetMapping("listar-processos-advogado")
    public ResponseEntity<List<ProcessosAdvogadoResponse>> listarProcessos() {
        List<Processo> processos = processoService.listarProcessosPorIdAdvogado();

        List<ProcessosAdvogadoResponse> responses = processos.stream()
                .map(p -> new ProcessosAdvogadoResponse(
                        p.getCodProcesso(),
                        p.getDescricao(),
                        p.getAreaDireito(),
                        p.getJuizResponsavel()
                ))
                .toList();

        return ResponseEntity.ok().body(responses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/listar-todos-processos")
    public ResponseEntity<List<ProcessosAdvogadoResponse>> listarProcessosAdmin() {
        List<Processo> processos = processoService.listarTodosOsProcessos();

        List<ProcessosAdvogadoResponse> responses = processos.stream()
                .map(p -> new ProcessosAdvogadoResponse(
                        p.getCodProcesso(),
                        p.getDescricao(),
                        p.getAreaDireito(),
                        p.getJuizResponsavel()
                ))
                .toList();

        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("deletar-processo")
    public ResponseEntity deletarProcesso(
            @RequestParam Long id
    ) throws AccessDeniedException {
        processoService.deletarProcesso(id);

        return ResponseEntity.ok().build();
    }
}