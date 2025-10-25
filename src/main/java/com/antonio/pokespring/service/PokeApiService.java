package com.antonio.pokespring.service;

import com.antonio.pokespring.exception.PokeApiException;
import com.antonio.pokespring.exception.PokemonNotFoundException;
import com.antonio.pokespring.model.dto.pokeapi.PokeApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PokeApiService {
    
    private final WebClient webClient;
    
    public PokeApiResponse fetchPokemon(String nameOrId) {
        log.info("Buscando Pokemon na PokeAPI: {}", nameOrId);
        
        try {
            return webClient
                    .get()
                    .uri("/pokemon/{nameOrId}", nameOrId.toLowerCase())
                    .retrieve()
                    .onStatus(
                        status -> status.value() == 404,
                        response -> Mono.error(new PokemonNotFoundException(
                            "Pokemon não encontrado na PokeAPI: " + nameOrId))
                    )
                    .bodyToMono(PokeApiResponse.class)
                    .block();
        } catch (PokemonNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao buscar Pokemon na PokeAPI", e);
            throw new PokeApiException("Erro ao comunicar com PokeAPI", e);
        }
    }
}