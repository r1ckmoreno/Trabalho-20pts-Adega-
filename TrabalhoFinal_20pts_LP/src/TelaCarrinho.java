import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaCarrinho extends JFrame {
    private JPanel painelCarrinho;
    private JButton botaoFinalizarCompra;
    private JButton botaoLimparCarrinho;
    private ProductDisplay telaPrincipal;

    public TelaCarrinho(List<Vinho> itensCarrinho, ProductDisplay telaPrincipal) {
        this.telaPrincipal = telaPrincipal;

        setTitle("Carrinho de Compras");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        painelCarrinho = new JPanel();
        painelCarrinho.setLayout(new GridLayout(0, 1, 10, 10));

        for (Vinho vinho : itensCarrinho) {
            JPanel itemCarrinho = criarItemCarrinho(vinho, itensCarrinho);
            painelCarrinho.add(itemCarrinho);
        }

        JScrollPane scrollPane = new JScrollPane(painelCarrinho);
        add(scrollPane, BorderLayout.CENTER);

        botaoFinalizarCompra = new JButton("Finalizar Compra");
        botaoFinalizarCompra.addActionListener(e -> {
            if (telaPrincipal.getUsuarioAtual() == null) {
                JOptionPane.showMessageDialog(this, "Por favor, faça login para finalizar a compra.");
                new TelaLogin(telaPrincipal);
            } else {
                new TelaEndereco(itensCarrinho, telaPrincipal);
                dispose();
            }
        });

        botaoLimparCarrinho = new JButton("Limpar Carrinho");
        botaoLimparCarrinho.addActionListener(e -> {
            itensCarrinho.clear();
            dispose();
            new TelaCarrinho(itensCarrinho, telaPrincipal);
        });

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelInferior.add(botaoLimparCarrinho);
        painelInferior.add(botaoFinalizarCompra);

        add(painelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel criarItemCarrinho(Vinho vinho, List<Vinho> itensCarrinho) {
        JPanel itemCarrinho = new JPanel(new BorderLayout(10, 10));
        itemCarrinho.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nomeItem = new JLabel(vinho.getNome(), JLabel.LEFT);
        JLabel precoItem = new JLabel("R$ " + String.format("%.2f", vinho.getPreco()), JLabel.RIGHT);
        JButton botaoRemover = new JButton("Remover");

        botaoRemover.addActionListener(e -> {
            itensCarrinho.remove(vinho);
            dispose();
            new TelaCarrinho(itensCarrinho, telaPrincipal);
        });

        itemCarrinho.add(nomeItem, BorderLayout.WEST);
        itemCarrinho.add(precoItem, BorderLayout.CENTER);
        itemCarrinho.add(botaoRemover, BorderLayout.EAST);

        return itemCarrinho;
    }
}
