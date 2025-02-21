package service;

import model.Conta;
import repository.ContaRepository;

public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public void criarConta(Conta conta) {
        contaRepository.addConta(conta);
    }

    public Conta buscarConta(String numeroConta) {
        return contaRepository.buscarConta(numeroConta);
    }

    public void depositar(String numeroConta, double valor) {
        Conta conta = contaRepository.buscarConta(numeroConta);
        if (conta != null && valor > 0) {
            conta.setSaldo(conta.getSaldo() + valor);
        }
    }

    public void sacar(String numeroConta, double valor) {
        Conta conta = contaRepository.buscarConta(numeroConta);
        if (conta != null && valor > 0 && valor <= (conta.getSaldo() + conta.getLimite())) {
            conta.setSaldo(conta.getSaldo() - valor);
        }
    }

    public void transferir(String numeroOrigem, String numeroDestino, double valor) {
        Conta origem = contaRepository.buscarConta(numeroOrigem);
        Conta destino = contaRepository.buscarConta(numeroDestino);

        if (origem != null && destino != null && valor > 0 && valor <= (origem.getSaldo() + origem.getLimite())) {
            origem.setSaldo(origem.getSaldo() - valor);
            destino.setSaldo(destino.getSaldo() + valor);
        }
    }

    public void alterarLimite(String numeroConta, double novoLimite) {
        Conta conta = contaRepository.buscarConta(numeroConta);
        if (conta != null && novoLimite >= 0) {
            conta.setLimite(novoLimite);
        }
    }

}
