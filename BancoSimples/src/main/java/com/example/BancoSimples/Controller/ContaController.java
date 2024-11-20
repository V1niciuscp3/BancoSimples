package com.example.BancoSimples.Controller;


import com.example.BancoSimples.Model.DTO.TrocarContaDto;
import com.example.BancoSimples.Model.Entity.Conta;
import com.example.BancoSimples.Model.Repository.ContaRepository;
import com.example.BancoSimples.Model.Repository.UsuarioRepository;
import com.example.BancoSimples.Model.Request.RequestConta;
import com.example.BancoSimples.Model.Service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private ContaService contaService;


    @Autowired
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity getAllConta() {
        var allConta = contaRepository.findAll();
        return ResponseEntity.ok(allConta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> getContaById(@PathVariable Long id) {
        Optional<Conta> contaOptional = contaRepository.findById(id);
        if (contaOptional.isPresent()) {
            return ResponseEntity.ok(contaOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/tiposcontas/{tipoConta}")
    public List<Conta> getContaByTipoConta(@PathVariable String tipoConta){
    return contaService.buscaPorTipoConta(tipoConta);
}

    @PostMapping
    public ResponseEntity<Conta> registerConta(@RequestBody RequestConta conta){
        try {
            return contaService.registrarConta(conta);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/receberTransfer")
    @Transactional
    public ResponseEntity receberTransferencia(@RequestBody @Validated @RequestParam Long destinoId, float valorRecebido){
        try {
            contaService.receberTransferencia(destinoId, valorRecebido);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/trocarConta")
    @Transactional
    public ResponseEntity trocarConta(@RequestBody @Validated TrocarContaDto trocarContaDto ) {
        try {
            contaService.TrocarTipoConta(trocarContaDto);
            return ResponseEntity.ok(String.format("Conta trocada para %s", trocarContaDto.getTipoConta()));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarConta(@PathVariable Long id){
        try {
            contaService.deletarConta(id);
            return ResponseEntity.ok("Conta deletada com sucesso");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
