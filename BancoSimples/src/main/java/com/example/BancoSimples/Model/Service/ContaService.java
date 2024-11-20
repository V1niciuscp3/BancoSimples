package com.example.BancoSimples.Model.Service;

import com.example.BancoSimples.Model.DTO.TrocarContaDto;
import com.example.BancoSimples.Model.Entity.Conta;
import com.example.BancoSimples.Model.Entity.Usuario;
import com.example.BancoSimples.Model.Repository.ContaRepository;
import com.example.BancoSimples.Model.Repository.UsuarioRepository;
import com.example.BancoSimples.Model.Request.RequestConta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    public ContaService(ContaRepository contaRepository){
        this.contaRepository = contaRepository;
    }


    public String receberTransferencia(Long contaDestinoId, float valorRecebido){
        Conta contaDestino = contaRepository.findById(contaDestinoId)
                .orElseThrow(() -> new RuntimeException("Conta de destino nao encontrada"));

        contaDestino.setSaldo(contaDestino.getSaldo() + valorRecebido);
        contaRepository.save(contaDestino);
        return "valor recebido com sucesso";
    }

    public void deletarConta(Long contaDeleteId) {
        Conta ContaDelete = contaRepository.findById(contaDeleteId)
                .orElseThrow(() -> new RuntimeException("Conta nao existe"));
        if (ContaDelete.getSaldo() > 0.00) {
            throw new IllegalArgumentException(String.format("Não é possível deletar a conta com saldo positivo.\nSaldo atual: %.2f",ContaDelete.getSaldo()));
        }
        contaRepository.deleteById(contaDeleteId);

    }

    public String TrocarTipoConta(TrocarContaDto novoTrocaContaDto) {
        Conta trocaConta = contaRepository.findById(novoTrocaContaDto.getId())
                .orElseThrow(() -> new RuntimeException("Conta nao existe"));

        String novoTipoConta = novoTrocaContaDto.getTipoConta().toLowerCase(Locale.ROOT);
        if (!novoTipoConta.equalsIgnoreCase("corrente") && !novoTipoConta.equalsIgnoreCase("lojista")) {
            return "Tipo de conta inválido. Use 'corrente' ou 'lojista'";
        }
        trocaConta.setTipoConta(novoTipoConta);
        contaRepository.save(trocaConta);
        return "Tipo de conta alterado com sucesso para" + novoTipoConta;
    }

    public List<Conta> buscaPorTipoConta(String tipoConta) {
        if (tipoConta == null || tipoConta.isEmpty()) {
            throw new IllegalArgumentException("tipo de conta nao pode ser vazio");
        }
        if (tipoConta.toLowerCase().equals("lojista") || tipoConta.toLowerCase().equals("corrente")) {
            return contaRepository.findAllByTipoConta(tipoConta);
        }
        return List.of(); // retorna uma lista vazia se o tipo de conta não for encontrado
    }

    public ResponseEntity<Conta> registrarConta(RequestConta conta){
        Optional<Usuario> optionalUsuario = repository.findByCPF(conta.cpfUsuario());
        if (!optionalUsuario.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        Conta novaConta = new Conta();
        novaConta.setTipoConta(conta.tipoConta());
        novaConta.setSaldo(conta.saldo());
        novaConta.setUsuario(optionalUsuario.get());
        contaRepository.save(novaConta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }

}
