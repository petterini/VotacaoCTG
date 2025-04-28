package com.pedropetterini.votacaoctg.repositories;

import com.pedropetterini.votacaoctg.entities.Usuario;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Usuario findByMesa(@NotNull Long mesa);

    boolean existsByMesa(@NotNull Long mesa);

    void deleteByMesa(Long numMesa);

    Object getByMesa(@NotNull Long mesa);
}
