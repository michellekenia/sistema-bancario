package service;

import model.Conta;
import repository.ContaRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public boolean criarConta(Conta novaConta) {
        validarConta(novaConta);

        if (contaRepository.buscarContaPorNumero(novaConta.getNumeroConta()).isPresent()) {
            throw new IllegalArgumentException("Conta com o número " + novaConta.getNumeroConta() + " já existe.");
        }
        contaRepository.salvar(novaConta);

        return true;
    }

    public Conta buscarContaPorNumero(String numeroConta) {
        validarNumeroConta(numeroConta);

        return contaRepository.buscarContaPorNumero(numeroConta)
                .orElseThrow(() -> new NoSuchElementException("Cliente com a conta número " + numeroConta + " não encontrado."));
    }

    public void deletarContaPorNumeroConta(String numeroConta) {
        validarNumeroConta(numeroConta);

        boolean removido = contaRepository.removerContaPorNumero(numeroConta);
        if (!removido) {
            throw new NoSuchElementException("Cliente com a conta número " + numeroConta + " não encontrado.");
        }
    }

    public List<Conta> listarContas() {
        return contaRepository.listarTodasContas();
    }

    public boolean depositar(String numeroConta, double valor) {

        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }
        Optional<Conta> contaOptional = contaRepository.buscarContaPorNumero(numeroConta);
        if (contaOptional.isPresent()) {
            Conta conta = contaOptional.get();
            conta.setSaldo(conta.getSaldo() + valor);
            return true;
        }
        return false;
    }

    public boolean sacar(String numeroConta, double valor) {

        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo.");
        }
        Optional<Conta> contaOptional = contaRepository.buscarContaPorNumero(numeroConta);
        if (contaOptional.isPresent()) {
            Conta conta = contaOptional.get();
            double saldoDisponivel = conta.getSaldo() + conta.getLimite();
            if (valor <= saldoDisponivel) {
                conta.setSaldo(conta.getSaldo() - valor);
                return true;
            } else {
                throw new IllegalArgumentException("Saldo insuficiente para saque.");
            }
        }
        return false;
    }

    public boolean transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {

        if (numeroContaOrigem.equals(numeroContaDestino)) {
            throw new IllegalArgumentException("As contas de origem e destino devem ser diferentes.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        Optional<Conta> contaOrigemOptional = contaRepository.buscarContaPorNumero(numeroContaOrigem);
        Optional<Conta> contaDestinoOptional = contaRepository.buscarContaPorNumero(numeroContaDestino);

        if (contaOrigemOptional.isPresent() && contaDestinoOptional.isPresent()) {
            Conta contaOrigem = contaOrigemOptional.get();
            Conta contaDestino = contaDestinoOptional.get();
            double saldoDisponivel = contaOrigem.getSaldo() + contaOrigem.getLimite();
            if (valor <= saldoDisponivel) {
                contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
                contaDestino.setSaldo(contaDestino.getSaldo() + valor);
                return true;
            } else {
                throw new IllegalArgumentException("Saldo insuficiente na conta de origem.");
            }
        }
        return false;
    }

    public boolean alterarLimite(String numeroConta, double novoLimite) {
        if (novoLimite < 0) {
            throw new IllegalArgumentException("O novo limite não pode ser negativo.");
        }
        Optional<Conta> contaOptional = contaRepository.buscarContaPorNumero(numeroConta);
        if (contaOptional.isPresent()) {
            Conta conta = contaOptional.get();
            conta.setLimite(novoLimite);
            return true;
        }
        return false;
    }


    private void validarConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("A conta não pode ser nula.");
        }
        if (conta.getNumeroConta() == null || conta.getNumeroConta().trim().isEmpty()) {
            throw new IllegalArgumentException("O número da conta não pode ser vazio.");
        }
        if (conta.getNumeroAgencia() == null || conta.getNumeroAgencia().trim().isEmpty()) {
            throw new IllegalArgumentException("O número da agência não pode ser vazio.");
        }
        if (conta.getCliente() == null) {
            throw new IllegalArgumentException("A conta deve estar associada a um cliente.");
        }
        if (conta.getSaldo() < 0) {
            throw new IllegalArgumentException("O saldo não pode ser negativo.");
        }
        if (conta.getLimite() < 0) {
            throw new IllegalArgumentException("O limite não pode ser negativo.");
        }
        if (conta.getTipoConta() == null) {
            throw new IllegalArgumentException("O tipo de conta deve ser especificado.");
        }
    }

    private void validarNumeroConta(String numeroConta) {
        if (numeroConta == null || numeroConta.isBlank()) {
            throw new IllegalArgumentException("O número da conta não pode ser nulo ou vazio.");
        }
    }

}
