// Define the metrics data
const metricsData = {
  "metrics": [
    {
      "app_name": "third_party_service",
      "metrics": {
        "4xx_errors": [
          { "date": "2025-02-01", "time": "13:00:00", "count": 10 },
          { "date": "2025-02-02", "time": "11:00:00", "count": 5 },
          { "date": "2025-02-03", "time": "12:30:00", "count": 8 },
          { "date": "2025-02-04", "time": "17:00:00", "count": 12 },
          { "date": "2025-02-05", "time": "10:00:00", "count": 15 }
        ]
      }
    }
  ]
};

// Function to send the metrics data to the server
async function sendMetrics(metricsData) {
  const apiUrl = 'http://localhost:3000/metrics'; // Replace with your actual API endpoint

  try {
    // Send the POST request using Fetch API
    const response = await fetch(apiUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(metricsData) // Convert metricsData to a JSON string
    });

    // Handle the response
    if (response.ok) {
      const responseData = await response.json();
      console.log('Response:', responseData);
    } else {
      console.error('Failed to send metrics data:', response.status, response.statusText);
    }
  } catch (error) {
    console.error('Error sending metrics:', error);
  }
}

// Call the function to send the metrics data
sendMetrics(metricsData);
