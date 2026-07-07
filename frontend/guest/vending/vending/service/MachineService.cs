using System.Text.Json;
using vending.model;
using vending.network;

namespace vending.service
{
    public class MachineService
    {
        public async Task<Machine> GetMachine(string machineId)
        {
            HttpResponseMessage response =
                await ApiClient.Client.GetAsync($"machines/{machineId}");

            string json = await response.Content.ReadAsStringAsync();

            response.EnsureSuccessStatusCode();

            try
            {
                ApiResponse<Machine> apiResponse =
                    JsonSerializer.Deserialize<ApiResponse<Machine>>(
                        json,
                        new JsonSerializerOptions
                        {
                            PropertyNameCaseInsensitive = true
                        });

                return apiResponse.Result;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
                throw;
            }
        }
    }
}