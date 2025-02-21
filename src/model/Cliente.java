package model;

import java.util.List;

public class Cliente {
    private String nome;
    private String cpf;
    private List<Conta> contas;

    public Cliente(String nome, String cpf, List<Conta> contas) {
        this.nome = nome;
        this.cpf = cpf;
        this.contas = contas;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

}

