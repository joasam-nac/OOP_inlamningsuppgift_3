import javax.swing.*;
import java.awt.*;

public class GridGUI extends Grid {

    private final JFrame frame;
    private final JPanel panel;
    private final int gridSize;
    private final JButton[][] buttons;

    // senast oflyttbar ruta
    private JButton lastInvalidButton = null;

    public GridGUI(int size) {
        super(size);
        this.gridSize = size;
        this.frame = new JFrame("15 spel");
        this.panel = new JPanel(new GridLayout(size, size));
        this.buttons = new JButton[size][size];

        initGUI();
    }

    private void initGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int tileSize = 100;
        frame.setSize(tileSize * gridSize, tileSize * gridSize);
        frame.setResizable(false);

        updateButtons();
        frame.add(panel);
        frame.setVisible(true);
    }

    private void updateButtons() {
        panel.removeAll();

        // uppdaterar rutnätet
        Tile[][] grid = new Tile[gridSize][gridSize];
        for (Tile t : getTiles()) {
            grid[t.getY()][t.getX()] = t;
        }

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Tile tile = grid[y][x];
                int value = tile.getValue();
                JButton btn = new JButton(value == 0 ? "" : String.valueOf(value));

                btn.setFocusable(false);

                btn.addActionListener(_ -> handleMove(value, btn));

                panel.add(btn);
                buttons[y][x] = btn;
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    private void handleMove(int value, JButton clickedButton) {
        // tar bort färg efter nytt klick
        if (lastInvalidButton != null) {
            lastInvalidButton.setBackground(Color.LIGHT_GRAY);
            lastInvalidButton = null;
        }

        if (value == 0) return;

        if (canMoveTile(value)) {
            moveTile(value);
            updateButtons();
        } else {
            // rutan blir röd om den inte kan flyttas
            clickedButton.setBackground(Color.RED);
            lastInvalidButton = clickedButton;
        }
    }

    static void main() {
        SwingUtilities.invokeLater(() -> new GridGUI(4));
    }
}