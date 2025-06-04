package dev.rodolfo.sistema_advogados.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProcessosAdminResponse {

    private String codProcesso;

    private String descricao;

    private String areaDireito;

    private String juizResponsavel;
}
