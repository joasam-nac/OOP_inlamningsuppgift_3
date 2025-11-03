import javax.swing.*;
import java.awt.*;

public class Game {
    private final JFrame frame;
    private final JPanel gameBoard;
    private final GameLogic gameLogic;
    private JButton lastInvalidButton = null;

    public Game(int width, int height, boolean demonstrationMode) {
        this.gameLogic = new GameLogic(width, height, demonstrationMode);
        this.frame = new JFrame("15 spel");
        this.gameBoard = new JPanel(new GridLayout(height, width));

        initGUI();
    }

    private void initGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int tileSize = 100;
        frame.setSize(tileSize * gameLogic.getWidth(),
                tileSize * gameLogic.getHeight() + 50);
        frame.setResizable(false);

        JButton newGameBtn = new JButton("Nytt Spel");
        newGameBtn.addActionListener(_ -> handleNewGame());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(newGameBtn, BorderLayout.NORTH);
        panel.add(gameBoard, BorderLayout.CENTER);

        renderBoard();
        frame.add(panel);
        frame.setVisible(true);
    }

    private void renderBoard() {
        gameBoard.removeAll();
        Grid grid = gameLogic.getGrid();

        for (int y = 0; y < gameLogic.getHeight(); y++) {
            for (int x = 0; x < gameLogic.getWidth(); x++) {
                Tile tile = grid.findTileByPosition(x, y);
                createTileButton(tile);
            }
        }

        gameBoard.revalidate();
        gameBoard.repaint();
    }

    private void createTileButton(Tile tile) {
        final int value = tile.getValue();

        // hanterar tom ruta i samma rad
        JButton button = new JButton(value == 0 ? "" : String.valueOf(value));
        button.setFocusable(false);

        button.addActionListener(_ -> handleTileClick(value, button));
        gameBoard.add(button);
    }

    private void handleTileClick(int value, JButton clickedButton) {
        if (lastInvalidButton != null) {
            lastInvalidButton.setBackground(Color.LIGHT_GRAY);
            lastInvalidButton = null;
        }

        if (value == 0) return;

        // se GameLogic
        if (gameLogic.moveTile(value)) {
            renderBoard();
            checkWinCondition();
        } else {
            clickedButton.setBackground(Color.RED);
            lastInvalidButton = clickedButton;
        }
    }

    private void handleNewGame() {
        gameLogic.newGame();
        lastInvalidButton = null;
        renderBoard();
        checkWinCondition();
    }

    private void checkWinCondition() {
        if (gameLogic.playerHasWon()) {
            JOptionPane.showMessageDialog(null, "Du vann!");
        }
    }

    static void main() {
        GameDialog.show((width, height) -> new Game(width, height, false));
    }
}