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
        };

        public static HttpClient Client => client;
    }
}