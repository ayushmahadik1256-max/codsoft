public class InputValidator {

    /**
     * Evaluates raw fields to confirm they match numerical criteria requirements.
     */
    public static double validateAmount(String rawInput) throws IllegalArgumentException {
        if (rawInput.isEmpty()) {
            throw new IllegalArgumentException("Amount field cannot be empty. Please enter a value.");
        }

        try {
            double amount = Double.parseDouble(rawInput);
            if (amount < 0) {
                throw new IllegalArgumentException("Conversion amount metrics cannot be negative values.");
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input character sequence. Please use numeric text parameters.");
        }
    }
}
