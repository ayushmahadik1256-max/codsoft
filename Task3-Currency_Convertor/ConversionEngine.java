import java.util.HashMap;
import java.util.Map;

public class ConversionEngine {

    /**
     * Executes the conversion computation based on input amount and rate scalar factors[cite: 62].
     */
    public static double convert(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }

    /**
     * Returns matching character symbols for visual dashboard indicators[cite: 63].
     */
    public static String getCurrencySymbol(String currencyCode) {
        Map<String, String> symbols = new HashMap<>();
        symbols.put("USD", "$");
        symbols.put("EUR", "€");
        symbols.put("GBP", "£");
        symbols.put("INR", "₹");
        symbols.put("JPY", "¥");
        symbols.put("AUD", "A$");
        symbols.put("CAD", "C$");

        return symbols.getOrDefault(currencyCode, currencyCode);
    }
}
