import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

public class CurrencyConverterServlet extends HttpServlet {

    private static final Map<String, Double> exchangeRates = new HashMap<>();

    static {
        // Base: USD
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("INR", 83.0);
        exchangeRates.put("EUR", 0.93);
        exchangeRates.put("GBP", 0.79);
        exchangeRates.put("JPY", 157.2);
        exchangeRates.put("AUD", 1.51);
        exchangeRates.put("CAD", 1.37);
        exchangeRates.put("CNY", 7.24);
        exchangeRates.put("AED", 3.67);
        exchangeRates.put("CHF", 0.90);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try {
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String amtStr = request.getParameter("amount");

            // Basic validation
            if (from == null || to == null || amtStr == null ||
                from.isEmpty() || to.isEmpty() || amtStr.isEmpty()) {
                out.write("Invalid input.");
                return;
            }

            double amount = Double.parseDouble(amtStr);

            // Get exchange rates
            Double fromRate = exchangeRates.get(from.toUpperCase());
            Double toRate = exchangeRates.get(to.toUpperCase());

            if (fromRate == null || toRate == null) {
                out.write("Unsupported currency.");
                return;
            }

            // Convert using USD as the base
            double usdAmount = amount / fromRate;
            double result = usdAmount * toRate;

            out.write(String.format("%.2f", result));

        } catch (NumberFormatException e) {
            out.write("Please enter a valid number.");
        } catch (Exception e) {
            out.write("Error: " + e.getMessage());
        }
    }
}