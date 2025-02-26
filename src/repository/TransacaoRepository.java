package repository;

import model.Transacao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransacaoRepository {

    private List<Transacao> transacoes = new ArrayList<>();

    public void registrarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<Transacao> retornarHistoricoTransacoes() {
        return new ArrayList<>(transacoes);
    }

    public List<Transacao> listarPorConta(String numeroConta) {
        return transacoes.stream()
                .filter(t -> t.getContaOrigem().equals(numeroConta) ||
                        t.getContaDestino().equals(numeroConta))
                .collect(Collectors.toList());
    }
}
