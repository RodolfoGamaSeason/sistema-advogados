package dev.rodolfo.sistema_advogados.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
    private Long idAdvogado;

    @Column(nullable = false)
    private String descricao;

    public Processo(String codProcesso, String areaDireito, String juizResponsavel, String descricao) {
        this.codProcesso = codProcesso;
        this.areaDireito = areaDireito;
        this.juizResponsavel = juizResponsavel;
        this.descricao = descricao;
    }
}
