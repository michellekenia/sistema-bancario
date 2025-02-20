package repository;

import model.Transacao;

import java.util.ArrayList;
import java.util.List;

public class TransacaoRepository {

    private List<Transacao> transacoes = new ArrayList<>();

    public void registrarTransacao(Transacao transacao){
        transacoes.add(transacao);
    }

    public List<Transacao> retornarHistoricoTransacoes() {
        return transacoes;
    }
}
