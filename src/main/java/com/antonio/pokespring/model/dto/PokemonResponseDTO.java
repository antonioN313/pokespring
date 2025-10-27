package com.antonio.pokespring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonResponseDTO {
    private Long idLocal;
    private Integer idPokeApi;
    private String name;
    private Integer height;
    private Integer weight;
    private String primeiraAbility;
    private String listaDeTypes;
    private LocalDateTime cachedAt;
    private Boolean favorite;
    private String note;
}