using System;
using System.Collections.Generic;
using System.Windows.Forms;
using vending.model;
using vending.network;
using vending.service;

namespace vending.page
{
    public partial class ProductPage : Form
    {
        private List<Product> products = new List<Product>();

        private readonly MachineService machineService = new MachineService();
        private readonly OrderService orderService = new OrderService();
        private readonly List<ProductCard> cards = new List<ProductCard>();

        private bool isChecking = false;
        private int dotCount = 0;

        private const string MACHINE_ID = "6a4b57640346a449bc73f519";

        public ProductPage()
        {
            InitializeComponent();
        }

        private void ProductPage_Load(object sender, EventArgs e)
        {
            lblTitle.Text = "Đang kết nối đến server";

            healthTimer.Interval = 1000;
            healthTimer.Start();
        }

        private async void healthTimer_Tick(object sender, EventArgs e)
        {
            if (isChecking)
                return;

            isChecking = true;

            dotCount = (dotCount + 1) % 4;
            lblTitle.Text = "Đang kết nối đến server" + new string('.', dotCount);

            try
            {
                var response = await ApiClient.Client.GetAsync("health");

                if (!response.IsSuccessStatusCode)
                    return;

                healthTimer.Stop();

                lblTitle.Text = "Máy bán hàng tự động";

                products = (await machineService.GetMachine(MACHINE_ID)).Products;

                ShowProducts();
            }
            catch
            {
                // Server chưa sẵn sàng, timer sẽ tự gọi lại sau 1 giây
            }
            finally
            {
                isChecking = false;
            }
        }

        private void ShowProducts()
        {
            tableProducts.Controls.Clear();
            cards.Clear();

            int row = 0;
            int col = 0;

            foreach (var product in products)
            {
                ProductCard card = new ProductCard();
                card.SetData(product);

                cards.Add(card);

                tableProducts.Controls.Add(card, col, row);

                col++;

                if (col == 2)
                {
                    col = 0;
                    row++;
                }
            }
        }

        private async void btnOrder_Click(object sender, EventArgs e)
        {
            try
            {
                CreateOrderRequest request = new CreateOrderRequest
                {
                    MachineId = MACHINE_ID,
                    Products = new List<CreateOrderItem>()
                };

                foreach (var card in cards)
                {
                    if (card.SelectedQuantity > 0)
                    {
                        request.Products.Add(new CreateOrderItem
                        {
                            ProductId = card.ProductId,
                            Quantity = card.SelectedQuantity
                        });
                    }
                }

                if (request.Products.Count == 0)
                {
                    MessageBox.Show("Vui lòng chọn sản phẩm.");
                    return;
                }

                string orderId = await orderService.CreateOrder(request);

                PaymentPage page = new PaymentPage(orderId);
                page.Show();

                Hide();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void lblTitle_Click(object sender, EventArgs e)
        {

        }

        private void flowProducts_Paint(object sender, PaintEventArgs e)
        {

        }

        private void tableProducts_Paint(object sender, PaintEventArgs e)
        {

        }

        private void lblTitle_Click_1(object sender, EventArgs e)
        {

        }
    }
}