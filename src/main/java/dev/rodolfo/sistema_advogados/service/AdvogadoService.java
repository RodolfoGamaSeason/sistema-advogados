package dev.rodolfo.sistema_advogados.service;

import dev.rodolfo.sistema_advogados.entity.Advogado;
import dev.rodolfo.sistema_advogados.exception.OABJaCadastradaException;
import dev.rodolfo.sistema_advogados.exception.SenhaInvalidaException;
import dev.rodolfo.sistema_advogados.repository.AdvogadoRepository;
import dev.rodolfo.sistema_advogados.viewModel.AdvogadoViewModel;
import dev.rodolfo.sistema_advogados.viewModel.LoginViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
public class AdvogadoService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdvogadoRepository advogadoRepository;

    public void cadastraAdvogado(AdvogadoViewModel viewModel) {
        if (viewModel.getSenha() == null || viewModel.getSenha().isBlank()) {
            throw new SenhaInvalidaException("Senha não pode ser vazia");
        }

        if (advogadoRepository.findByOAB(viewModel.getOAB()) != null) {
            throw new OABJaCadastradaException("OAB já cadastrada");
        }

        String senhaEncriptada = new BCryptPasswordEncoder().encode(viewModel.getSenha());
        String username = viewModel.getNome() + "#" + viewModel.getOAB();
        String usernameFormatado = Normalizer.normalize(username.trim(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("\\s+", "")
                .toLowerCase();
        Advogado novoAdvogado = new Advogado(viewModel.getNome(), usernameFormatado, viewModel.getOAB(), senhaEncriptada, viewModel.getRole());

        advogadoRepository.save(novoAdvogado);
    }

    public String loginAdvogado(LoginViewModel viewModel) throws BadCredentialsException {
        try {
            UsernamePasswordAuthenticationToken OABSenha =
                    new UsernamePasswordAuthenticationToken(viewModel.getOAB(), viewModel.getSenha());
            Authentication auth = this.authenticationManager.authenticate(OABSenha);

            Advogado advogado = (Advogado) auth.getPrincipal();
            String token = tokenService.gerarToken(advogado);

            return token;
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciais inválidas. Verifique OAB e senha.");
        }

    }
}
