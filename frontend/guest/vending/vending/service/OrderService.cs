using System.Text;
using System.Text.Json;
using vending.model;
using vending.network;

namespace vending.service
{
    public class OrderService
    {
        public async Task<Order> GetOrder(string orderId)
        {
            HttpResponseMessage response =
                await ApiClient.Client.GetAsync($"orders/{orderId}");

            response.EnsureSuccessStatusCode();

            string json = await response.Content.ReadAsStringAsync();

            ApiResponse<Order> apiResponse =
                JsonSerializer.Deserialize<ApiResponse<Order>>(
                    json,
                    new JsonSerializerOptions
                    {
                        PropertyNameCaseInsensitive = true
                    });

            return apiResponse.Result;
        }

        public async Task<string> GetOrderStatus(string orderId)
        {
            HttpResponseMessage response =
                await ApiClient.Client.GetAsync($"orders/{orderId}/status");

            response.EnsureSuccessStatusCode();

            string json = await response.Content.ReadAsStringAsync();

            ApiResponse<string> apiResponse =
                JsonSerializer.Deserialize<ApiResponse<string>>(json,
                new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                });

            return apiResponse.Result;
        }

        public async Task CancelOrder(string orderId)
        {
            HttpResponseMessage response =
                await ApiClient.Client.PutAsync(
                    $"orders/pending/{orderId}/status/CANCELLED",
                    null);

            response.EnsureSuccessStatusCode();
        }

        public async Task<string> CreateOrder(CreateOrderRequest request)
        {
            string json = JsonSerializer.Serialize(request);

            StringContent content = new StringContent(
                json,
                Encoding.UTF8,
                "application/json");

            HttpResponseMessage response =
                await ApiClient.Client.PostAsync("orders", content);

            response.EnsureSuccessStatusCode();

            string result = await response.Content.ReadAsStringAsync();

            ApiResponse<string> apiResponse =
                JsonSerializer.Deserialize<ApiResponse<string>>(
                    result,
                    new JsonSerializerOptions
                    {
                        PropertyNameCaseInsensitive = true
                    });

            return apiResponse.Result;
        }
    }
}