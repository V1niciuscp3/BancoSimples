package com.example.BancoSimples.Model.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferirValorDto {

    private Long origemId;
    private Long destinoId;
    private float valor;
}
