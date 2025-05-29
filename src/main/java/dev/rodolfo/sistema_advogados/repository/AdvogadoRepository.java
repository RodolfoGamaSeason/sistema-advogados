package dev.rodolfo.sistema_advogados.repository;

import dev.rodolfo.sistema_advogados.entity.Advogado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvogadoRepository extends JpaRepository<Advogado, Long> {
    Advogado findByOAB(String OAB);
}