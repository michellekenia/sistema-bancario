package model;

import java.time.LocalDateTime;

public class Transacao {

    private String tipo;
    private double valor;
    private Conta contaOrigem;
    private Conta contaDestino;
    private LocalDateTime dataHora;

    public Transacao(String tipo, double valor, Conta contaOrigem, Conta contaDestino, LocalDateTime dataHora) {
        this.tipo = tipo;
        this.valor = valor;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.dataHora = dataHora;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

}
