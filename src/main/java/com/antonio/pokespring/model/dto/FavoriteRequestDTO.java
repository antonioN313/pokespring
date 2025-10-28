package com.antonio.pokespring.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FavoriteRequestDTO {
    
    @NotNull(message = "O campo 'favorite' não pode ser nulo")
    private Boolean favorite;
    
    @Size(max = 1000, message = "A nota deve ter no máximo 1000 caracteres")
    private String note;
}