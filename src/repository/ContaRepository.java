package repository;

import model.Conta;

import java.util.ArrayList;
import java.util.List;

public class ContaRepository {

    private List<Conta> contas = new ArrayList<>();

    public void addConta(Conta conta) {
        contas.add(conta);
    }

    public Conta buscarConta(String numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta().equals(numeroConta)) {
                return conta;
            }
        }
        return null;
    }

    public List<Conta> listarContas() {
        return contas;
    }

    public void removerConta(String numeroConta) {
        contas.removeIf(conta -> conta.getNumeroConta().equals(numeroConta));
    }

}
