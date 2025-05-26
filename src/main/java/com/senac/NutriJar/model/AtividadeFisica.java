package com.senac.NutriJar.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "atividades_fisicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtividadeFisica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtividade;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @ToString.Exclude
    private Usuario usuario;
    
    @Column(nullable = false)
    private String tipo;
    
    @Column(nullable = false)
    private Integer duracaoMinutos;  // Deve ser Integer
    
    private String intensidade;
    
    @Column(nullable = false)
    private LocalDate dia;
    
    @Column(nullable = false)
    private LocalTime hora;  // Este campo deve existir
}