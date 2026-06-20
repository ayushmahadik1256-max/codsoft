import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Enforce thread-safety in Swing execution thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // FIXED: Changed GradeCalculatorEngine to GradeCalculatorFrame
                new GradeCalculatorFrame().setVisible(true);
            }
        });
    }
}
