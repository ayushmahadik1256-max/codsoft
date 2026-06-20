import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGameGUI extends JFrame {
    // Game Logic Variables
    private int randomNumber;
    private int attemptsLeft;
    private int score;
    private int roundsWon;
    private final int MAX_ATTEMPTS = 10;

    // GUI Components
    private JTextField guessField;
    private JButton guessButton;
    private JButton playAgainButton;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JLabel roundsLabel;

    public NumberGameGUI() {
        // Initialize Window Frame
        setTitle("CodSoft Internship - Number Guessing Game");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Initialize Stats
        score = 0;
        roundsWon = 0;

        // --- Header Panel ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        JLabel titleLabel = new JLabel("🎯 The Number Game 🎯");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // --- Center Panel (Game Area) ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel instructionLabel = new JLabel("I have chosen a number between 1 and 100. Guess it!", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(instructionLabel);

        // Input Wrapper Panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        guessField = new JTextField(6);
        guessField.setFont(new Font("Arial", Font.BOLD, 16));
        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(new JLabel("Enter your guess: "));
        inputPanel.add(guessField);
        inputPanel.add(guessButton);
        centerPanel.add(inputPanel);

        // Feedback Label
        feedbackLabel = new JLabel("Good Luck! Start guessing below.", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 14));
        feedbackLabel.setForeground(new Color(44, 62, 80));
        centerPanel.add(feedbackLabel);

        // Attempts Remaining Label
        attemptsLabel = new JLabel("Attempts Remaining: " + MAX_ATTEMPTS, SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        centerPanel.add(attemptsLabel);

        add(centerPanel, BorderLayout.CENTER);

        // --- Footer Panel (Scoreboard & Actions) ---
        JPanel footerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Score display panel
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        scoreLabel = new JLabel("Total Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 13));
        roundsLabel = new JLabel("Rounds Won: 0");
        roundsLabel.setFont(new Font("Arial", Font.BOLD, 13));
        scorePanel.add(scoreLabel);
        scorePanel.add(roundsLabel);
        footerPanel.add(scorePanel);

        // Play Again Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        playAgainButton = new JButton("Play Another Round");
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 13));
        playAgainButton.setEnabled(false); // Disabled until round ends
        buttonPanel.add(playAgainButton);
        footerPanel.add(buttonPanel);

        add(footerPanel, BorderLayout.SOUTH);

        // --- Event Listeners ---
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });

        // Allow pressing Enter key in the text field to submit
        guessField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewRound();
            }
        });

        // Start the very first round
        startNewRound();
    }

    // Starts a brand new round and resets game states
    private void startNewRound() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1; // 1 to 100 range [cite: 35]
        attemptsLeft = MAX_ATTEMPTS; // Reset attempts limit [cite: 40]

        guessField.setText("");
        guessField.setEditable(true);
        guessButton.setEnabled(true);
        playAgainButton.setEnabled(false);

        feedbackLabel.setText("New round started! Guess a number between 1 and 100.");
        feedbackLabel.setForeground(new Color(44, 62, 80));
        attemptsLabel.setText("Attempts Remaining: " + attemptsLeft);
    }

    // Handles logic execution when a guess is submitted
    private void processGuess() {
        String input = guessField.getText().trim();

        // Input validation
        if (input.isEmpty()) {
            feedbackLabel.setText("❌ Please enter a number first!");
            feedbackLabel.setForeground(Color.RED);
            return;
        }

        int userGuess;
        try {
            userGuess = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("❌ Invalid input! Enter integers only.");
            feedbackLabel.setForeground(Color.RED);
            return;
        }

        if (userGuess < 1 || userGuess > 100) {
            feedbackLabel.setText("❌ The number is strictly between 1 and 100.");
            feedbackLabel.setForeground(Color.RED);
            return;
        }

        attemptsLeft--; // Decrement attempt count [cite: 40]

        // Core comparison and feedback loop [cite: 37]
        if (userGuess == randomNumber) {
            feedbackLabel.setText("🎉 Correct! You guessed it right!");
            feedbackLabel.setForeground(new Color(39, 174, 96)); // Green
            
            // Score tracking algorithm based on attempts left 
            int roundScore = attemptsLeft + 1; 
            score += roundScore;
            roundsWon++;
            
            updateScoreboard();
            endRound();
        } else if (attemptsLeft == 0) {
            feedbackLabel.setText("💥 Game Over! The correct number was " + randomNumber + ".");
            feedbackLabel.setForeground(Color.RED);
            attemptsLabel.setText("Attempts Remaining: 0");
            endRound();
        } else {
            // Provide higher/lower contextual hints [cite: 37]
            if (userGuess > randomNumber) {
                feedbackLabel.setText("📉 Too High! Try a lower number.");
                feedbackLabel.setForeground(new Color(230, 126, 34)); // Orange
            } else {
                feedbackLabel.setText("📈 Too Low! Try a higher number.");
                feedbackLabel.setForeground(new Color(230, 126, 34)); // Orange
            }
            attemptsLabel.setText("Attempts Remaining: " + attemptsLeft);
            guessField.selectAll();
            guessField.requestFocus();
        }
    }

    // Disables inputs and prompts next round choice 
    private void endRound() {
        guessField.setEditable(false);
        guessButton.setEnabled(false);
        playAgainButton.setEnabled(true); // Allow playing again 
    }

    // Refreshes the score display labels 
    private void updateScoreboard() {
        scoreLabel.setText("Total Score: " + score);
        roundsLabel.setText("Rounds Won: " + roundsWon);
    }

    // Main program driver execution point
    public static void main(String[] args) {
        // Enforce thread-safety in Swing execution thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGameGUI().setVisible(true);
            }
        });
    }
}
