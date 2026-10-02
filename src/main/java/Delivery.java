import javax.swing.SwingUtilities;

import model.Categoria;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import view.TelaCadastro;

public class Delivery {

    public static void main(String[] args) {


        demonstrarFluxoDoSistema();

        SwingUtilities.invokeLater(() -> {
            TelaCadastro tela = new TelaCadastro();
            tela.setVisible(true);
        });
    }

    private static void demonstrarFluxoDoSistema() {
        // Categoria -> contem -> Produto
        Categoria categoriaLanches = new Categoria(1, "Lanches");
        Produto xBurguer = new Produto(1, "X-Burguer", 18.90, categoriaLanches.getId());

        // Cliente -> faz -> Pedido
        Cliente cliente = new Cliente(1, "Maria Silva", "(51) 99999-0001", "Rua das Flores, 123");
        Pedido pedido = new Pedido();
        pedido.setIdCliente(cliente.getId());
        pedido.setFormaPagamento("Pix");

        // Pedido -> possui -> ItemPedido / ItemPedido -> referencia -> Produto
        ItemPedido item = new ItemPedido(xBurguer.getId(), xBurguer.getNome(), 2, xBurguer.getPreco());
        pedido.adicionarItem(item);

        System.out.println("Sistema de Delivery");
        System.out.println("Categoria: " + categoriaLanches);
        System.out.println("Produto: " + xBurguer);
        System.out.println("Cliente: " + cliente);
        System.out.println("Pedido do cliente " + cliente.getId()
                + " | Forma de pagamento: " + pedido.getFormaPagamento()
                + " | Status: " + pedido.getStatus());
        System.out.println("Item do pedido: " + item.getQuantidade() + "x " + item.getNomeProduto()
                + " = R$ " + item.calcularSubtotal());
        System.out.println("Total do pedido: R$ " + pedido.getTotal());

    }
}