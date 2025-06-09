package dev.rodolfo.sistema_advogados.controller;

import dev.rodolfo.sistema_advogados.service.AdvogadoService;
import dev.rodolfo.sistema_advogados.service.TokenService;
import dev.rodolfo.sistema_advogados.entity.Advogado;
import dev.rodolfo.sistema_advogados.repository.AdvogadoRepository;
import dev.rodolfo.sistema_advogados.response.LoginResponse;
import dev.rodolfo.sistema_advogados.viewModel.AdvogadoViewModel;
import dev.rodolfo.sistema_advogados.viewModel.LoginViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.Normalizer;

@RestController
@RequestMapping("/auth")
public class AdvogadoController {

    @Autowired
    private AdvogadoRepository advogadoRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdvogadoService advogadoService;

    @PostMapping("/registrar")
    public ResponseEntity register(
            @RequestBody AdvogadoViewModel viewModel
    ) {
        advogadoService.cadastraAdvogado(viewModel);

        return ResponseEntity.ok().body("Advogado cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestBody LoginViewModel viewModel
    ) {
        String token = advogadoService.loginAdvogado(viewModel);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
