import javax.swing.SwingUtilities;

import view.TelaDemonstracao;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaDemonstracao tela = new TelaDemonstracao();
            tela.setVisible(true);
        });
    }
}