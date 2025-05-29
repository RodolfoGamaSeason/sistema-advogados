package dev.rodolfo.sistema_advogados.viewModel;

import lombok.Data;

@Data
public class ProcessoViewModel {

    private Long idProcesso;

    private String codProcesso;

    private String areaDireito;

    private String juizResponsavel;

    private Long idAdvogado;

    private String descricao;
}
