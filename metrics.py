import requests
import json

# Define the metrics data
metrics_data = {
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
}

# Function to send the metrics data to the server
def send_metrics(metrics_data):
    url = "http://localhost:3000/metrics"  # Replace with your actual API endpoint

    headers = {
        'Content-Type': 'application/json',
    }

    # Send the POST request
    try:
        response = requests.post(url, headers=headers, data=json.dumps(metrics_data))

        # Check if the request was successful
        if response.status_code == 200:
            print("Successfully sent metrics data")
            print("Response:", response.json())
        else:
            print(f"Failed to send metrics data. Status Code: {response.status_code}")
            print("Response:", response.text)
    except Exception as e:
        print(f"An error occurred: {e}")

# Call the function to send the metrics data
send_metrics(metrics_data)
