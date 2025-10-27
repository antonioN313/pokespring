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
public class PokemonPageDTO {
    private Long idLocal;
    private Integer idPokeApi;
    private String name;
    private String types;
    private LocalDateTime cachedAt;
}