package com.pedropetterini.votacaoctg.repositories;

import com.pedropetterini.votacaoctg.entities.RegistrarVotos;
import com.pedropetterini.votacaoctg.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RegistroRepository extends JpaRepository<RegistrarVotos, UUID> {
    int countByUsuario(Usuario usuario);

    Optional<Object> findByUsuario(Usuario usuario);

    RegistrarVotos getByUsuario(Usuario usuario);
}
