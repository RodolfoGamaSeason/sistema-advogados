package dev.rodolfo.sistema_advogados.viewModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.rodolfo.sistema_advogados.enums.AdvogadoRoleEnum;
import lombok.Data;

@Data
public class AdvogadoViewModel {
    private String nome;

    @JsonProperty("OAB")
    private String OAB;

    private String senha;

    private AdvogadoRoleEnum role;
}
