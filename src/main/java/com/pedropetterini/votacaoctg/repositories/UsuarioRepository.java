package com.pedropetterini.votacaoctg.repositories;

import com.pedropetterini.votacaoctg.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
