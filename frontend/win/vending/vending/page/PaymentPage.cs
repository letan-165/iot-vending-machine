using QRCoder;
using System;
using System.Drawing;
using System.IO.Ports;
using System.Threading.Tasks;
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

        private const string PAYMENT_URL =
            "https://iot-vending-machine.netlify.app/user-payment.html?orderId=";

        private SerialPort arduinoPort;

        public PaymentPage(string orderId)
        {
            InitializeComponent();
            this.orderId = orderId;

            InitSerial();
        }

        private void InitSerial()
        {
            try
            {
                arduinoPort = new SerialPort
                {
                    PortName = "COM4",
                    BaudRate = 9600,
                    DataBits = 8,
                    Parity = Parity.None,
                    StopBits = StopBits.One
                };

                if (!arduinoPort.IsOpen)
                    arduinoPort.Open();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Không thể kết nối cổng Serial: " + ex.Message);
            }
        }

        private async void PaymentPage_Load(object sender, EventArgs e)
        {
            try
            {
                order = await orderService.GetOrder(orderId);

                ShowOrder();

                GenerateQRCode($"{PAYMENT_URL}{orderId}");

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
            using (QRCodeGenerator generator = new QRCodeGenerator())
            {
                QRCodeData data = generator.CreateQrCode(
                    text,
                    QRCodeGenerator.ECCLevel.Q);

                using (QRCode qrCode = new QRCode(data))
                {
                    Bitmap bitmap = qrCode.GetGraphic(20);
                    picQR.Image = bitmap;
                }
            }
        }

        private string GetProductCodeFromMongoId(string mongoId)
        {
            switch (mongoId)
            {
                case "6a4a8f5a5641136a60ffd044":
                    return "01";

                case "6a4a8f775641136a60ffd045":
                    return "02";

                case "6a4a8f9f5641136a60ffd046":
                    return "03";

                case "6a4a8fc05641136a60ffd047":
                    return "04";

                case "6a4a8fe65641136a60ffd048":
                    return "05";

                case "6a4a8ffb5641136a60ffd049":
                    return "06";

                default:
                    return "UNKNOWN";
            }
        }

        private async void timerCheckStatus_Tick(object sender, EventArgs e)
        {
            try
            {
                string status = await orderService.GetOrderStatus(orderId);

                if (status == "PAID")
                {
                    StopTimers();

                    // Nếu backend chưa cập nhật thì gọi
                    try
                    {
                        await orderService.UpdateOrderStatus(orderId, "PAID");
                    }
                    catch
                    {
                    }

                    if (arduinoPort != null && arduinoPort.IsOpen)
                    {
                        if (order != null && order.Products != null)
                        {
                            foreach (var item in order.Products)
                            {
                                string productCode =
                                    GetProductCodeFromMongoId(item.Id.ToString());

                                if (productCode != "UNKNOWN")
                                {
                                    int quantity =
                                        item.Quantity <= 0 ? 1 : item.Quantity;

                                    string command =
                                        $"DISPENSE_{productCode}:{quantity}\n";

                                    arduinoPort.Write(command);

                                    await Task.Delay(100);
                                }
                                else
                                {
                                    System.Diagnostics.Debug.WriteLine(
                                        $"Không tìm thấy mã sản phẩm cho {item.Id}");
                                }
                            }
                        }
                    }

                    ResultPage page = new ResultPage(true, orderId);
                    page.Show();
                    this.Close();
                }
                else if (status == "CANCELLED")
                {
                    StopTimers();

                    ResultPage page = new ResultPage(false, orderId);
                    page.Show();
                    this.Close();
                }
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine(ex.Message);
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

                ResultPage page = new ResultPage(false, orderId);
                page.Show();

                this.Close();
            }
        }

        private void StopTimers()
        {
            timerCheckStatus.Stop();
            timerCountdown.Stop();
        }

        protected override void OnFormClosed(FormClosedEventArgs e)
        {
            StopTimers();

            if (arduinoPort != null && arduinoPort.IsOpen)
            {
                arduinoPort.Close();
            }

            base.OnFormClosed(e);
        }

        private void dgvOrder_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
        }

        private void lblName_Click(object sender, EventArgs e)
        {
        }

        private void btnTest_Click(object sender, EventArgs e)
        {
            if (arduinoPort != null && arduinoPort.IsOpen)
            {
                arduinoPort.Write("DISPENSE_01\n");
                MessageBox.Show("Đã gửi lệnh tới Arduino!");
            }
            else
            {
                MessageBox.Show("Cổng Serial chưa mở!");
            }
        }

        private void timerCheckStatus_Tick_1(object sender, EventArgs e)
        {
        }
    }
}