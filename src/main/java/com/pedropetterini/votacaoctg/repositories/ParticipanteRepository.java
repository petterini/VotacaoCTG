package com.pedropetterini.votacaoctg.repositories;

import com.pedropetterini.votacaoctg.entities.Participante;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    boolean existsByNome(@NotBlank String nome);

    Optional<Participante> findByNome(@NotBlank(message = "Nenhum participante encontrado.") String nome);

    Optional<List<Participante>> findByTipo(@NotBlank(message = "Nenhum participante encontrado.") String tipo);

    Optional<List<Participante>> findByCategoria(@NotBlank(message = "Nenhum participante encontrado.") String categoria);

    Optional<List<Participante>> findByCategoriaAndTipo(@NotBlank(message = "Nenhum participante encontrado.") String categoria, String tipo);

    Participante getByNome(@NotBlank(message = "É necessário cadastrar o nome do participante.") String nome);
}
