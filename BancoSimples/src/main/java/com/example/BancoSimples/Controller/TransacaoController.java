package com.example.BancoSimples.Controller;

import com.example.BancoSimples.Model.DTO.TransferirValorDto;
import com.example.BancoSimples.Model.Entity.Transacao;
import com.example.BancoSimples.Model.Repository.TransacaoRepository;
import com.example.BancoSimples.Model.Service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private TransacaoService transacaoService;

    @Autowired
    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @Autowired
    TransacaoRepository transacaoRepository;

    @PutMapping("/transferir")
    @Transactional
    public ResponseEntity Transferencia(@RequestBody @Validated TransferirValorDto transferencia){
        try {
            transacaoService.realizarTransferencia(transferencia);
            return ResponseEntity.ok().body("Transferencia realizada com sucesso");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/enviadas/{id}")
    public ResponseEntity<List<String>> getContaById(@PathVariable Long id) {
        List<Transacao> transacoes = transacaoRepository.findByOrigemId(id);

        if (!transacoes.isEmpty()) {
            // Mapeia a lista de transações para uma lista de IDs (String)
            List<String> transacaoIds = transacoes.stream()
                    .map(Transacao::getId)  // Obtém apenas o ID
                    .collect(Collectors.toList());
            return ResponseEntity.ok(transacaoIds);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/recebidas/{id}")
    public ResponseEntity<List<String>> getIdsDasTransferenciasRecebidas(@PathVariable Long id) {
        List<Transacao> transacoes = transacaoRepository.findByDestinoId(id);

        if (!transacoes.isEmpty()) {
            // Mapeia a lista de transações para uma lista de IDs (String)
            List<String> transacaoIds = transacoes.stream()
                    .map(Transacao::getId)  // Obtém apenas o ID
                    .collect(Collectors.toList());
            return ResponseEntity.ok(transacaoIds);
        }

        return ResponseEntity.notFound().build();
    }

}
