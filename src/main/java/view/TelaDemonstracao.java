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
        setSize(980, 730);
        setLocationRelativeTo(null);
        initComponents();
        atualizarTotal();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(montarPainelCadastros(), BorderLayout.NORTH);
        add(montarPainelPedido(), BorderLayout.CENTER);
        add(montarPainelResumo(), BorderLayout.SOUTH);
    }

    private JPanel montarPainelCadastros() {
        JPanel painel = new JPanel(new GridLayout(1, 3, 20, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.add(montarPainelCategoria());
        painel.add(montarPainelProduto());
        painel.add(montarPainelCliente());
        return painel;
    }

    private JPanel montarPainelCategoria() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Categoria"),
                BorderFactory.createEmptyBorder(8, 10, 10, 10)));

        txtNomeCategoria = new JTextField();
        txtNomeCategoria.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtNomeCategoria.getPreferredSize().height));
        JButton btnAdicionar = new JButton("Adicionar Categoria");
        btnAdicionar.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboCategoria = new JComboBox<>();

        btnAdicionar.addActionListener(e -> adicionarCategoria());

        painel.add(new JLabel("Nome:"));
        painel.add(Box.createVerticalStrut(5));
        painel.add(txtNomeCategoria);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnAdicionar);
        painel.add(Box.createVerticalStrut(15));
        painel.add(new JLabel("Categorias cadastradas:"));
        painel.add(Box.createVerticalStrut(5));
        painel.add(comboCategoria);
        return painel;
    }

    private JPanel montarPainelProduto() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Produto"),
                BorderFactory.createEmptyBorder(8, 10, 10, 10)));

        txtNomeProduto = new JTextField();
        txtNomeProduto.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtNomeProduto.getPreferredSize().height));
        txtPrecoProduto = new JTextField();
        txtPrecoProduto.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtPrecoProduto.getPreferredSize().height));
        JButton btnAdicionar = new JButton("Adicionar Produto");
        btnAdicionar.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboProduto = new JComboBox<>();

        btnAdicionar.addActionListener(e -> adicionarProduto());

        painel.add(new JLabel("Nome:"));
        painel.add(Box.createVerticalStrut(5));
        painel.add(txtNomeProduto);
        painel.add(Box.createVerticalStrut(10));
        painel.add(new JLabel("Preco:"));
        painel.add(Box.createVerticalStrut(5));
        painel.add(txtPrecoProduto);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnAdicionar);
        painel.add(Box.createVerticalStrut(15));
        painel.add(new JLabel("Produtos cadastrados:"));
        painel.add(Box.createVerticalStrut(5));
        painel.add(comboProduto);
        return painel;
    }

    private JPanel montarPainelCliente() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Cliente"),
                BorderFactory.createEmptyBorder(8, 10, 10, 10)));

        txtNomeCliente = new JTextField();
        txtNomeCliente.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtNomeCliente.getPreferredSize().height));
        txtTelefoneCliente = new JTextField();
        txtTelefoneCliente.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtTelefoneCliente.getPreferredSize().height));
        txtEnderecoCliente = new JTextField();
        txtEnderecoCliente.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtEnderecoCliente.getPreferredSize().height));
        JButton btnCriar = new JButton("Criar Cliente");
        btnCriar.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblClienteAtual = new JLabel("Nenhum cliente selecionado");

        btnCriar.addActionListener(e -> criarCliente());

        painel.add(new JLabel("Nome:"));
        painel.add(Box.createVerticalStrut(5));
        painel.add(txtNomeCliente);
        painel.add(Box.createVerticalStrut(10));
        painel.add(new JLabel("Telefone:"));
        painel.add(Box.createVerticalStrut(5));
        painel.add(txtTelefoneCliente);
        painel.add(Box.createVerticalStrut(10));
        painel.add(new JLabel("Endereco:"));
        painel.add(Box.createVerticalStrut(5));
        painel.add(txtEnderecoCliente);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnCriar);
        painel.add(Box.createVerticalStrut(15));
        painel.add(lblClienteAtual);
        return painel;
    }

    private JPanel montarPainelPedido() {
        JPanel painel = new JPanel(new BorderLayout(5, 10));
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Pedido"),
                BorderFactory.createEmptyBorder(8, 10, 10, 10)));

        JPanel painelItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        txtQuantidade = new JTextField(5);
        comboFormaPagamento = new JComboBox<>(new String[]{"Dinheiro", "Cartao", "Pix"});
        JButton btnAdicionarItem = new JButton("Adicionar Item ao Pedido");
        JButton btnFinalizar = new JButton("Finalizar Pedido");

        btnAdicionarItem.addActionListener(e -> adicionarItemAoPedido());
        btnFinalizar.addActionListener(e -> finalizarPedido());

        painelItem.add(new JLabel("Quantidade:"));
        painelItem.add(txtQuantidade);
        painelItem.add(btnAdicionarItem);
        painelItem.add(Box.createHorizontalStrut(25));
        painelItem.add(new JLabel("Forma de Pagamento:"));
        painelItem.add(comboFormaPagamento);
        painelItem.add(btnFinalizar);

        modeloItens = new DefaultTableModel(new Object[]{"Produto", "Quantidade", "Preco Unit.", "Subtotal"}, 0);
        tabelaItens = new JTable(modeloItens);

        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 14f));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        painel.add(painelItem, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaItens), BorderLayout.CENTER);
        painel.add(lblTotal, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel montarPainelResumo() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Pedidos Finalizados"),
                BorderFactory.createEmptyBorder(8, 10, 10, 10)));
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