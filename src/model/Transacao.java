package model;

import java.time.LocalDateTime;

public class Transacao {

    private String tipo;
    private double valor;
    private String contaOrigem;
    private String contaDestino;
    private LocalDateTime dataHora;

    public Transacao(String tipo, LocalDateTime dataHora, String contaOrigem, String contaDestino, double valor) {
        this.tipo = tipo;
        this.dataHora = dataHora;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

}
