import javax.swing.*;
import java.awt.*;

public class GameDialog {
    public interface GameDialogListener {
        void onDialogResult(int width, int height);
    }

    public static void show(GameDialogListener listener) {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(4, 2, 100, 1));
            JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(4, 2, 100, 1));

            panel.add(new JLabel("Bredd:"));
            panel.add(widthSpinner);
            panel.add(new JLabel("Höjd:"));
            panel.add(heightSpinner);

            if (JOptionPane.showConfirmDialog(null, panel, "Välj storlek",
                    JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                listener.onDialogResult(
                        (Integer) widthSpinner.getValue(),
                        (Integer) heightSpinner.getValue()
                );
            } else {
                System.exit(0);
            }
        });
    }
}