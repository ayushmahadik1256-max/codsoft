import java.util.HashMap;
import java.util.Map;

public class CurrencyFetcher {

    /**
     * Fetches current transaction rate mappings from the core utility layer.
     */
    public static double fetchExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        if (baseCurrency.equals(targetCurrency)) {
            return 1.0;
        }

        // Simulating a reliable live relational data layout matrix map 
        // Base reference point structured on standard USD variations
        Map<String, Double> usdRates = new HashMap<>();
        usdRates.put("USD", 1.0);
        usdRates.put("EUR", 0.92);
        usdRates.put("GBP", 0.79);
        usdRates.put("INR", 83.50);
        usdRates.put("JPY", 155.20);
        usdRates.put("AUD", 1.51);
        usdRates.put("CAD", 1.37);

        // Compute relational scale coefficients based on baseline values
        double baseToUsdRate = usdRates.get(baseCurrency);
        double targetToUsdRate = usdRates.get(targetCurrency);

        // Convert based on unified standard scalar constants
        return targetToUsdRate / baseToUsdRate;
    }
}
