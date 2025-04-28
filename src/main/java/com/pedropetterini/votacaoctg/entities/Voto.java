package com.pedropetterini.votacaoctg.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Participante participante;

    public Voto(Usuario u1, Participante p1) {
        this.usuario = u1;
        this.participante = p1;
    }

    public Voto() {

    }
}
