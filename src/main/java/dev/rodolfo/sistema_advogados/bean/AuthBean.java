package dev.rodolfo.sistema_advogados.bean;

import dev.rodolfo.sistema_advogados.repository.AdvogadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthBean implements UserDetailsService {
    @Autowired
    private AdvogadoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String OAB) throws UsernameNotFoundException {
        return repository.findByOAB(OAB);
    }
}