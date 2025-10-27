package com.antonio.pokespring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "pokemon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pokemon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocal;
    
    @Column(nullable = false, unique = true)
    private Integer idPokeApi;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Integer height;
    
    @Column(nullable = false)
    private Integer weight;
    
    @Column(name = "primeira_ability")
    private String primeiraAbility;
    
    @Column(name = "lista_de_types", length = 500)
    private String listaDeTypes;
    
    @Column(nullable = false)
    private LocalDateTime cachedAt;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean favorite = false;
    
    @Column(length = 1000)
    private String note;
    
    @PrePersist
    @PreUpdate
    public void updateCachedAt() {
        this.cachedAt = LocalDateTime.now();
    }
}