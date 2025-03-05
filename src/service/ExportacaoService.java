package service;

import model.Transacao;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportacaoService {

    public void exportarParaCSV(List<Transacao> transacoes, String caminhoArquivo) {

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (FileWriter writer = new FileWriter(caminhoArquivo)) {

            writer.append("\"ID\",\"Tipo de Transação\",\"Valor\",\"Conta Origem\",\"Conta Destino\",\"Data da Transação\"\n");

            for (Transacao transacao : transacoes) {
                writer.append("\"").append(transacao.getId()).append("\",");
                writer.append("\"").append(transacao.getTipo().toString()).append("\",");
                writer.append("\"").append(String.format("%.2f", transacao.getValor())).append("\",");
                writer.append("\"").append(transacao.getContaOrigem() != null ? transacao.getContaOrigem().getNumeroConta() : "N/A").append("\",");
                writer.append("\"").append(transacao.getContaDestino() != null ? transacao.getContaDestino().getNumeroConta() : "N/A").append("\",");
                writer.append("\"").append(formatoData.format(transacao.getDataHora())).append("\"\n");

                if (transacao.getDataHora() != null) {
                    writer.append("\"").append(transacao.getDataHora().format(formatoData)).append("\"\n");
                } else {
                    writer.append("\"N/A\"\n");
                }
            }


        } catch (IOException e) {
            System.err.println("Erro ao exportar transações: " + e.getMessage());
        }
    }
}
