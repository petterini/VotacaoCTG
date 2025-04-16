package com.pedropetterini.votacaoctg.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Usuario {
    @Id
    @NotNull
    private Long id;

    @NotBlank
    private String cpf;
}
