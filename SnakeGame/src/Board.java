import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;

public class Board extends JPanel implements KeyListener {

    private Image apple;
    private Image snakeHead;
    private Image snakeBody;
    private Image appleEasterEgg;

    private final int screenWidth = 600;
    private final int screenHeight = 600;
    private final int squareSize = 30;
    private final int gridSizes = screenWidth / 30;
    private int[][] mapGrid;

    Board() {
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setFocusable(true);

        setImages();
        startGame();
    }

    public void setImages() {
        this.apple = new ImageIcon("src/game-icons/apple-fruit.png").getImage();
        this.snakeHead = new ImageIcon("src/game-icons/snake-head.png").getImage();
        this.snakeBody = new ImageIcon("src/game-icons/snake-body.png").getImage();
        this.appleEasterEgg = new ImageIcon("src/game-icons/apple-logo.png").getImage();
    }

    public void startGame() {
        mapGrid = new int[gridSizes][gridSizes]; // 20x20
        int initialSize = 3;

        int middleGrid = mapGrid.length / 2;
        for (int i = 0; i < initialSize; i++) {
            mapGrid[middleGrid - i][middleGrid] = (i == 0) ? 1 : 2; // 1 == snakeHead && 2 == snakeBody
        }

        spawnApple();

    }

    public void spawnApple() {
        int x, y;

        do {
          x = ThreadLocalRandom.current().nextInt(0, gridSizes);
          y = ThreadLocalRandom.current().nextInt(0, gridSizes);
        } while (mapGrid[x][y] != 0);

        mapGrid[x][y] = 3;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        for (int i = 0; i < screenWidth; i += squareSize) {
//            g.drawLine(i, 0, i, screenHeight);
//        }
//        for (int i = 0; i < screenHeight; i += squareSize) {
//            g.drawLine(0, i, screenWidth, i);
//        }

        for (int x = 0; x < gridSizes; x++) {
            for (int y = 0; y < gridSizes; y++) {

                if (mapGrid[x][y] == 1) {
                    g.drawImage(snakeHead, x * squareSize + 2, y * squareSize + 2, squareSize - 4, squareSize - 4, null);
//                    System.out.println("snake head on position " + x + ", " + y);
                } else if (mapGrid[x][y] == 2) {
                    g.drawImage(snakeBody, x * squareSize + 2, y * squareSize + 2, squareSize - 4, squareSize - 4, null);
//                    System.out.println("snake body on position " + x + ", " + y);
                } else if (mapGrid[x][y] == 3) {

                    int random = ThreadLocalRandom.current().nextInt(0, 100);
                    Image apples = (random % 99 == 0) ? appleEasterEgg : apple;
                    g.drawImage(apples, x * squareSize + 2, y * squareSize + 2, squareSize - 4, squareSize - 4, null);
//                    System.out.println("apple on position " + x + ", " + y);
                }

            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {

        } else if (key == KeyEvent.VK_A) {

        } else if (key == KeyEvent.VK_S) {

        } else if (key == KeyEvent.VK_D) {

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
