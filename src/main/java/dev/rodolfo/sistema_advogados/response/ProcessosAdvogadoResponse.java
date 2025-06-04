package dev.rodolfo.sistema_advogados.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessosAdvogadoResponse {

    private String codProcesso;

    private String descricao;

    private String areaDireito;

    private String juizResponsavel;
}