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
    
    @NotNull(message = "O ID da PokeAPI não pode ser nulo")
    @Positive(message = "O ID da PokeAPI deve ser positivo")
    @Column(nullable = false, unique = true)
    private Integer idPokeApi;
    
    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres")
    @Column(nullable = false)
    private String name;
    
    @NotNull(message = "A altura não pode ser nula")
    @Positive(message = "A altura deve ser positiva")
    @Column(nullable = false)
    private Integer height;
    
    @NotNull(message = "O peso não pode ser nulo")
    @Positive(message = "O peso deve ser positivo")
    @Column(nullable = false)
    private Integer weight;
    
    @Size(max = 100, message = "A habilidade deve ter no máximo 100 caracteres")
    @Column(name = "primeira_ability")
    private String primeiraAbility;
    
    @Size(max = 500, message = "A lista de tipos deve ter no máximo 500 caracteres")
    @Column(name = "lista_de_types", length = 500)
    private String listaDeTypes;
    
    @NotNull(message = "A data de cache não pode ser nula")
    @Column(nullable = false)
    private LocalDateTime cachedAt;
    
    @NotNull(message = "O campo 'favorite' não pode ser nulo")
    @Column(nullable = false)
    @Builder.Default
    private Boolean favorite = false;
    
    @Size(max = 1000, message = "A nota deve ter no máximo 1000 caracteres")
    @Column(length = 1000)
    private String note;
    
    @PrePersist
    @PreUpdate
    public void updateCachedAt() {
        this.cachedAt = LocalDateTime.now();
    }
}