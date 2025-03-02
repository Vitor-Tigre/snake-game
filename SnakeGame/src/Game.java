import javax.swing.*;

public class Game extends JFrame {
    Game() {
        super("Snake Game");
        add(new Board());
        pack();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
