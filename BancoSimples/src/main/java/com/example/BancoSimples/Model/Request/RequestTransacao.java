package com.example.BancoSimples.Model.Request;

import com.example.BancoSimples.Model.Entity.Conta;

import java.time.LocalDateTime;

public record RequestTransacao(String id, float valor, LocalDateTime data, Conta origemId, Conta destinoId) {
}
