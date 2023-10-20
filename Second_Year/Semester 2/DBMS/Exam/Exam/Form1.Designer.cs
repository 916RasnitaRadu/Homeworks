namespace Exam
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dgvFavouriteBakery = new System.Windows.Forms.DataGridView();
            this.button1 = new System.Windows.Forms.Button();
            this.dgvClients = new System.Windows.Forms.DataGridView();
            this.button2 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.clientIdBox = new System.Windows.Forms.TextBox();
            this.clientNameBox = new System.Windows.Forms.TextBox();
            this.clientSurnameBox = new System.Windows.Forms.TextBox();
            this.clientGenderBox = new System.Windows.Forms.TextBox();
            this.ageBox = new System.Windows.Forms.TextBox();
            this.favBakeryBox = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.dgvFavouriteBakery)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvClients)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvFavouriteBakery
            // 
            this.dgvFavouriteBakery.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvFavouriteBakery.Location = new System.Drawing.Point(183, 87);
            this.dgvFavouriteBakery.Name = "dgvFavouriteBakery";
            this.dgvFavouriteBakery.RowTemplate.Height = 25;
            this.dgvFavouriteBakery.Size = new System.Drawing.Size(362, 384);
            this.dgvFavouriteBakery.TabIndex = 0;
            this.dgvFavouriteBakery.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgvFavouriteBakery_CellClick);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(679, 547);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(116, 30);
            this.button1.TabIndex = 1;
            this.button1.Text = "Insert";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // dgvClients
            // 
            this.dgvClients.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvClients.Location = new System.Drawing.Point(679, 87);
            this.dgvClients.Name = "dgvClients";
            this.dgvClients.RowTemplate.Height = 25;
            this.dgvClients.Size = new System.Drawing.Size(405, 384);
            this.dgvClients.TabIndex = 2;
            this.dgvClients.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgvClients_CellClick);
            this.dgvClients.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView2_CellContentClick);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(846, 546);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(116, 33);
            this.button2.TabIndex = 3;
            this.button2.Text = "Delete";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(1018, 549);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(116, 30);
            this.button3.TabIndex = 4;
            this.button3.Text = "Update";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // clientIdBox
            // 
            this.clientIdBox.Location = new System.Drawing.Point(1261, 124);
            this.clientIdBox.Name = "clientIdBox";
            this.clientIdBox.Size = new System.Drawing.Size(233, 23);
            this.clientIdBox.TabIndex = 5;
            // 
            // clientNameBox
            // 
            this.clientNameBox.Location = new System.Drawing.Point(1261, 174);
            this.clientNameBox.Name = "clientNameBox";
            this.clientNameBox.Size = new System.Drawing.Size(233, 23);
            this.clientNameBox.TabIndex = 6;
            // 
            // clientSurnameBox
            // 
            this.clientSurnameBox.Location = new System.Drawing.Point(1261, 229);
            this.clientSurnameBox.Name = "clientSurnameBox";
            this.clientSurnameBox.Size = new System.Drawing.Size(233, 23);
            this.clientSurnameBox.TabIndex = 7;
            // 
            // clientGenderBox
            // 
            this.clientGenderBox.Location = new System.Drawing.Point(1261, 277);
            this.clientGenderBox.Name = "clientGenderBox";
            this.clientGenderBox.Size = new System.Drawing.Size(233, 23);
            this.clientGenderBox.TabIndex = 8;
            // 
            // ageBox
            // 
            this.ageBox.Location = new System.Drawing.Point(1261, 326);
            this.ageBox.Name = "ageBox";
            this.ageBox.Size = new System.Drawing.Size(233, 23);
            this.ageBox.TabIndex = 9;
            // 
            // favBakeryBox
            // 
            this.favBakeryBox.Location = new System.Drawing.Point(1261, 376);
            this.favBakeryBox.Name = "favBakeryBox";
            this.favBakeryBox.Size = new System.Drawing.Size(233, 23);
            this.favBakeryBox.TabIndex = 10;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1685, 708);
            this.Controls.Add(this.favBakeryBox);
            this.Controls.Add(this.ageBox);
            this.Controls.Add(this.clientGenderBox);
            this.Controls.Add(this.clientSurnameBox);
            this.Controls.Add(this.clientNameBox);
            this.Controls.Add(this.clientIdBox);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.dgvClients);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.dgvFavouriteBakery);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvFavouriteBakery)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvClients)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private DataGridView dgvFavouriteBakery;
        private Button button1;
        private DataGridView dgvClients;
        private Button button2;
        private Button button3;
        private TextBox clientIdBox;
        private TextBox clientNameBox;
        private TextBox clientSurnameBox;
        private TextBox clientGenderBox;
        private TextBox ageBox;
        private TextBox favBakeryBox;
    }
}