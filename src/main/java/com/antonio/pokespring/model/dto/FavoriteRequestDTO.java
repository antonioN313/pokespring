package com.antonio.pokespring.model.dto;

import lombok.Data;

@Data
public class FavoriteRequestDTO {
    private Boolean favorite;
    private String note;
}
