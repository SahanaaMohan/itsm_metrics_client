require 'net/http'
require 'uri'
require 'json'

class MetricsApiClient
  # Initialize with the URL and API endpoint
  def initialize(api_url)
    @api_url = api_url
  end

  # Method to send the metrics data as a POST request
  def send_metrics(metrics)
    uri = URI.parse(@api_url)

    # Prepare the request body as JSON
    request_body = {
      "metrics" => metrics
    }.to_json

    # Create the POST request
    request = Net::HTTP::Post.new(uri, {'Content-Type' => 'application/json'})
    request.body = request_body

    # Send the request and get the response
    response = Net::HTTP.start(uri.hostname, uri.port) do |http|
      http.request(request)
    end

    # Handle the response
    if response.is_a?(Net::HTTPSuccess)
      puts "Successfully sent metrics data"
      puts "Response: #{response.body}"
    else
      puts "Failed to send metrics data"
      puts "Response: #{response.body}"
    end
  end
end
