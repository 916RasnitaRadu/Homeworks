namespace A1
{
    partial class Form1
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
            this.parentTable = new System.Windows.Forms.DataGridView();
            this.childTable = new System.Windows.Forms.DataGridView();
            this.insertButton = new System.Windows.Forms.Button();
            this.deleteButton = new System.Windows.Forms.Button();
            this.updateButton = new System.Windows.Forms.Button();
            this.idTypeBox = new System.Windows.Forms.TextBox();
            this.nameBox = new System.Windows.Forms.TextBox();
            this.descriptionBox = new System.Windows.Forms.TextBox();
            this.priceBox = new System.Windows.Forms.TextBox();
            this.idTypeLabel = new System.Windows.Forms.Label();
            this.nameLabel = new System.Windows.Forms.Label();
            this.descLabel = new System.Windows.Forms.Label();
            this.priceLabel = new System.Windows.Forms.Label();
            this.productIdBox = new System.Windows.Forms.TextBox();
            this.idlabel = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.parentTable)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.childTable)).BeginInit();
            this.SuspendLayout();
            // 
            // parentTable
            // 
            this.parentTable.AccessibleName = "parentTable";
            this.parentTable.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.parentTable.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.parentTable.GridColor = System.Drawing.SystemColors.ActiveCaption;
            this.parentTable.Location = new System.Drawing.Point(75, 69);
            this.parentTable.Name = "parentTable";
            this.parentTable.Size = new System.Drawing.Size(273, 324);
            this.parentTable.TabIndex = 0;
            this.parentTable.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.parentTable_CellClick);
            this.parentTable.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellContentClick);
            // 
            // childTable
            // 
            this.childTable.AccessibleName = "childTable";
            this.childTable.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.childTable.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.childTable.Location = new System.Drawing.Point(429, 69);
            this.childTable.Name = "childTable";
            this.childTable.Size = new System.Drawing.Size(582, 324);
            this.childTable.TabIndex = 2;
            this.childTable.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.childTable_CellClick);
            // 
            // insertButton
            // 
            this.insertButton.AccessibleName = "insertButton";
            this.insertButton.Location = new System.Drawing.Point(429, 419);
            this.insertButton.Name = "insertButton";
            this.insertButton.Size = new System.Drawing.Size(135, 37);
            this.insertButton.TabIndex = 3;
            this.insertButton.Text = "Insert";
            this.insertButton.UseVisualStyleBackColor = true;
            this.insertButton.Click += new System.EventHandler(this.button1_Click);
            // 
            // deleteButton
            // 
            this.deleteButton.Location = new System.Drawing.Point(429, 485);
            this.deleteButton.Name = "deleteButton";
            this.deleteButton.Size = new System.Drawing.Size(135, 37);
            this.deleteButton.TabIndex = 4;
            this.deleteButton.Text = "Delete";
            this.deleteButton.UseVisualStyleBackColor = true;
            this.deleteButton.Click += new System.EventHandler(this.deleteButton_Click);
            // 
            // updateButton
            // 
            this.updateButton.Location = new System.Drawing.Point(429, 553);
            this.updateButton.Name = "updateButton";
            this.updateButton.Size = new System.Drawing.Size(135, 37);
            this.updateButton.TabIndex = 5;
            this.updateButton.Text = "Update";
            this.updateButton.UseVisualStyleBackColor = true;
            this.updateButton.Click += new System.EventHandler(this.updateButton_Click);
            // 
            // idTypeBox
            // 
            this.idTypeBox.Location = new System.Drawing.Point(1208, 143);
            this.idTypeBox.Name = "idTypeBox";
            this.idTypeBox.Size = new System.Drawing.Size(227, 20);
            this.idTypeBox.TabIndex = 6;
            // 
            // nameBox
            // 
            this.nameBox.Location = new System.Drawing.Point(1208, 190);
            this.nameBox.Name = "nameBox";
            this.nameBox.Size = new System.Drawing.Size(227, 20);
            this.nameBox.TabIndex = 7;
            // 
            // descriptionBox
            // 
            this.descriptionBox.Location = new System.Drawing.Point(1208, 244);
            this.descriptionBox.Multiline = true;
            this.descriptionBox.Name = "descriptionBox";
            this.descriptionBox.Size = new System.Drawing.Size(227, 80);
            this.descriptionBox.TabIndex = 8;
            // 
            // priceBox
            // 
            this.priceBox.Location = new System.Drawing.Point(1208, 351);
            this.priceBox.Name = "priceBox";
            this.priceBox.Size = new System.Drawing.Size(227, 20);
            this.priceBox.TabIndex = 9;
            // 
            // idTypeLabel
            // 
            this.idTypeLabel.AutoSize = true;
            this.idTypeLabel.Font = new System.Drawing.Font("Verdana", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.idTypeLabel.Location = new System.Drawing.Point(1100, 143);
            this.idTypeLabel.Name = "idTypeLabel";
            this.idTypeLabel.Size = new System.Drawing.Size(52, 13);
            this.idTypeLabel.TabIndex = 10;
            this.idTypeLabel.Text = "ID Type";
            // 
            // nameLabel
            // 
            this.nameLabel.AutoSize = true;
            this.nameLabel.Font = new System.Drawing.Font("Verdana", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.nameLabel.Location = new System.Drawing.Point(1100, 190);
            this.nameLabel.Name = "nameLabel";
            this.nameLabel.Size = new System.Drawing.Size(40, 13);
            this.nameLabel.TabIndex = 11;
            this.nameLabel.Text = "Name";
            this.nameLabel.Click += new System.EventHandler(this.nameLabel_Click);
            // 
            // descLabel
            // 
            this.descLabel.AutoSize = true;
            this.descLabel.Font = new System.Drawing.Font("Verdana", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.descLabel.Location = new System.Drawing.Point(1100, 247);
            this.descLabel.Name = "descLabel";
            this.descLabel.Size = new System.Drawing.Size(71, 13);
            this.descLabel.TabIndex = 12;
            this.descLabel.Text = "Description";
            // 
            // priceLabel
            // 
            this.priceLabel.AutoSize = true;
            this.priceLabel.Font = new System.Drawing.Font("Verdana", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.priceLabel.Location = new System.Drawing.Point(1100, 351);
            this.priceLabel.Name = "priceLabel";
            this.priceLabel.Size = new System.Drawing.Size(35, 13);
            this.priceLabel.TabIndex = 13;
            this.priceLabel.Text = "Price";
            // 
            // productIdBox
            // 
            this.productIdBox.Location = new System.Drawing.Point(1208, 94);
            this.productIdBox.Name = "productIdBox";
            this.productIdBox.Size = new System.Drawing.Size(227, 20);
            this.productIdBox.TabIndex = 14;
            // 
            // idlabel
            // 
            this.idlabel.AutoSize = true;
            this.idlabel.Font = new System.Drawing.Font("Verdana", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.idlabel.Location = new System.Drawing.Point(1100, 94);
            this.idlabel.Name = "idlabel";
            this.idlabel.Size = new System.Drawing.Size(68, 13);
            this.idlabel.TabIndex = 15;
            this.idlabel.Text = "Product ID";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.SeaShell;
            this.ClientSize = new System.Drawing.Size(1582, 641);
            this.Controls.Add(this.idlabel);
            this.Controls.Add(this.productIdBox);
            this.Controls.Add(this.priceLabel);
            this.Controls.Add(this.descLabel);
            this.Controls.Add(this.nameLabel);
            this.Controls.Add(this.idTypeLabel);
            this.Controls.Add(this.priceBox);
            this.Controls.Add(this.descriptionBox);
            this.Controls.Add(this.nameBox);
            this.Controls.Add(this.idTypeBox);
            this.Controls.Add(this.updateButton);
            this.Controls.Add(this.deleteButton);
            this.Controls.Add(this.insertButton);
            this.Controls.Add(this.childTable);
            this.Controls.Add(this.parentTable);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.parentTable)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.childTable)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView parentTable;
        private System.Windows.Forms.DataGridView childTable;
        private System.Windows.Forms.Button insertButton;
        private System.Windows.Forms.Button deleteButton;
        private System.Windows.Forms.Button updateButton;
        private System.Windows.Forms.TextBox idTypeBox;
        private System.Windows.Forms.TextBox nameBox;
        private System.Windows.Forms.TextBox descriptionBox;
        private System.Windows.Forms.TextBox priceBox;
        private System.Windows.Forms.Label idTypeLabel;
        private System.Windows.Forms.Label nameLabel;
        private System.Windows.Forms.Label descLabel;
        private System.Windows.Forms.Label priceLabel;
        private System.Windows.Forms.TextBox productIdBox;
        private System.Windows.Forms.Label idlabel;
    }
}