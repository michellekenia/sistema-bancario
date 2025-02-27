package service;

import model.Conta;
import model.Transacao;
import repository.TransacaoRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransacaoService {

    private final ContaService contaService;
    private final TransacaoRepository transacaoRepository;

    public TransacaoService(ContaService contaService, TransacaoRepository transacaoRepository) {
        this.contaService = contaService;
        this.transacaoRepository = transacaoRepository;
    }

    public void realizarTransacao(String tipo, String numeroContaOrigem, String numeroContaDestino, double valor) {

        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("Tipo de transação não pode ser vazio.");
        }


        tipo = tipo.trim().toUpperCase();

        validarTransacao(tipo, numeroContaOrigem, numeroContaDestino, valor);

        boolean sucesso = false;
        Conta contaOrigem = contaService.buscarContaPorNumero(numeroContaOrigem);
        Conta contaDestino = numeroContaDestino != null ? contaService.buscarContaPorNumero(numeroContaDestino) : null;

        switch (tipo.toUpperCase()) {
            case "SAQUE":
                contaOrigem = contaService.buscarContaPorNumero(numeroContaOrigem);
                sucesso = contaService.sacar(numeroContaOrigem, valor);
                break;
            case "DEPÓSITO":
                contaDestino = contaService.buscarContaPorNumero(numeroContaDestino);
                sucesso = contaService.depositar(numeroContaDestino, valor);
                break;
            case "TRANSFERÊNCIA":
                contaOrigem = contaService.buscarContaPorNumero(numeroContaOrigem);
                contaDestino = contaService.buscarContaPorNumero(numeroContaDestino);
                sucesso = contaService.transferir(numeroContaOrigem, numeroContaDestino, valor);
                break;
            default:
                throw new IllegalArgumentException("Tipo de transação inválida.");
        }

        if (sucesso) {
            Transacao transacao = new Transacao(
                    UUID.randomUUID().toString(),
                    tipo.toUpperCase(),
                    valor,
                    contaOrigem,
                    contaDestino,
                    LocalDateTime.now()
            );
            transacaoRepository.registrarTransacao(transacao);
        } else {
            throw new IllegalArgumentException("Falha ao realizar a transação.");
        }
    }

    private void validarTransacao(String tipo, String numeroContaOrigem, String numeroContaDestino, double valor) {
        if (tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("O tipo da transação deve ser informado.");
        }
        if (numeroContaOrigem == null || numeroContaOrigem.isBlank()) {
            throw new IllegalArgumentException("A conta de origem deve ser informada.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser positivo.");
        }
        if (tipo.equalsIgnoreCase("TRANSFERÊNCIA") && (numeroContaDestino == null || numeroContaDestino.isBlank())) {
            throw new IllegalArgumentException("A conta de destino deve ser informada para transferências.");
        }
    }
}

