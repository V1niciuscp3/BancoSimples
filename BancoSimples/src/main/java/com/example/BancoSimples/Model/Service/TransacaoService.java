package com.example.BancoSimples.Model.Service;

import com.example.BancoSimples.Model.DTO.TransferirValorDto;
import com.example.BancoSimples.Model.Entity.Conta;
import com.example.BancoSimples.Model.Entity.Transacao;
import com.example.BancoSimples.Model.Repository.ContaRepository;
import com.example.BancoSimples.Model.Repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransacaoService {

    private TransacaoRepository transacaoRepository;

    private ContaRepository contaRepository;

    @Autowired
    public TransacaoService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }


    public ResponseEntity<Transacao> realizarTransferencia(TransferirValorDto transferirValorDto) {

        Conta contaOrigem = contaRepository.findById(transferirValorDto.getOrigemId())
                .orElseThrow(() -> new RuntimeException("Conta de origem nao encontrada"));
        Conta contaDestino = contaRepository.findById(transferirValorDto.getDestinoId())
                .orElseThrow(() -> new RuntimeException("Conta de destino nao encontrada"));

        if (!"corrente".equals(contaOrigem.getTipoConta())) {
            throw new IllegalArgumentException("Apenas contas do tipo correntistas podem realizar transferencias");
        }
        if (contaOrigem.getSaldo() < transferirValorDto.getValor()) {
            throw new IllegalArgumentException("Saldo insuficiente para a transferência");
        }

        Transacao novaTransacao = new Transacao();
        novaTransacao.setOrigem(contaOrigem);
        novaTransacao.setDestino(contaDestino);
        novaTransacao.setValor(transferirValorDto.getValor());
        novaTransacao.setData(LocalDateTime.now());
        transacaoRepository.save(novaTransacao);

        contaOrigem.setSaldo(contaOrigem.getSaldo() - transferirValorDto.getValor());
        contaDestino.setSaldo(contaDestino.getSaldo() + transferirValorDto.getValor());
        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaTransacao);
    }


}
