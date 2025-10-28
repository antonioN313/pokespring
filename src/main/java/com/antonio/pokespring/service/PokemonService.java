package com.antonio.pokespring.service;

import com.antonio.pokespring.exception.PokemonNotFoundException;
import com.antonio.pokespring.model.Pokemon;
import com.antonio.pokespring.model.dto.FavoriteRequestDTO;
import com.antonio.pokespring.model.dto.PokemonPageDTO;
import com.antonio.pokespring.model.dto.PokemonResponseDTO;
import com.antonio.pokespring.model.dto.pokeapi.AbilityInfo;
import com.antonio.pokespring.model.dto.pokeapi.PokeApiResponse;
import com.antonio.pokespring.model.dto.pokeapi.TypeInfo;
import com.antonio.pokespring.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PokemonService {
    
    private final PokemonRepository repository;
    private final PokeApiService pokeApiService;
    
    @Transactional
    @CacheEvict(value = {"pokemons", "pokemonById", "pokemonsByType"}, allEntries = true)
    public PokemonResponseDTO cacheOrUpdatePokemon(String nameOrId) {
        log.info("Processando cache/atualização para: {}", nameOrId);
        
        // Busca na PokeAPI
        PokeApiResponse apiResponse = pokeApiService.fetchPokemon(nameOrId);
        
        // Verifica se já existe no banco
        Pokemon pokemon = repository.findByIdPokeApi(apiResponse.getId())
                .orElse(new Pokemon());
        
        // Atualiza/Define campos
        pokemon.setIdPokeApi(apiResponse.getId());
        pokemon.setName(apiResponse.getName());
        pokemon.setHeight(apiResponse.getHeight());
        pokemon.setWeight(apiResponse.getWeight());
        
        // Primeira habilidade
        if (apiResponse.getAbilities() != null && !apiResponse.getAbilities().isEmpty()) {
            pokemon.setPrimeiraAbility(apiResponse.getAbilities().get(0).getAbility().getName());
        }
        
        // Lista de tipos (CSV)
        if (apiResponse.getTypes() != null) {
            String types = apiResponse.getTypes().stream()
                    .map(t -> t.getType().getName())
                    .collect(Collectors.joining(", "));
            pokemon.setListaDeTypes(types);
        }
        
        pokemon.setCachedAt(LocalDateTime.now());
        
        // Salva
        Pokemon saved = repository.save(pokemon);
        log.info("Pokemon salvo/atualizado: idLocal={}, name={}", saved.getIdLocal(), saved.getName());
        
        return mapToResponseDTO(saved);
    }
    
    @Cacheable(value = "pokemons", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<PokemonPageDTO> listPokemon(Pageable pageable) {
        log.info("Buscando lista de Pokémons (cache miss)");
        return repository.findAll(pageable)
                .map(this::mapToPageDTO);
    }
    
    @Cacheable(value = "pokemonById", key = "#idLocal")
    public PokemonResponseDTO getPokemonById(Long idLocal) {
        log.info("Buscando Pokemon por ID: {} (cache miss)", idLocal);
        Pokemon pokemon = repository.findById(idLocal)
                .orElseThrow(() -> new PokemonNotFoundException(
                    "Pokemon não encontrado com idLocal: " + idLocal));
        return mapToResponseDTO(pokemon);
    }
    
    @Cacheable(value = "pokemonsByType", key = "#typeName + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<PokemonPageDTO> searchByType(String typeName, Pageable pageable) {
        log.info("Buscando Pokémons por tipo: {} (cache miss)", typeName);
        return repository.findByTypeContaining(typeName, pageable)
                .map(this::mapToPageDTO);
    }
    
    @Transactional
    @CacheEvict(value = {"pokemons", "pokemonById", "pokemonsByType"}, allEntries = true)
    public PokemonResponseDTO updateFavorite(Long idLocal, FavoriteRequestDTO request) {
        log.info("Atualizando favorito para Pokemon: {}", idLocal);
        
        if (request.getFavorite() != null) {
            pokemon.setFavorite(request.getFavorite());
        }
        
        if (request.getNote() != null) {
            pokemon.setNote(request.getNote());
        }
        
        Pokemon updated = repository.save(pokemon);
        return mapToResponseDTO(updated);
    }
    
    // Mappers
    private PokemonResponseDTO mapToResponseDTO(Pokemon pokemon) {
        return PokemonResponseDTO.builder()
                .idLocal(pokemon.getIdLocal())
                .idPokeApi(pokemon.getIdPokeApi())
                .name(pokemon.getName())
                .height(pokemon.getHeight())
                .weight(pokemon.getWeight())
                .primeiraAbility(pokemon.getPrimeiraAbility())
                .listaDeTypes(pokemon.getListaDeTypes())
                .cachedAt(pokemon.getCachedAt())
                .favorite(pokemon.getFavorite())
                .note(pokemon.getNote())
                .build();
    }
    
    private PokemonPageDTO mapToPageDTO(Pokemon pokemon) {
        return PokemonPageDTO.builder()
                .idLocal(pokemon.getIdLocal())
                .idPokeApi(pokemon.getIdPokeApi())
                .name(pokemon.getName())
                .types(pokemon.getListaDeTypes())
                .cachedAt(pokemon.getCachedAt())
                .build();
    }

    @CacheEvict(value = {"pokemons", "pokemonById", "pokemonsByType"}, allEntries = true)
    public void clearAllCaches() {
        log.info("Todos os caches foram limpos");
    }
}