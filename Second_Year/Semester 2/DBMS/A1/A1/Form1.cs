using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace A1
{
    public partial class Form1 : Form
    {
        private SqlConnection connection;
        private SqlDataAdapter dataAdapter;
        private DataSet dataSet;

        public Form1()
        {
            this.connection = new SqlConnection(getConnectionString());
            this.dataAdapter = new SqlDataAdapter();
            this.dataSet = new DataSet();
           
            InitializeComponent();
            this.childTable.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            this.productIdBox.ReadOnly = true;
            this.productIdBox.Enabled = false;
            parentTableLoad();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void parentTableLoad()
        {
            this.parentTable.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dataAdapter.SelectCommand = new SqlCommand("SELECT * FROM Product_Type", connection);
            this.dataSet.Clear();
            this.dataAdapter.Fill(dataSet, "Product_Type");

            this.parentTable.DataSource = dataSet.Tables["Product_Type"];
        }

        private static String getConnectionString()
        {
            return "Data Source= DESKTOP-8131C0C\\SQLEXPRESS; Initial Catalog= GymStore;Integrated Security=true;";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                // we create the insert command
                dataAdapter.InsertCommand = new SqlCommand("INSERT INTO Product(id_type, product_name, prod_desc, price) VALUES (@t, @n, @d, @p)",connection);

                // we add the parameters of the cmd
                dataAdapter.InsertCommand.Parameters.Add("@t", SqlDbType.Int).Value = Int32.Parse(idTypeBox.Text);
                dataAdapter.InsertCommand.Parameters.Add("@n", SqlDbType.VarChar).Value = nameBox.Text;
                dataAdapter.InsertCommand.Parameters.Add("@d", SqlDbType.VarChar).Value = descriptionBox.Text;
                dataAdapter.InsertCommand.Parameters.Add("@p", SqlDbType.Float).Value = float.Parse(priceBox.Text);

                connection.Open();
                dataAdapter.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Inserted Successfully in the Database!", "",MessageBoxButtons.OK, MessageBoxIcon.Information);
                connection.Close();

                dataSet = new DataSet();
                dataAdapter.Fill(dataSet, "Product");
                childTable.DataSource = this.dataSet.Tables["Product"];

                this.clearTextBoxes();
            }
            catch(Exception ex)
            {
               
                MessageBox.Show(ex.Message,"",MessageBoxButtons.OK, MessageBoxIcon.Error);
                connection.Close();
            }
        }

        private void deleteButton_Click(object sender, EventArgs e)
        {
            int index = this.childTable.SelectedRows[0].Index;
            DialogResult dialogResult;
            dialogResult = MessageBox.Show("Are you sure?\n You can't undo this action.", "Please confirm deletion", MessageBoxButtons.YesNo, MessageBoxIcon.Question);

            if (dialogResult.Equals(DialogResult.Yes))
            {
                dataAdapter.DeleteCommand = new SqlCommand("DELETE FROM Product WHERE id=@d");
                dataAdapter.DeleteCommand.Parameters.Add("@d", SqlDbType.Int).Value = dataSet.Tables["Product"].Rows[index][0];
                dataAdapter.DeleteCommand.Connection = connection;
                this.connection.Open();
                dataAdapter.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show("Successfully deleted from database", "", MessageBoxButtons.OK, MessageBoxIcon.Information);
                this.connection.Close();

                this.dataSet = new DataSet();
                this.dataAdapter.Fill(dataSet, "Product");
                childTable.DataSource = dataSet.Tables["Product"];

                this.clearTextBoxes();
            }
        }

        private void updateButton_Click(object sender, EventArgs e)
        {
            try
            {
                // take the index of the selected row
                int index = childTable.SelectedRows[0].Index;

                // create the update cmd and add its parameters
                dataAdapter.UpdateCommand = new SqlCommand("UPDATE Product SET id_type = @t, product_name = @n, prod_desc = @d, price = @p WHERE id = @id", connection);

                dataAdapter.UpdateCommand.Parameters.Add("@t", SqlDbType.Int).Value = Int32.Parse(idTypeBox.Text);
                dataAdapter.UpdateCommand.Parameters.Add("@n", SqlDbType.VarChar).Value = nameBox.Text;
                dataAdapter.UpdateCommand.Parameters.Add("@d", SqlDbType.VarChar).Value = descriptionBox.Text;
                dataAdapter.UpdateCommand.Parameters.Add("@p", SqlDbType.Float).Value = float.Parse(priceBox.Text);

                dataAdapter.UpdateCommand.Parameters.Add("@id", SqlDbType.Int).Value = dataSet.Tables["Product"].Rows[index][0];

                // open connection and execute the command
                this.connection.Open();
                dataAdapter.UpdateCommand.ExecuteNonQuery();
                MessageBox.Show("Updated succesfull", "", MessageBoxButtons.OK, MessageBoxIcon.Information);
                this.connection.Close();

                // repopulate child table
                this.dataSet = new DataSet();
                this.dataAdapter.Fill(dataSet, "Product");
                childTable.DataSource = dataSet.Tables["Product"];
                this.clearTextBoxes();
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message, "", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.connection.Close();
            }
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void nameLabel_Click(object sender, EventArgs e)
        {

        }

        private void clearTextBoxes()
        {
            nameBox.Clear();
            idTypeBox.Clear();
            descriptionBox.Clear();
            priceBox.Clear();
        }

        private void parentTable_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            clearTextBoxes(); // we clear the text boxes

            // take the current selected row
            DataGridViewRow selectedParent = parentTable.SelectedRows[0];
            if (selectedParent.Cells[0].Value.ToString() != String.Empty)
            { 
                this.idTypeBox.Text = selectedParent.Cells[0].Value.ToString();


                if (this.parentTable.SelectedRows.Count > 0)
                {
                    // we take the id of the library 
                    int productTypeId = Convert.ToInt32(selectedParent.Cells[0].Value);
                    // create a new sql command with the productTypeId parameter
                    dataAdapter.SelectCommand = new SqlCommand("SELECT * FROM Product WHERE id_type = @id", connection);
                    dataAdapter.SelectCommand.Parameters.AddWithValue("@id", productTypeId);

                    // create a new data set and repopulate the child table
                    dataSet = new DataSet();
                    this.dataAdapter.Fill(dataSet, "Product");
                    this.childTable.DataSource = dataSet.Tables["Product"];


                }
            }
            // we write in the textbox the id of the product type
            
        }

        private void childTable_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            int index = childTable.SelectedRows[0].Index;
            if (index <= childTable.SelectedRows.Count)
            {
                this.productIdBox.Text = dataSet.Tables["Product"].Rows[index][0].ToString();
                this.idTypeBox.Text = dataSet.Tables["Product"].Rows[index][1].ToString();
                this.nameBox.Text = dataSet.Tables["Product"].Rows[index][2].ToString();
                this.descriptionBox.Text = dataSet.Tables["Product"].Rows[index][3].ToString();
                this.priceBox.Text = dataSet.Tables["Product"].Rows[index][4].ToString();
            }
           
        }

    }
}
