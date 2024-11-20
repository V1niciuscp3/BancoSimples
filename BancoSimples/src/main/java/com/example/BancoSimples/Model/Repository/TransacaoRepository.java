package com.example.BancoSimples.Model.Repository;

import com.example.BancoSimples.Model.Entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, String> {

    List<Transacao> findByOrigemId(Long origemId);

    List<Transacao> findByDestinoId(Long destinoId);

}
