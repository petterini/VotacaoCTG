package com.pedropetterini.votacaoctg.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
public class Config {

    @Id
    private Long id;

    public Config(Long id) {
        this.id = id;
    }

    public Config() {}
}
