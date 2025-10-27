package com.antonio.pokespring.model.dto.pokeapi;

import lombok.Data;

@Data
public class AbilityInfo {
    private Ability ability;
    
    @Data
    public static class Ability {
        private String name;
    }
}