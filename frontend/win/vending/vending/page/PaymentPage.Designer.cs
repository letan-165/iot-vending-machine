namespace vending.page
{
    partial class PaymentPage
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            components = new System.ComponentModel.Container();
            dgvOrder = new DataGridView();
            colName = new DataGridViewTextBoxColumn();
            colQuantity = new DataGridViewTextBoxColumn();
            colPrice = new DataGridViewTextBoxColumn();
            picQR = new PictureBox();
            lblTotal = new Label();
            lblTitle = new Label();
            lblCountdown = new Label();
            timerCheckStatus = new System.Windows.Forms.Timer(components);
            timerCountdown = new System.Windows.Forms.Timer(components);
            ((System.ComponentModel.ISupportInitialize)dgvOrder).BeginInit();
            ((System.ComponentModel.ISupportInitialize)picQR).BeginInit();
            SuspendLayout();
            // 
            // dgvOrder
            // 
            dgvOrder.AllowUserToAddRows = false;
            dgvOrder.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
            dgvOrder.BackgroundColor = Color.White;
            dgvOrder.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dgvOrder.Columns.AddRange(new DataGridViewColumn[] { colName, colQuantity, colPrice });
            dgvOrder.Location = new Point(0, 37);
            dgvOrder.Name = "dgvOrder";
            dgvOrder.ReadOnly = true;
            dgvOrder.RowHeadersVisible = false;
            dgvOrder.RowHeadersWidth = 51;
            dgvOrder.Size = new Size(382, 262);
            dgvOrder.TabIndex = 2;
            dgvOrder.CellContentClick += dgvOrder_CellContentClick;
            // 
            // colName
            // 
            colName.HeaderText = "Tên";
            colName.MinimumWidth = 6;
            colName.Name = "colName";
            colName.ReadOnly = true;
            // 
            // colQuantity
            // 
            colQuantity.HeaderText = "SL";
            colQuantity.MinimumWidth = 6;
            colQuantity.Name = "colQuantity";
            colQuantity.ReadOnly = true;
            // 
            // colPrice
            // 
            colPrice.HeaderText = "Giá";
            colPrice.MinimumWidth = 6;
            colPrice.Name = "colPrice";
            colPrice.ReadOnly = true;
            // 
            // picQR
            // 
            picQR.BackColor = SystemColors.ControlDark;
            picQR.Location = new Point(77, 374);
            picQR.Name = "picQR";
            picQR.Size = new Size(230, 231);
            picQR.SizeMode = PictureBoxSizeMode.StretchImage;
            picQR.TabIndex = 3;
            picQR.TabStop = false;
            // 
            // lblTotal
            // 
            lblTotal.AutoSize = true;
            lblTotal.Font = new Font("Segoe UI", 15F, FontStyle.Bold);
            lblTotal.Location = new Point(154, 318);
            lblTotal.Name = "lblTotal";
            lblTotal.Size = new Size(228, 35);
            lblTotal.TabIndex = 5;
            lblTotal.Text = "Tổng: 10.000 VNĐ";
            lblTotal.TextAlign = ContentAlignment.BottomLeft;
            lblTotal.Click += lblName_Click;
            // 
            // lblTitle
            // 
            lblTitle.AutoSize = true;
            lblTitle.BackColor = Color.White;
            lblTitle.Font = new Font("Segoe UI", 16F, FontStyle.Bold);
            lblTitle.ForeColor = Color.Black;
            lblTitle.Location = new Point(12, -3);
            lblTitle.Name = "lblTitle";
            lblTitle.Size = new Size(358, 37);
            lblTitle.TabIndex = 6;
            lblTitle.Text = "MÁY BÁN HÀNG TỰ ĐỘNG";
            lblTitle.TextAlign = ContentAlignment.MiddleCenter;
            // 
            // lblCountdown
            // 
            lblCountdown.AutoSize = true;
            lblCountdown.Font = new Font("Segoe UI", 10F);
            lblCountdown.Location = new Point(24, 619);
            lblCountdown.Name = "lblCountdown";
            lblCountdown.Size = new Size(226, 23);
            lblCountdown.TabIndex = 10;
            lblCountdown.Text = "Đơn hàng sẽ tự hủy sau: 10s";
            lblCountdown.TextAlign = ContentAlignment.BottomLeft;
            // 
            // timerCheckStatus
            // 
            timerCheckStatus.Tick += timerCheckStatus_Tick;
            // 
            // timerCountdown
            // 
            timerCountdown.Tick += timerCountdown_Tick;
            // 
            // PaymentPage
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(382, 703);
            Controls.Add(lblCountdown);
            Controls.Add(lblTitle);
            Controls.Add(lblTotal);
            Controls.Add(picQR);
            Controls.Add(dgvOrder);
            Name = "PaymentPage";
            Text = "Trang thanh toán";
            Load += PaymentPage_Load;
            ((System.ComponentModel.ISupportInitialize)dgvOrder).EndInit();
            ((System.ComponentModel.ISupportInitialize)picQR).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion
        private DataGridView dgvOrder;
        private DataGridViewTextBoxColumn colName;
        private DataGridViewTextBoxColumn colQuantity;
        private DataGridViewTextBoxColumn colPrice;
        private PictureBox picQR;
        private Label lblTotal;
        private Label lblTitle;
        private Label lblCountdown;
        private System.Windows.Forms.Timer timerCheckStatus;
        private System.Windows.Forms.Timer timerCountdown;
    }
}