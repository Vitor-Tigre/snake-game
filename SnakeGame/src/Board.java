import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Board extends JPanel implements KeyListener {
    private JLabel messageLabel;

    private Image cobraCabeca;
    private Image cobraCorpo;
    private Image apple;
    private Image appleEasterEgg;
    private Image appleInGame;

    private final int telaLargura = 600;
    private final int telaAltura = 600;
    private final int celulaTamanho = 30;   // tamanho em pixels de cada célula do grid
    private final int gridTamanho = telaLargura / celulaTamanho;    // tamanho do grid da tela
    private final int[][] gridMapa = new int[gridTamanho][gridTamanho];   // mapa feito em matriz 20x20

    public ListaLigada cobra = new ListaLigada();   // algoritmo de LinkedList
    private String direcao = "d";

    Board() {
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(telaLargura, telaAltura));
        setFocusable(true);
        addKeyListener(this);

        setImages();
        iniciarGame();

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        messageLabel.setBounds(25, telaAltura / 2 - 30, telaLargura, 60);
        messageLabel.setVisible(false);
        add(messageLabel);

        Timer timer = new Timer(250, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mover();
            }
        });
        timer.start();
    }

    public void setImages() {
        this.apple = new ImageIcon("src/game-icons/apple-fruit.png").getImage();
        this.cobraCabeca = new ImageIcon("src/game-icons/snake-head.png").getImage();
        this.cobraCorpo = new ImageIcon("src/game-icons/snake-body.png").getImage();
        this.appleEasterEgg = new ImageIcon("src/game-icons/apple-logo.png").getImage();
    }

    public void iniciarGame() {
        int meioGrid  = gridMapa.length / 2;
        gridMapa[meioGrid][meioGrid] = 1; // 1 == snakeHead && 2 == snakeBody

        spawnApple();
    }

    public void spawnApple() {
        int x, y;

        do {
          x = ThreadLocalRandom.current().nextInt(0, gridTamanho);
          y = ThreadLocalRandom.current().nextInt(0, gridTamanho);
        } while (gridMapa[x][y] != 0);

        int random = ThreadLocalRandom.current().nextInt(0, 100);
        appleInGame = (random % 99 == 0) ? appleEasterEgg : apple;

        gridMapa[x][y] = 3;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int[] parteCorpo : cobra.getCorpoCobra()) {
            g.drawImage(cobraCorpo, parteCorpo[0] * celulaTamanho + 2, parteCorpo[1] * celulaTamanho + 2, celulaTamanho - 4, celulaTamanho - 4, null);
        }

        int[] cabecaCobra = cobra.getCabeca();
        g.drawImage(cobraCabeca, cabecaCobra[0] * celulaTamanho + 2, cabecaCobra[1] * celulaTamanho + 2, celulaTamanho - 4, celulaTamanho - 4, null);

        for (int x = 0; x < gridTamanho; x++) {
            for (int y = 0; y < gridTamanho; y++) {
                if (gridMapa[x][y] == 3) {
                    g.drawImage(appleInGame, x * celulaTamanho + 2, y * celulaTamanho + 2, celulaTamanho - 4, celulaTamanho - 4, null);
                }
            }
        }
    }

    public boolean colidiu(int x, int y) {
        return x < 0 || x >= gridTamanho || y < 0 || y >= gridTamanho;
    }

    public void mover() {
        int[] cabecaCobra = cobra.getCabeca();
        int x = cabecaCobra[0];
        int y = cabecaCobra[1];

        switch (direcao) {
            case "w": y--; break;
            case "a": x--; break;
            case "s": y++; break;
            case "d": x++; break;
        }

        if (colidiu(x, y) || cobra.colidiuCorpo(x, y)) {
            messageLabel.setText("GAME OVER");
            messageLabel.setVisible(true);

            Timer messageTimer = new Timer(150, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            messageTimer.setRepeats(false);
            messageTimer.start();
        }

        cobra.adicionaCabeca(x, y);

        if (gridMapa[x][y] == 3) {
            spawnApple();
        } else {
            int[] ultimaParteCobra = cobra.removerTail();
            if (ultimaParteCobra != null) {
                gridMapa[ultimaParteCobra[0]][ultimaParteCobra[1]] = 0;
            }
        }

        gridMapa[x][y] = 1;

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W && !Objects.equals(direcao, "s")) {
            direcao = "w";
        } else if (key == KeyEvent.VK_A && !Objects.equals(direcao, "d")) {
            direcao = "a";
        } else if (key == KeyEvent.VK_S && !Objects.equals(direcao, "w")) {
            direcao = "s";
        } else if (key == KeyEvent.VK_D && !Objects.equals(direcao, "a")) {
            direcao = "d";
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
