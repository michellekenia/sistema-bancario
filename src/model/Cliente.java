package model;

import enuns.TipoCliente;

import java.util.List;

public class Cliente {
    private String nome;
    private String cpf;
    private TipoCliente tipoCliente;
    private List<Conta> contas;

    public Cliente(String nome, String cpf, TipoCliente tipoCliente, List<Conta> contas) {
        this.nome = nome;
        this.cpf = cpf;
        this.tipoCliente = tipoCliente;
        this.contas = contas;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

