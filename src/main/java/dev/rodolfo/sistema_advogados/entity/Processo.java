package dev.rodolfo.sistema_advogados.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
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
    private String descricao;

    @ManyToMany(mappedBy = "processos")
    private Set<Advogado> advogados = new HashSet<>();

    public Processo(String codProcesso, String areaDireito, String juizResponsavel, String descricao) {
        this.codProcesso = codProcesso;
        this.areaDireito = areaDireito;
        this.juizResponsavel = juizResponsavel;
        this.descricao = descricao;
    }
}
