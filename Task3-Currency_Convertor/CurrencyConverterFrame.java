import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CurrencyConverterFrame extends JFrame {
    private JComboBox<String> baseCurrencyBox;
    private JComboBox<String> targetCurrencyBox;
    private JTextField amountField;
    private JButton convertButton;
    private JButton resetButton;

    private JLabel exchangeRateLabel;
    private JLabel convertedAmountLabel;

    // Supported currency profiles
    private final String[] currencies = {"USD", "EUR", "GBP", "INR", "JPY", "AUD", "CAD"};

    public CurrencyConverterFrame() {
        setTitle("CodSoft Internship - Currency Converter");
        setSize(480, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Header Panel ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(155, 89, 182)); // Elegant Purple
        JLabel titleLabel = new JLabel("💱 Currency Converter 💱");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // --- Center Input Panel ---
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        centerPanel.setBorder(new EmptyBorder(20, 30, 10, 30));

        JLabel baseLabel = new JLabel("Base Currency:");
        baseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        baseCurrencyBox = new JComboBox<>(currencies);
        baseCurrencyBox.setSelectedItem("USD");

        JLabel targetLabel = new JLabel("Target Currency:");
        targetLabel.setFont(new Font("Arial", Font.BOLD, 14));
        targetCurrencyBox = new JComboBox<>(currencies);
        targetCurrencyBox.setSelectedItem("INR");

        JLabel amountLabel = new JLabel("Amount to Convert:");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));

        centerPanel.add(baseLabel);
        centerPanel.add(baseCurrencyBox);
        centerPanel.add(targetLabel);
        centerPanel.add(targetCurrencyBox);
        centerPanel.add(amountLabel);
        centerPanel.add(amountField);
        add(centerPanel, BorderLayout.CENTER);

        // --- Bottom Summary & Action Controls ---
        JPanel bottomContainer = new JPanel(new BorderLayout(5, 5));

        // Action Toolbar Buttons
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        convertButton = new JButton("Convert");
        resetButton = new JButton("Reset");
        
        styleButton(convertButton, new Color(155, 89, 182), Color.WHITE);
        styleButton(resetButton, new Color(149, 165, 166), Color.WHITE);
        actionsPanel.add(convertButton);
        actionsPanel.add(resetButton);
        bottomContainer.add(actionsPanel, BorderLayout.NORTH);

        // Dashboard Display Visual Board
        JPanel displayDashboard = new JPanel(new GridLayout(2, 1, 5, 5));
        displayDashboard.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(10, 30, 20, 30),
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true)
        ));
        displayDashboard.setBackground(new Color(250, 250, 250));

        exchangeRateLabel = new JLabel("Exchange Rate: --", SwingConstants.CENTER);
        exchangeRateLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        
        convertedAmountLabel = new JLabel("Converted Amount: --", SwingConstants.CENTER);
        convertedAmountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        convertedAmountLabel.setForeground(new Color(44, 62, 80));

        displayDashboard.add(exchangeRateLabel);
        displayDashboard.add(convertedAmountLabel);
        bottomContainer.add(displayDashboard, BorderLayout.SOUTH);

        add(bottomContainer, BorderLayout.SOUTH);

        // --- Action Event Bindings ---
        convertButton.addActionListener(e -> executeConversion());
        resetButton.addActionListener(e -> resetFormFields());
    }

    private void executeConversion() {
        String base = (String) baseCurrencyBox.getSelectedItem();
        String target = (String) targetCurrencyBox.getSelectedItem();
        String rawAmount = amountField.getText().trim();

        // 1. Input Verification Block
        double parsedAmount;
        try {
            parsedAmount = InputValidator.validateAmount(rawAmount);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Validation Failure", JOptionPane.ERROR_MESSAGE);
            amountField.requestFocus();
            amountField.selectAll();
            return;
        }

        // 2. Fetch Conversion Exchange Rates
        try {
            double rate = CurrencyFetcher.fetchExchangeRate(base, target);
            
            // 3. Engine Operations Execution
            double finalResult = ConversionEngine.convert(parsedAmount, rate);
            String targetSymbol = ConversionEngine.getCurrencySymbol(target);

            // 4. Scoreboard Dashboard Rendering Updates
            exchangeRateLabel.setText(String.format("1 %s = %.4f %s", base, rate, target));
            convertedAmountLabel.setText(String.format("Converted Value: %s %.2f", targetSymbol, finalResult));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to capture exchange rate configurations.", "API Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFormFields() {
        baseCurrencyBox.setSelectedItem("USD");
        targetCurrencyBox.setSelectedItem("INR");
        amountField.setText("");
        exchangeRateLabel.setText("Exchange Rate: --");
        convertedAmountLabel.setText("Converted Amount: --");
    }

    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }
}
