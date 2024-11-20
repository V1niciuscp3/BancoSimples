package com.example.BancoSimples.Model.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrocarContaDto {

    private Long id;
    private String tipoConta;
}
