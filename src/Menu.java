import enuns.TipoCliente;
import enuns.TipoConta;
import enuns.TipoTransacao;
import model.Cliente;
import model.Conta;
import service.ClienteService;
import service.ContaService;
import service.TransacaoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final ClienteService clienteService;
    private final ContaService contaService;
    private final TransacaoService transacaoService;
    private final Scanner scanner;

    public Menu(ClienteService clienteService, ContaService contaService, TransacaoService transacaoService) {
        this.clienteService = clienteService;
        this.contaService = contaService;
        this.transacaoService = transacaoService;
        this.scanner = new Scanner(System.in);
    }

    public void exibir() {
        while (true) {
            System.out.println("\n=== MENU DO SISTEMA BANCÁRIO ===");
            System.out.println("1 - Criar Cliente");
            System.out.println("2 - Criar Conta");
            System.out.println("3 - Realizar Transação");
            System.out.println("4 - Exibir Informações");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarCliente();
                    break;
                case 2:
                    criarConta();
                    break;
                case 3:
                    menuTransacoes();
                    break;
                case 4:
                    exibirInformacoes();
                    break;
                case 5:
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void menuTransacoes() {
        System.out.println("\n=== MENU DE TRANSAÇÕES ===");
        System.out.println("0 - Voltar ao menu anterior.");
        System.out.println("1 - Saque");
        System.out.println("2 - Depósito");
        System.out.println("3 - Transferência");
        System.out.print("Escolha uma opção: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        TipoTransacao tipo = null;

        switch (opcao) {
            case 1:
                tipo = TipoTransacao.SAQUE;
                break;
            case 2:
                tipo = TipoTransacao.DEPOSITO;
                break;
            case 3:
                tipo = TipoTransacao.TRANSFERENCIA;
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                return;
            default:
                System.out.println("Opção inválida! Voltando ao menu principal...");
        }

        realizarTransacao(tipo);
    }

    private void criarCliente() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();

        System.out.print("Tipo de cliente (PF/PJ): ");
        String tipo = scanner.nextLine().toUpperCase();
        TipoCliente tipoCliente = tipo.equals("PJ") ? TipoCliente.PESSOA_JURIDICA : TipoCliente.PESSOA_FISICA;

        Cliente cliente = new Cliente(nome, cpf, tipoCliente, new ArrayList<>());
        boolean sucesso = clienteService.cadastrarCliente(cliente);
        System.out.println("Cliente criado com sucesso: " + cliente.getNome());
    }

    private void criarConta() {
        System.out.print("CPF do titular da conta: ");
        String cpf = scanner.nextLine();

        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Número da Conta: ");
        String numeroConta = scanner.nextLine();

        System.out.print("Número da Agência: ");
        String numeroAgencia = scanner.nextLine();

        System.out.print("Saldo inicial: ");
        double saldo = scanner.nextDouble();

        System.out.print("Limite da conta: ");
        double limite = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Tipo de conta (CORRENTE/POUPANCA): ");
        String tipo = scanner.nextLine().toUpperCase();
        TipoConta tipoConta = tipo.equals("POUPANCA") ? TipoConta.CONTA_POUPANCA : TipoConta.CONTA_CORRENTE;

        Conta novaConta = new Conta(numeroConta, numeroAgencia, cliente, saldo, limite, tipoConta);
        cliente.getContas().add(novaConta);

        boolean sucesso = contaService.criarConta(novaConta);
        if (sucesso) {
            System.out.println("Conta criada com sucesso! Número: " + novaConta.getNumeroConta());
        } else {
            System.out.println("Erro ao criar a conta. Conta já existente.");
        }

        Conta contaEncontrada = contaService.buscarContaPorNumero(numeroConta);

        if (contaEncontrada != null) {
            System.out.println("Conta cadastrada e encontrada com sucesso.");
        } else {
            System.out.println("Erro: Conta não está sendo salva corretamente.");
        }

    }

    private void realizarTransacao(TipoTransacao tipo) {
        String numeroContaOrigem = null;
        String numeroContaDestino = null;


        if (tipo == TipoTransacao.DEPOSITO) {
            System.out.print("Número da conta de destino: ");
            numeroContaDestino = scanner.nextLine();
        } else if (tipo == TipoTransacao.SAQUE) {
            System.out.print("Número da conta de origem: ");
            numeroContaOrigem = scanner.nextLine();
        } else if (tipo == TipoTransacao.TRANSFERENCIA) {
            System.out.print("Número da conta de origem: ");
            numeroContaOrigem = scanner.nextLine();

            System.out.print("Número da conta de destino: ");
            numeroContaDestino = scanner.nextLine();
        }

        System.out.print("Valor da transação: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        try {
            System.out.println("LOG - Iniciando " + tipo);

            if (tipo == TipoTransacao.DEPOSITO) {
                System.out.println("LOG - Conta de destino: " + numeroContaDestino);
                System.out.println("LOG - Valor: " + valor);

                transacaoService.realizarDeposito(numeroContaDestino, valor);
            }
            else if (tipo == TipoTransacao.SAQUE) {
                System.out.println("LOG - Conta de origem: " + numeroContaOrigem);
                System.out.println("LOG - Valor: " + valor);

                transacaoService.realizarSaque(numeroContaOrigem, valor);
            }
            else if (tipo == TipoTransacao.TRANSFERENCIA) {
                System.out.println("LOG - Conta de origem: " + numeroContaOrigem);
                System.out.println("LOG - Conta de destino: " + numeroContaDestino);
                System.out.println("LOG - Valor: " + valor);

                transacaoService.realizarTransferencia(numeroContaOrigem, numeroContaDestino, valor);
            }

            System.out.println("Transação realizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao realizar transação: " + e.getMessage());
        }

    }

    private void exibirInformacoes() {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("\n=== Informações do Cliente ===");
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Tipo: " + cliente.getTipoCliente());

        List<Conta> contas = cliente.getContas();
        if (contas.isEmpty()) {
            System.out.println("O cliente não possui contas cadastradas.");
        } else {
            System.out.println("Contas:");
            for (Conta conta : contas) {
                System.out.println("- Conta: " + conta.getNumeroConta() + " | Agência: " + conta.getNumeroAgencia() + " | Saldo: R$ " + conta.getSaldo());
            }
        }
    }
}
