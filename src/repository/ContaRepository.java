package repository;

import model.Conta;

import java.util.ArrayList;
import java.util.List;

public class ContaRepository {

    private List <Conta> contas = new ArrayList<>();

   public void addConta(Conta conta ) {
       contas.add(conta);
   }

   public Conta buscarConta(String numeroConta){
       return contas.stream()
               .filter(conta -> conta.getNumeroConta().equals(numeroConta))
               .findFirst()
               .orElse(null);
   }

}
