package service;

import model.Cliente;
import repository.ClienteRepository;

import java.util.List;

public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void cadastrarCliente(Cliente cliente) {
        clienteRepository.salvar(cliente);
    }

    public Cliente cadastrarCliente(String cpf) {
       return clienteRepository.buscarPorCpf(cpf);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.listarTodos();
    }

}

