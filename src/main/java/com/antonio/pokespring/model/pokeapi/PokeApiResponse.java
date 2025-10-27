package com.antonio.pokespring.model.dto.pokeapi;

import lombok.Data;
import java.util.List;

@Data
public class PokeApiResponse {
    private Integer id;
    private String name;
    private Integer height;
    private Integer weight;
    private List<AbilityInfo> abilities;
    private List<TypeInfo> types;
}