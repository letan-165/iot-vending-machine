using System;
using System.Collections.Generic;
using System.Text;

using System.Net.Http;

namespace vending.network
{
    public class ApiClient
    {
        private static readonly HttpClient client = new HttpClient()
        {
            BaseAddress = new Uri("http://localhost:8080/api/")
            //BaseAddress = new Uri("https://p01--iot-vending-machine--9w4j25rlpvhz.code.run/api/")
        };

        public static HttpClient Client => client;
    }
}