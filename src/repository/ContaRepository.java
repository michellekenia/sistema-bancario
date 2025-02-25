package repository;

import model.Conta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ContaRepository {

    private final List<Conta> contas = new ArrayList<>();

    public boolean salvar(Conta conta) {
        if (buscarContaPorNumero(conta.getNumeroConta()).isPresent()) {
            return false;
        }
        contas.add(conta);
        return true;
    }

    public Optional<Conta> buscarContaPorNumero(String numeroConta) {
        return contas.stream()
                .filter(conta -> conta.getNumeroConta().equals(numeroConta))
                .findFirst();
    }

    public boolean atualizarConta(String numeroConta, double novoSaldo, double novoLimite) {
        Optional<Conta> contaOptional = buscarContaPorNumero(numeroConta);
        if (contaOptional.isPresent()) {
            Conta conta = contaOptional.get();
            conta.setSaldo(novoSaldo);
            conta.setLimite(novoLimite);
            return true;
        }
        return false;
    }


    public boolean removerContaPorNumero(String numeroConta) {
        return contas.removeIf(conta -> conta.getNumeroConta().equals(numeroConta));
    }

    public List<Conta> listarTodasContas() {
        return new ArrayList<>(contas);
    }

}
