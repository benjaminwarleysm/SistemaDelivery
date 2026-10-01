
import javax.swing.SwingUtilities;
import view.TelaCadastro;

public class Delivery {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            TelaCadastro tela = new TelaCadastro();
            tela.setVisible(true);
        });
    }
}