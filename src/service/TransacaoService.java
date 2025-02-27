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

    public void realizarTransacao(TipoTransacao tipo, String numeroContaOrigem, String numeroContaDestino, double valor) {
        validarTransacao(tipo, numeroContaOrigem, numeroContaDestino, valor);

        boolean sucesso = false;
        Conta contaOrigem = contaService.buscarContaPorNumero(numeroContaOrigem);
        Conta contaDestino = numeroContaDestino != null ? contaService.buscarContaPorNumero(numeroContaDestino) : null;

        switch (tipo) {
            case SAQUE:
                sucesso = contaService.sacar(numeroContaOrigem, valor);
                break;
            case DEPOSITO:
                sucesso = contaService.depositar(numeroContaDestino, valor);
                break;
            case TRANSFERENCIA:
                sucesso = contaService.transferir(numeroContaOrigem, numeroContaDestino, valor);
                break;
        }

        if (sucesso) {
            Transacao transacao = new Transacao(
                    UUID.randomUUID().toString(),
                    tipo,
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

    private void validarTransacao(TipoTransacao tipo, String numeroContaOrigem, String numeroContaDestino, double valor) {
        if (numeroContaOrigem == null || numeroContaOrigem.isBlank()) {
            throw new IllegalArgumentException("A conta de origem deve ser informada.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser positivo.");
        }
        if (tipo == TipoTransacao.TRANSFERENCIA && (numeroContaDestino == null || numeroContaDestino.isBlank())) {
            throw new IllegalArgumentException("A conta de destino deve ser informada para transferências.");
        }
    }
}

