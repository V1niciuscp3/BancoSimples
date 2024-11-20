package com.example.BancoSimples.Model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaRequestDto {

    private String tipoConta;
    private float saldo;
    private Long usuarioId;

}
