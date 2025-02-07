import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.json.JSONArray;
import org.json.JSONObject;

public class MetricsApiClient {

    private String apiUrl;

    // Constructor to initialize with the API URL
    public MetricsApiClient(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    // Method to send the metrics data as a POST request
    public void sendMetrics(JSONObject metricsData) throws Exception {
        // Create the URL object
        URL url = new URL(apiUrl);

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send the metrics data in JSON format
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = metricsData.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Get the response from the server
        int statusCode = connection.getResponseCode();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            // Output response
            System.out.println("Response Code: " + statusCode);
            System.out.println("Response: " + response.toString());
        }
    }

    public static void main(String[] args) {
        try {
            // Define the metrics data in JSON format
            JSONObject metricsData = new JSONObject();
            JSONArray metricsArray = new JSONArray();

            JSONObject serviceData = new JSONObject();
            serviceData.put("app_name", "third_party_service");
            JSONObject metrics = new JSONObject();
            
            JSONArray errorsArray = new JSONArray();
            errorsArray.put(new JSONObject().put("date", "2025-02-01").put("time", "13:00:00").put("count", 10));
            errorsArray.put(new JSONObject().put("date", "2025-02-02").put("time", "11:00:00").put("count", 5));
            errorsArray.put(new JSONObject().put("date", "2025-02-03").put("time", "12:30:00").put("count", 8));
            errorsArray.put(new JSONObject().put("date", "2025-02-04").put("time", "17:00:00").put("count", 12));
            errorsArray.put(new JSONObject().put("date", "2025-02-05").put("time", "10:00:00").put("count", 15));

            metrics.put("5xx_errors", errorsArray);
            serviceData.put("metrics", metrics);

            metricsArray.put(serviceData);
            metricsData.put("metrics", metricsArray);

            // Create an instance of MetricsApiClient with the API URL
            MetricsApiClient apiClient = new MetricsApiClient("http://localhost:3000/metrics"); // Replace with your actual API endpoint

            // Send the metrics data
            apiClient.sendMetrics(metricsData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
