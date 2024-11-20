package com.example.BancoSimples.Model.Entity;

import com.example.BancoSimples.Model.Request.RequestUsuario;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "usuario")
@Entity(name = "usuario")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "CPF")
    private String CPF;
    @Column(name = "email")
    private String email;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "cep")
    private String cep;
    private String senha;
    @Column(name = "contaativa")
    private boolean contaAtiva;


    public Usuario(RequestUsuario requestUsuario){
        this.nome = requestUsuario.nome();
        this.CPF = requestUsuario.CPF();
        this.email = requestUsuario.email();
        this.endereco = requestUsuario.endereco();
        this.cep = requestUsuario.cep();
        this.senha = requestUsuario.senha();
        this.contaAtiva = requestUsuario.contaAtiva();
    }
}
