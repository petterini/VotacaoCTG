package com.pedropetterini.votacaoctg.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Participante participante;
}
