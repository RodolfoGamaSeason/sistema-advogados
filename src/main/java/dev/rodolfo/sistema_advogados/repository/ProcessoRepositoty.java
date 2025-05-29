package dev.rodolfo.sistema_advogados.repository;

import dev.rodolfo.sistema_advogados.entity.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessoRepositoty extends JpaRepository<Processo, Long> {

}
