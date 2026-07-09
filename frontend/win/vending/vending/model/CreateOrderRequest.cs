using System.Text.Json.Serialization;

public class CreateOrderRequest
{
    [JsonPropertyName("machineId")]
    public string MachineId { get; set; }

    [JsonPropertyName("products")]
    public List<CreateOrderItem> Products { get; set; }
}

public class CreateOrderItem
{
    [JsonPropertyName("productId")]
    public string ProductId { get; set; }

    [JsonPropertyName("quantity")]
    public int Quantity { get; set; }
}