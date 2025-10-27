package com.antonio.pokespring.repository;

import com.antonio.pokespring.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    
    Optional<Pokemon> findByIdPokeApi(Integer idPokeApi);
    
    @Query("SELECT p FROM Pokemon p WHERE LOWER(p.listaDeTypes) LIKE LOWER(CONCAT('%', :typeName, '%'))")
    Page<Pokemon> findByTypeContaining(@Param("typeName") String typeName, Pageable pageable);
}