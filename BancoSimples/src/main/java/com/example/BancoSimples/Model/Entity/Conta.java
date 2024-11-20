package com.example.BancoSimples.Model.Entity;


import com.example.BancoSimples.Model.Request.RequestConta;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "Conta")
@Entity(name = "Conta")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor

public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "tipoconta")
    private String tipoConta;
    @Column(name = "saldo")
    private float saldo;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Conta(RequestConta requestConta) {
        this.id = requestConta.id();
        this.tipoConta = requestConta.tipoConta();
        this.saldo = requestConta.saldo();
        this.usuario = usuario;
    }

}

