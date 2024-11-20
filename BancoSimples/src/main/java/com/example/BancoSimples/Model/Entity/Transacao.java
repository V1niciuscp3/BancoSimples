package com.example.BancoSimples.Model.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "transacao")
@Entity(name = "transacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private float valor;
    private LocalDateTime data;
    @ManyToOne
    @JoinColumn(name = "origem_id")
    private Conta origem;
    @ManyToOne
    @JoinColumn(name = "destino_id")
    private Conta destino;

    @PrePersist
    protected void onCreate(){
        this.data = LocalDateTime.now();
    }

}
