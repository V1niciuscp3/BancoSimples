package com.example.BancoSimples.Model.Request;

import org.antlr.v4.runtime.misc.NotNull;

public record RequestUsuario(Long id, @NotNull String nome, @NotNull String CPF, @NotNull String email, @NotNull String senha, boolean contaAtiva, String cep, String endereco) {
}
