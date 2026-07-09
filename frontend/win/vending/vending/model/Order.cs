using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace vending.model
{
    public class Order
    {
        [JsonPropertyName("id")]
        public string Id { get; set; }

        [JsonPropertyName("machineId")]
        public string MachineId { get; set; }

        [JsonPropertyName("products")]
        public List<Product> Products { get; set; }

        [JsonPropertyName("date")]
        public DateTime Date { get; set; }

        [JsonPropertyName("total")]
        public double Total { get; set; }

        [JsonPropertyName("status")]
        public string Status { get; set; }

        [JsonPropertyName("datePay")]
        public DateTime? DatePay { get; set; }
    }
}