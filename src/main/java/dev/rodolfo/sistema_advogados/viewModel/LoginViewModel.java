package dev.rodolfo.sistema_advogados.viewModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginViewModel {
    @JsonProperty("OAB")
    private String OAB;

    private String senha;
}
