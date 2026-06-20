import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GradeCalculatorFrame extends JFrame {
    private JPanel subjectsContainer;
    private JScrollPane scrollPane;
    private JButton addSubjectButton;
    private JButton calculateButton;
    private JButton resetButton;

    private JLabel totalMarksLabel;
    private JLabel averagePercentageLabel;
    private JLabel gradeLabel;

    private List<SubjectRowPanel> rowComponents;

    public GradeCalculatorFrame() {
        setTitle("CodSoft Internship - Student Grade Calculator");
        setSize(550, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        rowComponents = new ArrayList<>();

        // --- Header Panel ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 204, 113));
        JLabel titleLabel = new JLabel("📊 Student Grade Calculator 📊");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // --- Center Panel (Dynamic Viewport) ---
        subjectsContainer = new JPanel();
        subjectsContainer.setLayout(new BoxLayout(subjectsContainer, BoxLayout.Y_AXIS));
        
        scrollPane = new JScrollPane(subjectsContainer);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Enter Subject Information (Marks out of 100)"));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // --- Actions Panel ---
        JPanel sideControlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addSubjectButton = new JButton("➕ Add Subject");
        calculateButton = new JButton("🧮 Calculate Results");
        resetButton = new JButton("🔄 Reset");

        styleButton(addSubjectButton, new Color(52, 152, 219), Color.WHITE);
        styleButton(calculateButton, new Color(46, 204, 113), Color.WHITE);
        styleButton(resetButton, new Color(231, 76, 60), Color.WHITE);

        sideControlsPanel.add(addSubjectButton);
        sideControlsPanel.add(calculateButton);
        sideControlsPanel.add(resetButton);

        // --- Bottom Summary Panel ---
        JPanel bottomContainer = new JPanel(new BorderLayout(5, 5));
        bottomContainer.add(sideControlsPanel, BorderLayout.NORTH);

        JPanel summaryDashboard = new JPanel(new GridLayout(3, 1, 10, 10));
        summaryDashboard.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(10, 20, 20, 20),
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true)
        ));
        summaryDashboard.setBackground(new Color(248, 249, 250));

        totalMarksLabel = new JLabel("Total Marks Obtained: --", SwingConstants.CENTER);
        totalMarksLabel.setFont(new Font("Arial", Font.BOLD, 15));
        
        averagePercentageLabel = new JLabel("Average Percentage: -- %", SwingConstants.CENTER);
        averagePercentageLabel.setFont(new Font("Arial", Font.BOLD, 15));
        
        gradeLabel = new JLabel("Final Grade: --", SwingConstants.CENTER);
        gradeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gradeLabel.setForeground(new Color(44, 62, 80));

        summaryDashboard.add(totalMarksLabel);
        summaryDashboard.add(averagePercentageLabel);
        summaryDashboard.add(gradeLabel);
        bottomContainer.add(summaryDashboard, BorderLayout.SOUTH);

        add(bottomContainer, BorderLayout.SOUTH);

        // --- Event Wiring ---
        addSubjectButton.addActionListener(e -> addNewSubjectRow("", ""));
        calculateButton.addActionListener(e -> calculateStudentPerformance());
        resetButton.addActionListener(e -> resetCalculatorForm());

        resetCalculatorForm();
    }

    private void addNewSubjectRow(String defaultName, String defaultMarks) {
        SubjectRowPanel row = new SubjectRowPanel(this, rowComponents.size() + 1, defaultName, defaultMarks);
        rowComponents.add(row);
        subjectsContainer.add(row);
        subjectsContainer.revalidate();
        subjectsContainer.repaint();
    }

    public void removeSubjectRow(SubjectRowPanel row) {
        if (rowComponents.size() <= 1) {
            JOptionPane.showMessageDialog(this, "At least one subject field is required.", "Form Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        rowComponents.remove(row);
        subjectsContainer.remove(row);
        reindexRowLabels(); // Correct sequence evaluation layout call
        subjectsContainer.revalidate();
        subjectsContainer.repaint();
    }

    private void reindexRowLabels() {
        for (int i = 0; i < rowComponents.size(); i++) {
            rowComponents.get(i).updateIndexLabel(i + 1);
        }
    }

    private void calculateStudentPerformance() {
        if (rowComponents.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No valid subject data rows to parse.", "Evaluation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalMarks = 0;
        
        for (SubjectRowPanel row : rowComponents) {
            String markText = row.getMarksInput();
            String subjectName = row.getSubjectName();

            try {
                double marks = FormValidator.validateAndParseMarks(markText, subjectName);
                totalMarks += marks;
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Failure", JOptionPane.ERROR_MESSAGE);
                row.focusMarksField();
                return;
            }
        }

        double averagePercentage = GradeCalculatorEngine.calculateAverage(totalMarks, rowComponents.size());
        String mappedGrade = GradeCalculatorEngine.determineGrade(averagePercentage);

        totalMarksLabel.setText(String.format("Total Marks Obtained: %.2f / %d", totalMarks, rowComponents.size() * 100));
        averagePercentageLabel.setText(String.format("Average Percentage: %.2f %%", averagePercentage));
        gradeLabel.setText("Final Grade: " + mappedGrade);

        gradeLabel.setForeground(mappedGrade.startsWith("F") ? Color.RED : new Color(39, 174, 96));
    }

    private void resetCalculatorForm() {
        subjectsContainer.removeAll();
        rowComponents.clear();

        totalMarksLabel.setText("Total Marks Obtained: --");
        averagePercentageLabel.setText("Average Percentage: -- %");
        gradeLabel.setText("Final Grade: --");
        gradeLabel.setForeground(new Color(44, 62, 80));

        addNewSubjectRow("Mathematics", "");
        addNewSubjectRow("Science", "");
        addNewSubjectRow("English", "");
        
        // FIXED: Explicit rendering lifecycle update to fix layout ghosting
        subjectsContainer.revalidate();
        subjectsContainer.repaint();
    }

    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }
}
