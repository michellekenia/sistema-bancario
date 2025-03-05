import repository.ClienteRepository;
import repository.ContaRepository;
import repository.TransacaoRepository;
import service.ClienteService;
import service.ContaService;
import service.ExportacaoService;
import service.TransacaoService;


public class Main {
    public static void main(String[] args) {

        ClienteRepository clienteRepository = new ClienteRepository();
        ContaRepository contaRepository = new ContaRepository();
        TransacaoRepository transacaoRepository = new TransacaoRepository();

        ClienteService clienteService = new ClienteService(clienteRepository);
        ContaService contaService = new ContaService(contaRepository);
        ExportacaoService exportacaoService = new ExportacaoService();
        TransacaoService transacaoService = new TransacaoService(contaService, transacaoRepository, exportacaoService);

        Menu menu = new Menu(clienteService, contaService, transacaoService, exportacaoService);
        menu.exibir();
    }
}