package dev.rodolfo.sistema_advogados.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "processos")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProcesso;

    @Column(nullable = false)
    private String codProcesso;

    @Column(nullable = false)
    private String areaDireito;

    private String juizResponsavel;

    @Column(nullable = false)
    private String idAdvogado;

    @Column(nullable = false)
    private String descricao;
}
