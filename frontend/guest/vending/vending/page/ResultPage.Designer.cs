namespace vending.page
{
    partial class ResultPage
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
            picQR = new PictureBox();
            lblResult = new Label();
            label1 = new Label();
            lblTitle = new Label();
            lblCountdown = new Label();
            timer1 = new System.Windows.Forms.Timer(components);
            ((System.ComponentModel.ISupportInitialize)picQR).BeginInit();
            SuspendLayout();
            // 
            // picQR
            // 
            picQR.BackColor = SystemColors.ControlDark;
            picQR.Location = new Point(71, 63);
            picQR.Name = "picQR";
            picQR.Size = new Size(230, 231);
            picQR.SizeMode = PictureBoxSizeMode.Zoom;
            picQR.TabIndex = 4;
            picQR.TabStop = false;
            picQR.Click += picQR_Click;
            // 
            // lblResult
            // 
            lblResult.AutoSize = true;
            lblResult.Font = new Font("Segoe UI", 15F, FontStyle.Bold);
            lblResult.Location = new Point(12, 307);
            lblResult.Name = "lblResult";
            lblResult.Size = new Size(361, 35);
            lblResult.TabIndex = 6;
            lblResult.Text = "THANH TOÁN THÀNH CÔNG";
            lblResult.TextAlign = ContentAlignment.BottomLeft;
            lblResult.Click += lblTotal_Click;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("Segoe UI", 13F);
            label1.Location = new Point(53, 351);
            label1.Name = "label1";
            label1.Size = new Size(268, 30);
            label1.TabIndex = 7;
            label1.Text = "Cảm ơn bạn đã mua hàng!";
            label1.TextAlign = ContentAlignment.BottomLeft;
            label1.Click += label1_Click;
            // 
            // lblTitle
            // 
            lblTitle.AutoSize = true;
            lblTitle.BackColor = Color.White;
            lblTitle.Font = new Font("Segoe UI", 16F, FontStyle.Bold);
            lblTitle.ForeColor = Color.Black;
            lblTitle.Location = new Point(12, 9);
            lblTitle.Name = "lblTitle";
            lblTitle.Size = new Size(358, 37);
            lblTitle.TabIndex = 8;
            lblTitle.Text = "MÁY BÁN HÀNG TỰ ĐỘNG";
            lblTitle.TextAlign = ContentAlignment.MiddleCenter;
            lblTitle.Click += lblTitle_Click;
            // 
            // lblCountdown
            // 
            lblCountdown.AutoSize = true;
            lblCountdown.Font = new Font("Segoe UI", 10F);
            lblCountdown.Location = new Point(34, 399);
            lblCountdown.Name = "lblCountdown";
            lblCountdown.Size = new Size(315, 23);
            lblCountdown.TabIndex = 9;
            lblCountdown.Text = "Trang này sẽ chuyển tiếp trong vòng: 3s";
            lblCountdown.TextAlign = ContentAlignment.BottomLeft;
            lblCountdown.Click += label2_Click;
            // 
            // timer1
            // 
            timer1.Tick += timer1_Tick;
            // 
            // ResultPage
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            BackColor = Color.White;
            ClientSize = new Size(382, 703);
            Controls.Add(lblCountdown);
            Controls.Add(lblTitle);
            Controls.Add(label1);
            Controls.Add(lblResult);
            Controls.Add(picQR);
            Name = "ResultPage";
            Text = "Trang kết quả";
            Load += ResultPage_Load;
            ((System.ComponentModel.ISupportInitialize)picQR).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion
        private PictureBox picQR;
        private Label lblResult;
        private Label label1;
        private Label lblTitle;
        private Label lblCountdown;
        private System.Windows.Forms.Timer timer1;
    }
}