package repository;

import model.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepository {

    private final List<Cliente> clientes = new ArrayList<>();

    public boolean salvar(Cliente cliente) {
        if (buscarPorCpf(cliente.getCpf()).isPresent()) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst();
    }

    public boolean atualizarNome(String cpf, String novoNome) {
        Optional<Cliente> clienteOptional = buscarPorCpf(cpf);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setNome(novoNome);
            return true;
        }
        return false;
    }

    public boolean excluirPorCpf(String cpf) {
        return clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }


}
