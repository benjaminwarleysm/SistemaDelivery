package view;

import model.Categoria;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaDemonstracao extends JFrame {

    private final List<Categoria> categorias = new ArrayList<>();
    private final List<Produto> produtos = new ArrayList<>();
    private Cliente clienteAtual;
    private Pedido pedidoAtual = new Pedido();

    private int proximoIdCategoria = 1;
    private int proximoIdProduto = 1;
    private int proximoIdCliente = 1;

    private JTextField txtNomeCategoria;
    private JComboBox<Categoria> comboCategoria;

    private JTextField txtNomeProduto;
    private JTextField txtPrecoProduto;
    private JComboBox<Produto> comboProduto;

    private JTextField txtNomeCliente;
    private JTextField txtTelefoneCliente;
    private JTextField txtEnderecoCliente;
    private JLabel lblClienteAtual;

    private JTextField txtQuantidade;
    private JComboBox<String> comboFormaPagamento;
    private JTable tabelaItens;
    private DefaultTableModel modeloItens;
    private JLabel lblTotal;
    private JTextArea areaResumoPedidos;

    public TelaDemonstracao() {
        setTitle("Sistema de Delivery - Demonstracao");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 700);
        setLocationRelativeTo(null);
        initComponents();
        atualizarTotal();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        add(montarPainelCadastros(), BorderLayout.NORTH);
        add(montarPainelPedido(), BorderLayout.CENTER);
        add(montarPainelResumo(), BorderLayout.SOUTH);
    }

    private JPanel montarPainelCadastros() {
        JPanel painel = new JPanel(new GridLayout(1, 3, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.add(montarPainelCategoria());
        painel.add(montarPainelProduto());
        painel.add(montarPainelCliente());
        return painel;
    }

    private JPanel montarPainelCategoria() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createTitledBorder("Categoria"));

        txtNomeCategoria = new JTextField();
        JButton btnAdicionar = new JButton("Adicionar Categoria");
        comboCategoria = new JComboBox<>();

        btnAdicionar.addActionListener(e -> adicionarCategoria());

        painel.add(new JLabel("Nome:"));
        painel.add(txtNomeCategoria);
        painel.add(btnAdicionar);
        painel.add(new JLabel("Categorias cadastradas:"));
        painel.add(comboCategoria);
        return painel;
    }

    private JPanel montarPainelProduto() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createTitledBorder("Produto"));

        txtNomeProduto = new JTextField();
        txtPrecoProduto = new JTextField();
        JButton btnAdicionar = new JButton("Adicionar Produto");
        comboProduto = new JComboBox<>();

        btnAdicionar.addActionListener(e -> adicionarProduto());

        painel.add(new JLabel("Nome:"));
        painel.add(txtNomeProduto);
        painel.add(new JLabel("Preco:"));
        painel.add(txtPrecoProduto);
        painel.add(btnAdicionar);
        painel.add(new JLabel("Produtos cadastrados:"));
        painel.add(comboProduto);
        return painel;
    }

    private JPanel montarPainelCliente() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createTitledBorder("Cliente"));

        txtNomeCliente = new JTextField();
        txtTelefoneCliente = new JTextField();
        txtEnderecoCliente = new JTextField();
        JButton btnCriar = new JButton("Criar Cliente");
        lblClienteAtual = new JLabel("Nenhum cliente selecionado");

        btnCriar.addActionListener(e -> criarCliente());

        painel.add(new JLabel("Nome:"));
        painel.add(txtNomeCliente);
        painel.add(new JLabel("Telefone:"));
        painel.add(txtTelefoneCliente);
        painel.add(new JLabel("Endereco:"));
        painel.add(txtEnderecoCliente);
        painel.add(btnCriar);
        painel.add(lblClienteAtual);
        return painel;
    }

    private JPanel montarPainelPedido() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Pedido"));

        JPanel painelItem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtQuantidade = new JTextField(5);
        comboFormaPagamento = new JComboBox<>(new String[]{"Dinheiro", "Cartao", "Pix"});
        JButton btnAdicionarItem = new JButton("Adicionar Item ao Pedido");
        JButton btnFinalizar = new JButton("Finalizar Pedido");

        btnAdicionarItem.addActionListener(e -> adicionarItemAoPedido());
        btnFinalizar.addActionListener(e -> finalizarPedido());

        painelItem.add(new JLabel("Quantidade:"));
        painelItem.add(txtQuantidade);
        painelItem.add(btnAdicionarItem);
        painelItem.add(new JLabel("Forma de Pagamento:"));
        painelItem.add(comboFormaPagamento);
        painelItem.add(btnFinalizar);

        modeloItens = new DefaultTableModel(new Object[]{"Produto", "Quantidade", "Preco Unit.", "Subtotal"}, 0);
        tabelaItens = new JTable(modeloItens);

        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 14f));

        painel.add(painelItem, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaItens), BorderLayout.CENTER);
        painel.add(lblTotal, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel montarPainelResumo() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Pedidos Finalizados"));
        areaResumoPedidos = new JTextArea(6, 0);
        areaResumoPedidos.setEditable(false);
        painel.add(new JScrollPane(areaResumoPedidos), BorderLayout.CENTER);
        return painel;
    }

    private void adicionarCategoria() {
        String nome = txtNomeCategoria.getText();
        if (nome.isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o nome da categoria.");
            return;
        }
        Categoria categoria = new Categoria(proximoIdCategoria, nome);
        proximoIdCategoria++;
        categorias.add(categoria);
        comboCategoria.addItem(categoria);
        txtNomeCategoria.setText("");
    }

    private void adicionarProduto() {
        Categoria categoria = (Categoria) comboCategoria.getSelectedItem();
        if (categoria == null) {
            JOptionPane.showMessageDialog(this, "Cadastre uma categoria antes de cadastrar um produto.");
            return;
        }
        if (txtNomeProduto.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o nome do produto.");
            return;
        }
        double preco;
        try {
            preco = Double.parseDouble(txtPrecoProduto.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe um preco valido.");
            return;
        }
        Produto produto = new Produto(proximoIdProduto, txtNomeProduto.getText(), preco, categoria.getId());
        proximoIdProduto++;
        produtos.add(produto);
        comboProduto.addItem(produto);
        txtNomeProduto.setText("");
        txtPrecoProduto.setText("");
    }

    private void criarCliente() {
        if (txtNomeCliente.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o nome do cliente.");
            return;
        }
        clienteAtual = new Cliente(proximoIdCliente, txtNomeCliente.getText(),
                txtTelefoneCliente.getText(), txtEnderecoCliente.getText());
        proximoIdCliente++;
        lblClienteAtual.setText("Cliente atual: " + clienteAtual);
        txtNomeCliente.setText("");
        txtTelefoneCliente.setText("");
        txtEnderecoCliente.setText("");
    }

    private void adicionarItemAoPedido() {
        Produto produto = (Produto) comboProduto.getSelectedItem();
        if (produto == null) {
            JOptionPane.showMessageDialog(this, "Cadastre um produto antes de montar o pedido.");
            return;
        }
        int quantidade;
        try {
            quantidade = Integer.parseInt(txtQuantidade.getText());
            if (quantidade <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe uma quantidade valida.");
            return;
        }

        ItemPedido item = new ItemPedido(produto.getId(), produto.getNome(), quantidade, produto.getPreco());
        pedidoAtual.adicionarItem(item);

        modeloItens.addRow(new Object[]{
                item.getNomeProduto(), item.getQuantidade(), item.getPrecoUnitario(), item.calcularSubtotal()
        });
        txtQuantidade.setText("");
        atualizarTotal();
    }

    private void atualizarTotal() {
        lblTotal.setText(String.format("Total: R$ %.2f", pedidoAtual.getTotal()));
    }

    private void finalizarPedido() {
        if (clienteAtual == null) {
            JOptionPane.showMessageDialog(this, "Crie um cliente antes de finalizar o pedido.");
            return;
        }
        if (pedidoAtual.getItens().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos um item ao pedido.");
            return;
        }

        pedidoAtual.setIdCliente(clienteAtual.getId());
        pedidoAtual.setFormaPagamento((String) comboFormaPagamento.getSelectedItem());

        areaResumoPedidos.append("Cliente: " + clienteAtual.getNome()
                + " | Forma de Pagamento: " + pedidoAtual.getFormaPagamento()
                + " | Status: " + pedidoAtual.getStatus()
                + " | Total: R$ " + String.format("%.2f", pedidoAtual.getTotal()) + "\n");

        for (ItemPedido item : pedidoAtual.getItens()) {
            areaResumoPedidos.append("   " + item.getQuantidade() + "x " + item.getNomeProduto()
                    + " = R$ " + String.format("%.2f", item.calcularSubtotal()) + "\n");
        }

        pedidoAtual = new Pedido();
        modeloItens.setRowCount(0);
        atualizarTotal();
    }
}