package com.pedropetterini.votacaoctg.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "É necessário cadastrar o nome do participante.")
    private String nome;

    @NotBlank(message = "É necessário selecionar se é Peão ou Prenda.")
    private String tipo;

    @NotBlank(message = "É necessário selecionar a categoria.")
    private String categoria;
}
