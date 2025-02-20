package model;

public class Conta {
    private String numeroConta;
    private String agencia;
    private Cliente cliente;
    private double saldo;
    private double limite;
    private String tipoConta;

    public Conta(String numeroConta, String tipoConta, double saldo, String agencia, Cliente cliente, double limite) {
        this.numeroConta = numeroConta;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
        this.agencia = agencia;
        this.cliente = cliente;
        this.limite = limite;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public String getTipoConta() {
        return tipoConta;
    }

}