using QRCoder;
using System;
using System.Drawing;
using System.Windows.Forms;
using vending.model;
using vending.service;

namespace vending.page
{
    public partial class PaymentPage : Form
    {
        private readonly OrderService orderService = new OrderService();
        private Order order; 
        private readonly string orderId;
        private int seconds = 20;

        public PaymentPage(string orderId)
        {
            InitializeComponent();
            this.orderId = orderId;
        }

        private async void PaymentPage_Load(object sender, EventArgs e)
        {
            try
            {
                order = await orderService.GetOrder(orderId);
                ShowOrder();
                GenerateQRCode("https://google.com");
                lblCountdown.Text = $"Đơn hàng sẽ tự hủy sau: {seconds}s";

                timerCheckStatus.Interval = 1000;
                timerCountdown.Interval = 1000;

                timerCheckStatus.Start();
                timerCountdown.Start();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void ShowOrder()
        {
            dgvOrder.Rows.Clear();

            foreach (var item in order.Products)
            {
                dgvOrder.Rows.Add(
                    item.Name,
                    item.Quantity,
                    item.Price.ToString("N0") + " đ"
                );
            }
            lblTotal.Text = "Tổng tiền: " + order.Total.ToString("N0") + " đ";
        }

        private void GenerateQRCode(string text)
        {
            QRCodeGenerator generator = new QRCodeGenerator();
            QRCodeData data = generator.CreateQrCode(
                text,
                QRCodeGenerator.ECCLevel.Q);
            QRCode qrCode = new QRCode(data);
            Bitmap bitmap = qrCode.GetGraphic(20);
            picQR.Image = bitmap;
        }

        private async void timerCheckStatus_Tick(object sender, EventArgs e)
        {
            try
            {
                string status = await orderService.GetOrderStatus(orderId);

                if (status == "PAID")
                {
                    StopTimers();
                    ResultPage page = new ResultPage(true);
                    page.Show();
                    Hide();
                }
                else if (status == "CANCELLED")
                {
                    StopTimers();
                    ResultPage page = new ResultPage(false);
                    page.Show();
                    Hide();
                }
            }
            catch
            {

            }
        }

        private async void timerCountdown_Tick(object sender, EventArgs e)
        {
            seconds--;
            lblCountdown.Text = $"Đơn hàng sẽ tự hủy sau: {seconds}s";
            if (seconds <= 0)
            {
                StopTimers();
                try
                {
                    await orderService.CancelOrder(orderId);
                }
                catch
                {

                }

                ResultPage page = new ResultPage(false);
                page.Show();

                Hide();
            }
        }

        private void StopTimers()
        {
            timerCheckStatus.Stop();
            timerCountdown.Stop();
        }

        private void dgvOrder_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void lblName_Click(object sender, EventArgs e)
        {

        }

        protected override void OnFormClosed(FormClosedEventArgs e)
        {
            StopTimers();
            base.OnFormClosed(e);
        }

        private void timerCheckStatus_Tick_1(object sender, EventArgs e)
        {

        }
    }
}