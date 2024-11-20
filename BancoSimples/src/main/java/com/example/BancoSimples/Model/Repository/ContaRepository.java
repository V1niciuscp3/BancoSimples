package com.example.BancoSimples.Model.Repository;

import com.example.BancoSimples.Model.Entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findAllByTipoConta(String tipoConta);

}
