package dev.rodolfo.sistema_advogados.controller;

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
    private TokenService tokenService;

    @PostMapping("/registrar")
    public ResponseEntity register(
            @RequestBody AdvogadoViewModel viewModel
    ) {
        if (viewModel.getSenha() == null || viewModel.getSenha().isBlank()) {
            return ResponseEntity.badRequest().body("Senha não pode ser vazia.");
        }

        if (advogadoRepository.findByOAB(viewModel.getOAB()) != null) {
            return ResponseEntity.badRequest().body("OAB já cadastrada.");
        }

        String senhaEncriptada = new BCryptPasswordEncoder().encode(viewModel.getSenha());
        String username = viewModel.getNome() + "#" + viewModel.getOAB();
        String usernameFormatado = Normalizer.normalize(username.trim(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("\\s+", "")
                .toLowerCase();
        Advogado novoAdvogado = new Advogado(viewModel.getNome(), usernameFormatado, viewModel.getOAB(), senhaEncriptada, viewModel.getRole());

        advogadoRepository.save(novoAdvogado);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestBody LoginViewModel viewModel
    ) {
        try {
            UsernamePasswordAuthenticationToken OABSenha =
                    new UsernamePasswordAuthenticationToken(viewModel.getOAB(), viewModel.getSenha());
            Authentication auth = this.authenticationManager.authenticate(OABSenha);

            Advogado advogado = (Advogado) auth.getPrincipal();
            String token = tokenService.gerarToken(advogado);

            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OAB ou senha inválidos");
        }
    }
}
