using System;
using System.Windows.Forms;
using vending.service;

namespace vending.page
{
    public partial class ResultPage : Form
    {
        private readonly bool success;
        private int seconds = 10;
        private readonly string orderId;
        private readonly OrderService orderService = new OrderService();
        public ResultPage(bool success, string orderId)
        {
            InitializeComponent();
            this.success = success;
            this.orderId = orderId;
        }

        private async void ResultPage_Load(object sender, EventArgs e)
        {
            if (success)
            {
                lblResult.Text = "THANH TOÁN THÀNH CÔNG";
                lblResult.ForeColor = System.Drawing.Color.Green;

                try
                {
                    await orderService.CompleteOrder(orderId);
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
            else
            {
                lblResult.Text = "THANH TOÁN THẤT BẠI";
                lblResult.ForeColor = System.Drawing.Color.Red;
            }

            lblCountdown.Text = $"Trang sẽ chuyển về trong: {seconds}s";

            timer1.Interval = 1000;
            timer1.Start();
        }


        private void timer1_Tick(object sender, EventArgs e)
        {
            seconds--;

            lblCountdown.Text = $"Trang này sẽ chuyển tiếp trong vòng: {seconds}s";

            if (seconds <= 0)
            {
                timer1.Stop();

                ProductPage page = new ProductPage();
                page.Show();

                this.Close();
            }
        }

        private void lblTotal_Click(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void picQR_Click(object sender, EventArgs e)
        {

        }

        private void lblTitle_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }
    }
}