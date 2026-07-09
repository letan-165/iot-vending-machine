using System;
using System.Windows.Forms;
using vending.model;

namespace vending
{
    public partial class ProductCard : UserControl
    {
        private int stock = 0;
        private int selectedQuantity = 0;
        public string ProductId { get; private set; }
        public int SelectedQuantity
        {
            get { return selectedQuantity; }
        }

        public ProductCard()
        {
            InitializeComponent();
        }

        public void SetData(Product product)
        {
            ProductId = product.Id;

            stock = product.Quantity;

            lblName.Text = product.Name;
            lblPrice.Text = product.Price.ToString("N0") + " đ";
            lblStock.Text = stock.ToString();

            selectedQuantity = 0;

            UpdateStockLabel();

            try
            {
                picProduct.Load(product.Image);
            }
            catch
            {
            }

            if (product.Status != "AVAILABLE")
            {
                btnPlus.Enabled = false;
                btnMinus.Enabled = false;
            }
            else
            {
                btnPlus.Enabled = true;
                btnMinus.Enabled = true;
            }
        }

        private void UpdateStockLabel()
        {
            lblQuantity.Text = selectedQuantity.ToString();
        }

        // Nút +
        private void btnPlus_Click(object sender, EventArgs e)
        {
            if (selectedQuantity < stock)
            {
                selectedQuantity++;
                UpdateStockLabel();
            }
        }

        // Nút -
        private void btnMinus_Click(object sender, EventArgs e)
        {
            if (selectedQuantity > 0)
            {
                selectedQuantity--;
                UpdateStockLabel();
            }
        }

        private void ProductCard_Load(object sender, EventArgs e)
        {

        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void lblStock_Click(object sender, EventArgs e)
        {

        }
    }
}