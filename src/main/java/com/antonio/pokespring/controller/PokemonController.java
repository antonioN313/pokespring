package com.antonio.pokespring.controller;

import com.antonio.pokespring.model.dto.FavoriteRequestDTO;
import com.antonio.pokemoncache.model.dto.PokemonPageDTO;
import com.ada.pokemoncache.model.dto.PokemonResponseDTO;
import com.ada.pokemoncache.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemon")
@RequiredArgsConstructor
public class PokemonController {
    
    private final PokemonService service;
    
    @PostMapping("/cache/{nameOrId}")
    public ResponseEntity<PokemonResponseDTO> cacheOrUpdatePokemon(
            @PathVariable String nameOrId) {
        PokemonResponseDTO result = service.cacheOrUpdatePokemon(nameOrId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    
    @GetMapping
    public ResponseEntity<Page<PokemonPageDTO>> listPokemon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PokemonPageDTO> result = service.listPokemon(pageable);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{idLocal}")
    public ResponseEntity<PokemonResponseDTO> getPokemonById(
            @PathVariable Long idLocal) {
        PokemonResponseDTO result = service.getPokemonById(idLocal);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<PokemonPageDTO>> searchByType(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PokemonPageDTO> result = service.searchByType(type, pageable);
        return ResponseEntity.ok(result);
    }
    
    @PatchMapping("/{idLocal}/favorite")
    public ResponseEntity<PokemonResponseDTO> updateFavorite(
            @PathVariable Long idLocal,
            @RequestBody FavoriteRequestDTO request) {
        PokemonResponseDTO result = service.updateFavorite(idLocal, request);
        return ResponseEntity.ok(result);
    }
}