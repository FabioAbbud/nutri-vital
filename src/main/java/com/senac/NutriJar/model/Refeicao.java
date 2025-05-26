package com.senac.NutriJar.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "refeicoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refeicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRefeicao;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @ToString.Exclude
    private Usuario usuario;
    
    @Column(nullable = false)
    private String alimento;
    
    private String quantidade;
    
    @Column(nullable = false)
    private Integer kcal;
    
    @Column(nullable = false)
    private LocalDate data;
    
    @Column(nullable = false)
    private LocalTime hora;
}