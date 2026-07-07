using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace vending.model
{
    public class Machine
    {
        [JsonPropertyName("id")]
        public string Id { get; set; }

        [JsonPropertyName("products")]
        public List<Product> Products { get; set; }

        [JsonPropertyName("location")]
        public string Location { get; set; }

        [JsonPropertyName("status")]
        public string Status { get; set; }
    }
}