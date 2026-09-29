import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Cliente;

import java.util.Scanner;

public class ClienteProdutoTest {

    @Test
    public void deveArmazenarDadosDoClienteCorretamente() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Joao Silva");
        cliente.setTelefone("(51) 99999-1111");
        cliente.setEndereco("Rua das Flores, 123");

        assertEquals(1, cliente.getId());
        assertEquals("Joao Silva", cliente.getNome());
        assertEquals("(51) 99999-1111", cliente.getTelefone());
        assertEquals("Rua das Flores, 123", cliente.getEndereco());
    }
    @Test
    public void deveArmazenarDadosDoProdutoCorretamente() {

        Produto produto = new Produto();
        produto.setId(1);
        produto.setNome("X-Burguer");
        produto.setPreco(18.90);
        produto.setIdCategoria(1);

        assertEquals(1, produto.getId());
        assertEquals("X-Burguer", produto.getNome());
        assertEquals(18.90, produto.getPreco(), 0.001);
        assertEquals(1, produto.getIdCategoria());
    }
}
