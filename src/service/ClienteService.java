package service;

import model.Cliente;
import repository.ClienteRepository;

import java.util.List;
import java.util.NoSuchElementException;


public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public boolean cadastrarCliente(Cliente cliente) {
        validarCliente(cliente);

        if (clienteRepository.buscarPorCpf(cliente.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF.");
        }
        return clienteRepository.salvar(cliente);
    }

    public Cliente buscarClientePorCpf(String cpf) {
        validarCpf(cpf);

        return clienteRepository.buscarPorCpf(cpf)
                .orElseThrow(() -> new NoSuchElementException("Cliente com o CPF " + cpf + " não encontrado."));
    }

    public void atualizarNomeCliente(String cpf, String novoNome) {
        validarCpf(cpf);

        if (novoNome == null || novoNome.trim().isEmpty()) {
            throw new IllegalArgumentException("O novo nome do cliente não pode ser vazio.");
        }

        boolean atualizado = clienteRepository.atualizarNome(cpf, novoNome);
        if (!atualizado) {
            throw new NoSuchElementException("Cliente com o CPF " + cpf + " não encontrado.");
        }
    }

    public void deletarClientePorCpf(String cpf) {
        validarCpf(cpf);

        boolean removido = clienteRepository.excluirPorCpf(cpf);
        if (!removido) {
            throw new NoSuchElementException("Cliente com o CPF " + cpf + " não encontrado.");
        }
    }


    public List<Cliente> listarClientes() {
        return clienteRepository.listarTodos();
    }


    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo.");
        }
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF do cliente não pode ser vazio.");
        }
        if (cliente.getTipoCliente() == null) {
            throw new IllegalArgumentException("O tipo de cliente deve ser especificado.");
        }
    }

    private void validarCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio.");
        }
    }


}

