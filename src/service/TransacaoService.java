package service;

import enuns.TipoTransacao;
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


    public void realizarSaque(String numeroContaOrigem, double valor) {
        validarSaque(numeroContaOrigem, valor);
        Conta contaOrigem = contaService.buscarContaPorNumero(numeroContaOrigem);

        if (contaService.sacar(numeroContaOrigem, valor)) {
            registrarTransacao(TipoTransacao.SAQUE, valor, contaOrigem, null);
        } else {
            throw new IllegalArgumentException("Falha ao realizar o saque.");
        }
    }

    public void realizarDeposito(String numeroContaDestino, double valor) {
        validarDeposito(numeroContaDestino, valor);
        Conta contaDestino = contaService.buscarContaPorNumero(numeroContaDestino);

        if (contaService.depositar(numeroContaDestino, valor)) {
            registrarTransacao(TipoTransacao.DEPOSITO, valor, null, contaDestino);
        } else {
            throw new IllegalArgumentException("Falha ao realizar o depósito.");
        }
    }

    public void realizarTransferencia(String numeroContaOrigem, String numeroContaDestino, double valor) {
        validarTransferencia(numeroContaOrigem, numeroContaDestino, valor);
        Conta contaOrigem = contaService.buscarContaPorNumero(numeroContaOrigem);
        Conta contaDestino = contaService.buscarContaPorNumero(numeroContaDestino);

        if (contaService.transferir(numeroContaOrigem, numeroContaDestino, valor)) {
            registrarTransacao(TipoTransacao.TRANSFERENCIA, valor, contaOrigem, contaDestino);
        } else {
            throw new IllegalArgumentException("Falha ao realizar a transferência.");
        }
    }

    private void registrarTransacao(TipoTransacao tipo, double valor, Conta contaOrigem, Conta contaDestino) {
        Transacao transacao = new Transacao(UUID.randomUUID().toString(), tipo, valor, contaOrigem, contaDestino, LocalDateTime.now());
        transacaoRepository.registrarTransacao(transacao);
        System.out.println("LOG - Transação registrada com sucesso: " + tipo);
    }


    private void validarSaque(String numeroContaOrigem, double valor) {
        if (numeroContaOrigem == null || numeroContaOrigem.isBlank()) {
            throw new IllegalArgumentException("A conta de origem deve ser informada para saques.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo.");
        }
    }

    private void validarDeposito(String numeroContaDestino, double valor) {
        if (numeroContaDestino == null || numeroContaDestino.isBlank()) {
            throw new IllegalArgumentException("A conta de destino deve ser informada para depósitos.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }
    }

    private void validarTransferencia(String numeroContaOrigem, String numeroContaDestino, double valor) {
        if (numeroContaOrigem == null || numeroContaOrigem.isBlank()) {
            throw new IllegalArgumentException("A conta de origem deve ser informada para transferências.");
        }
        if (numeroContaDestino == null || numeroContaDestino.isBlank()) {
            throw new IllegalArgumentException("A conta de destino deve ser informada para transferências.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }
    }

}

