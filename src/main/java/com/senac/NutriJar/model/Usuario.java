package com.senac.NutriJar.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String senha;
    
    // Remova as anotações de precisão para campos double
    private Double peso;
    
    private Double altura;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelUsuario nivel;
    
    private Double imc;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Refeicao> refeicoes;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<AtividadeFisica> atividadesFisicas;
    
    public enum NivelUsuario {
        PADRAO, PREMIUM, ADMINISTRADOR
    }
}
