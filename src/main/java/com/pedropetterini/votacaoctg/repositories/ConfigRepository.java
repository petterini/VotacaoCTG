package com.pedropetterini.votacaoctg.repositories;

import com.pedropetterini.votacaoctg.entities.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {
}
