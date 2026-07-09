using System.Text.Json.Serialization;

namespace vending.model
{
    public class ApiResponse<T>
    {
        [JsonPropertyName("code")]
        public int Code { get; set; }

        [JsonPropertyName("message")]
        public string Message { get; set; }

        [JsonPropertyName("result")]
        public T Result { get; set; }
    }
}