package com.antonio.pokespring.model.dto.pokeapi;

import lombok.Data;

@Data
public class TypeInfo {
    private Type type;
    
    @Data
    public static class Type {
        private String name;
    }
}