package com.pedropetterini.votacaoctg.repositories;

import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.entities.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VotoRepository extends JpaRepository<Voto, UUID> {

    List<Voto> findByUsuario(Usuario usuario);

    boolean getVotosById(UUID id);

}
