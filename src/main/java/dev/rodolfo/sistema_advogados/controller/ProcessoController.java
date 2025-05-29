package dev.rodolfo.sistema_advogados.controller;

import dev.rodolfo.sistema_advogados.entity.Processo;
import dev.rodolfo.sistema_advogados.repository.ProcessoRepositoty;
import dev.rodolfo.sistema_advogados.viewModel.ProcessoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    @Autowired
    private ProcessoRepositoty processoRepositoty;

    @PostMapping("adicionar")
    public ResponseEntity adicionarProcesso(
            @RequestBody ProcessoViewModel viewModel
    ) {
        Processo processo = new Processo(viewModel.getCodProcesso(), viewModel.getAreaDireito(), viewModel.getJuizResponsavel(), viewModel.getDescricao());

        processoRepositoty.save(processo);

        return ResponseEntity.ok().build();
    }
}
