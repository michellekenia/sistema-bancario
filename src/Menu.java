import enuns.TipoCliente;
import enuns.TipoConta;
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
                    realizarTransacao();
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
        cliente.getContas().add(novaConta); // Adiciona a conta ao cliente

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

    private void realizarTransacao() {
        System.out.print("Tipo de transação (SAQUE/DEPÓSITO/TRANSFERÊNCIA): ");
        String tipo = scanner.nextLine().toUpperCase();

        System.out.print("Número da conta de origem: ");
        String numeroContaOrigem = scanner.nextLine();


        Conta contaOrigem = contaService.buscarContaPorNumero(numeroContaOrigem);
        if (contaOrigem == null) {
            System.out.println("Conta de origem não encontrada.");
            return;
        }

        String numeroContaDestino = null;
        Conta contaDestino = null;

        if (tipo.equals("TRANSFERÊNCIA")) {
            System.out.print("Número da conta de destino: ");
            numeroContaDestino = scanner.nextLine();
            contaDestino = contaService.buscarContaPorNumero(numeroContaDestino);
            if (contaDestino == null) {
                System.out.println("Conta de destino não encontrada.");
                return;
            }
        }

        System.out.print("Valor da transação: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        try {
            transacaoService.realizarTransacao(tipo, numeroContaOrigem, numeroContaDestino, valor);
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
