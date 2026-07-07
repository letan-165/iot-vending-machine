namespace vending
{
    partial class ProductCard
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

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            picProduct = new PictureBox();
            lblName = new Label();
            lblPrice = new Label();
            btnMinus = new Button();
            btnPlus = new Button();
            lblQuantity = new Label();
            lblStock = new Label();
            label1 = new Label();
            ((System.ComponentModel.ISupportInitialize)picProduct).BeginInit();
            SuspendLayout();
            // 
            // picProduct
            // 
            picProduct.Location = new Point(15, 10);
            picProduct.Name = "picProduct";
            picProduct.Size = new Size(140, 90);
            picProduct.SizeMode = PictureBoxSizeMode.Zoom;
            picProduct.TabIndex = 0;
            picProduct.TabStop = false;
            picProduct.Click += pictureBox1_Click;
            // 
            // lblName
            // 
            lblName.AutoSize = true;
            lblName.Font = new Font("Segoe UI", 10F, FontStyle.Bold);
            lblName.Location = new Point(62, 112);
            lblName.Name = "lblName";
            lblName.Size = new Size(53, 23);
            lblName.TabIndex = 1;
            lblName.Text = "Sting";
            lblName.TextAlign = ContentAlignment.BottomLeft;
            lblName.Click += label1_Click;
            // 
            // lblPrice
            // 
            lblPrice.AutoSize = true;
            lblPrice.Font = new Font("Segoe UI", 10F, FontStyle.Bold);
            lblPrice.Location = new Point(43, 135);
            lblPrice.Name = "lblPrice";
            lblPrice.Size = new Size(81, 23);
            lblPrice.TabIndex = 2;
            lblPrice.Text = "15.000 đ";
            // 
            // btnMinus
            // 
            btnMinus.BackColor = Color.Gray;
            btnMinus.Location = new Point(15, 161);
            btnMinus.Name = "btnMinus";
            btnMinus.Size = new Size(35, 30);
            btnMinus.TabIndex = 3;
            btnMinus.Text = "-";
            btnMinus.UseVisualStyleBackColor = false;
            btnMinus.Click += btnMinus_Click;
            // 
            // btnPlus
            // 
            btnPlus.BackColor = Color.RoyalBlue;
            btnPlus.Location = new Point(120, 161);
            btnPlus.Name = "btnPlus";
            btnPlus.Size = new Size(35, 30);
            btnPlus.TabIndex = 4;
            btnPlus.Text = "+";
            btnPlus.UseVisualStyleBackColor = false;
            btnPlus.Click += btnPlus_Click;
            // 
            // lblQuantity
            // 
            lblQuantity.AutoSize = true;
            lblQuantity.Font = new Font("Segoe UI", 10F);
            lblQuantity.Location = new Point(56, 164);
            lblQuantity.Name = "lblQuantity";
            lblQuantity.Size = new Size(19, 23);
            lblQuantity.TabIndex = 5;
            lblQuantity.Text = "0";
            lblQuantity.TextAlign = ContentAlignment.BottomLeft;
            // 
            // lblStock
            // 
            lblStock.AutoSize = true;
            lblStock.Font = new Font("Segoe UI", 10F, FontStyle.Bold);
            lblStock.Location = new Point(94, 164);
            lblStock.Name = "lblStock";
            lblStock.Size = new Size(20, 23);
            lblStock.TabIndex = 6;
            lblStock.Text = "0";
            lblStock.TextAlign = ContentAlignment.BottomLeft;
            lblStock.Click += lblStock_Click;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("Segoe UI", 10F, FontStyle.Bold);
            label1.Location = new Point(75, 164);
            label1.Name = "label1";
            label1.Size = new Size(18, 23);
            label1.TabIndex = 7;
            label1.Text = "/";
            label1.TextAlign = ContentAlignment.BottomLeft;
            // 
            // ProductCard
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            BackColor = Color.White;
            BorderStyle = BorderStyle.FixedSingle;
            Controls.Add(label1);
            Controls.Add(lblStock);
            Controls.Add(lblQuantity);
            Controls.Add(btnPlus);
            Controls.Add(btnMinus);
            Controls.Add(lblPrice);
            Controls.Add(lblName);
            Controls.Add(picProduct);
            Name = "ProductCard";
            Size = new Size(170, 200);
            Load += ProductCard_Load;
            ((System.ComponentModel.ISupportInitialize)picProduct).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private PictureBox picProduct;
        private Label lblName;
        private Label lblPrice;
        private Button btnMinus;
        private Button btnPlus;
        private Label lblQuantity;
        private Label lblStock;
        private Label label1;
    }
}
