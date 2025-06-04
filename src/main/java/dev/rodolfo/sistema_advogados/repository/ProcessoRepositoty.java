package dev.rodolfo.sistema_advogados.repository;

import dev.rodolfo.sistema_advogados.entity.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessoRepositoty extends JpaRepository<Processo, Long> {
    List<Processo> findByAdvogadosId(Long id);
}
