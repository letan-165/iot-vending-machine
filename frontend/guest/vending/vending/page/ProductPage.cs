using System;
using System.Collections.Generic;
using System.Text.Json;
using System.Windows.Forms;
using vending.model;
using vending.service;

namespace vending.page
{
    public partial class ProductPage : Form
    {
        private List<Product> products = new List<Product>();
        private readonly MachineService machineService = new MachineService();
        private readonly List<ProductCard> cards = new List<ProductCard>();
        private readonly OrderService orderService = new OrderService();
        private const string MACHINE_ID ="6a4b57640346a449bc73f519";

        public ProductPage()
        {
            InitializeComponent();
        }

        private async void ProductPage_Load(object sender, EventArgs e)
        {
            try
            {
                products = (await machineService
                    .GetMachine("6a4b57640346a449bc73f519"))
                    .Products;

                ShowProducts();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
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
                CreateOrderRequest request = new CreateOrderRequest();

                request.MachineId = MACHINE_ID;
                request.Products = new List<CreateOrderItem>();

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