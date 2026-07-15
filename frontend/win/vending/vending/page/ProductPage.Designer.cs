namespace vending.page
{
    partial class ProductPage
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
            btnOrder = new Button();
            tableProducts = new TableLayoutPanel();
            lblTitle = new Label();
            healthTimer = new System.Windows.Forms.Timer(components);
            SuspendLayout();
            // 
            // btnOrder
            // 
            btnOrder.Dock = DockStyle.Bottom;
            btnOrder.Font = new Font("Segoe UI", 11F, FontStyle.Bold);
            btnOrder.Location = new Point(0, 674);
            btnOrder.Name = "btnOrder";
            btnOrder.Size = new Size(382, 29);
            btnOrder.TabIndex = 1;
            btnOrder.Text = "TẠO ĐƠN HÀNG";
            btnOrder.UseVisualStyleBackColor = true;
            btnOrder.Click += btnOrder_Click;
            // 
            // tableProducts
            // 
            tableProducts.ColumnCount = 2;
            tableProducts.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 50F));
            tableProducts.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 50F));
            tableProducts.Location = new Point(0, 37);
            tableProducts.Name = "tableProducts";
            tableProducts.RowCount = 3;
            tableProducts.RowStyles.Add(new RowStyle(SizeType.Percent, 33.3333321F));
            tableProducts.RowStyles.Add(new RowStyle(SizeType.Percent, 33.3333321F));
            tableProducts.RowStyles.Add(new RowStyle(SizeType.Percent, 33.3333321F));
            tableProducts.Size = new Size(382, 637);
            tableProducts.TabIndex = 2;
            tableProducts.Paint += tableProducts_Paint;
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
            lblTitle.TabIndex = 7;
            lblTitle.Text = "MÁY BÁN HÀNG TỰ ĐỘNG";
            lblTitle.TextAlign = ContentAlignment.MiddleCenter;
            lblTitle.Click += lblTitle_Click_1;
            // 
            // healthTimer
            // 
            healthTimer.Tick += healthTimer_Tick;
            // 
            // ProductPage
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            BackColor = Color.White;
            ClientSize = new Size(382, 703);
            Controls.Add(lblTitle);
            Controls.Add(tableProducts);
            Controls.Add(btnOrder);
            FormBorderStyle = FormBorderStyle.FixedSingle;
            MaximizeBox = false;
            Name = "ProductPage";
            StartPosition = FormStartPosition.CenterParent;
            Text = "Máy bán hàng";
            Load += ProductPage_Load;
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion
        private Button btnOrder;
        private TableLayoutPanel tableProducts;
        private Label lblTitle;
        private System.Windows.Forms.Timer healthTimer;
    }
}