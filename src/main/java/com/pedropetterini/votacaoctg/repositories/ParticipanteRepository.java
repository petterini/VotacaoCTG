package com.pedropetterini.votacaoctg.repositories;

import com.pedropetterini.votacaoctg.entities.Participante;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    boolean existsByNome(@NotBlank String nome);
}
