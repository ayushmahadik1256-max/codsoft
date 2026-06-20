import javax.swing.*;
import java.awt.*;

public class SubjectRowPanel extends JPanel {
    private JLabel rowNumberLabel;
    private JTextField nameField;
    private JTextField markField;
    private JButton deleteRowButton;

    public SubjectRowPanel(GradeCalculatorFrame parentFrame, int index, String defaultName, String defaultMarks) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        rowNumberLabel = new JLabel("Subject " + index + ":");
        rowNumberLabel.setPreferredSize(new Dimension(80, 25));

        nameField = new JTextField(12);
        nameField.setText(defaultName.isEmpty() ? "Subject Name" : defaultName);
        nameField.setForeground(Color.GRAY);
        
        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (nameField.getText().equals("Subject Name") || nameField.getText().isEmpty()) {
                    nameField.setText("");
                    nameField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (nameField.getText().trim().isEmpty()) {
                    nameField.setText("Subject Name");
                    nameField.setForeground(Color.GRAY);
                }
            }
        });

        markField = new JTextField(6);
        markField.setText(defaultMarks);

        deleteRowButton = new JButton("❌");
        deleteRowButton.setMargin(new Insets(2, 5, 2, 5));
        deleteRowButton.addActionListener(e -> parentFrame.removeSubjectRow(this));

        add(rowNumberLabel);
        add(nameField);
        add(new JLabel("Marks:"));
        add(markField);
        add(deleteRowButton);
    }

    public void updateIndexLabel(int index) {
        rowNumberLabel.setText("Subject " + index + ":");
    }

    public String getSubjectName() {
        String name = nameField.getText().trim();
        return name.equals("Subject Name") ? "Unnamed Subject" : name;
    }

    public String getMarksInput() {
        return markField.getText().trim();
    }

    public void focusMarksField() {
        markField.requestFocus();
        markField.selectAll();
    }
}
