package dev.rodolfo.sistema_advogados.service;

import dev.rodolfo.sistema_advogados.entity.Advogado;
import dev.rodolfo.sistema_advogados.repository.AdvogadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private AdvogadoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String OAB) throws UsernameNotFoundException {
        Advogado advogado = repository.findByOAB(OAB);

        if (advogado == null) {
            throw new UsernameNotFoundException("OAB não encontrada: " + OAB);
        }
        return advogado;
    }
}