package model;

import enuns.TipoCliente;
import enuns.TipoConta;

public class Conta {
    private String numeroConta;
    private String numeroAgencia;
    private Cliente cliente;
    private double saldo;
    private double limite;
    private TipoConta tipoConta;

    public Conta(String numeroConta, String numeroAgencia, Cliente cliente, double saldo, double limite, TipoConta tipoConta) {
        this.numeroConta = numeroConta;
        this.numeroAgencia = numeroAgencia;
        this.cliente = cliente;
        this.saldo = saldo;
        this.limite = limite;
        this.tipoConta = tipoConta;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public String getAgencia() {
        return numeroAgencia;
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

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }
}
