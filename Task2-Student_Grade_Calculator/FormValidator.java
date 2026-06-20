public class FormValidator {
    /**
     * Validates input text rules and safely extracts structural data elements. [cite: 45]
     */
    public static double validateAndParseMarks(String rawInput, String subjectName) throws IllegalArgumentException {
        if (rawInput.isEmpty()) {
            throw new IllegalArgumentException("Empty input field detected. Please review all fields.");
        }

        try {
            double marks = Double.parseDouble(rawInput);
            if (marks < 0 || marks > 100) {
                throw new IllegalArgumentException("Marks for '" + subjectName + "' must be strictly between 0 and 100.");
            }
            return marks;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Non-numeric text encountered. Please provide integers or decimal strings.");
        }
    }
}
