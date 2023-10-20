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
using System.Configuration;
using System.Linq.Expressions;
using System.Data.Common;

namespace Exam
{
    public partial class Form1 : Form
    {
        private SqlConnection connection;
        private SqlDataAdapter dataAdapter;
        private DataSet dataSet;
        private List<TextBox> textBoxes;

        public Form1()
        {
            this.connection = new SqlConnection(getConnectionString());
            this.dataAdapter = new SqlDataAdapter();
            this.dataSet = new DataSet();
            InitializeComponent();
            this.dgvClients.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            this.clientIdBox.ReadOnly = true;
            this.clientIdBox.Enabled = false;
            bakeryTableLoad();
        }

        private void bakeryTableLoad()
        {
            this.dgvFavouriteBakery.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dataAdapter.SelectCommand = new SqlCommand("SELECT * from FavouriteBakery", connection);
            this.dataSet.Clear();
            this.dataAdapter.Fill(dataSet, "FavouriteBakery");
            this.dgvFavouriteBakery.DataSource = dataSet.Tables["FavouriteBakery"];
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }
        
        private void clearTextBoxes()
        {
            clientNameBox.Clear();
            clientSurnameBox.Clear();
            clientGenderBox.Clear();
            ageBox.Clear();
            favBakeryBox.Clear();
        }

        private static String getConnectionString()
        {
            return "Data Source= DESKTOP-8131C0C\\SQLEXPRESS; Initial Catalog= Exam;Integrated Security=true;";
        }

        private void dataGridView2_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void dgvFavouriteBakery_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            clearTextBoxes();

            DataGridViewRow selectedParent = dgvFavouriteBakery.SelectedRows[0];
            if (selectedParent.Cells[0].Value.ToString() != String.Empty)
            {
                this.favBakeryBox.Text = selectedParent.Cells[0].Value.ToString();

                if (this.dgvFavouriteBakery.SelectedRows.Count > 0)
                {
                    int favBakeryId = Convert.ToInt32(selectedParent.Cells[0].Value);

                    dataAdapter.SelectCommand = new SqlCommand("SELECT * FROM Client WHERE bakeryId = @id", connection);
                    dataAdapter.SelectCommand.Parameters.AddWithValue("@id", favBakeryId);

                    dataSet = new DataSet();
                    this.dataAdapter.Fill(dataSet, "Client");
                    this.dgvClients.DataSource = dataSet.Tables["Client"];

                }

            }
        }

        private void dgvClients_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            int index = dgvClients.SelectedRows[0].Index;
            if (index <= dgvClients.SelectedRows.Count) // if the selected row is not emtpy
            {
                this.clientIdBox.Text = dataSet.Tables["Client"].Rows[index][0].ToString();
                this.clientNameBox.Text = dataSet.Tables["Client"].Rows[index][1].ToString();
                this.clientSurnameBox.Text = dataSet.Tables["Client"].Rows[index][2].ToString();
                this.clientGenderBox.Text = dataSet.Tables["Client"].Rows[index][3].ToString();
                this.ageBox.Text = dataSet.Tables["Client"].Rows[index][4].ToString();
                this.favBakeryBox.Text = dataSet.Tables["Client"].Rows[index][5].ToString();

            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                dataAdapter.InsertCommand = new SqlCommand("INSERT INTO Client(name, surname, gender, age, bakeryId) VALUES (@n, @s, @g, @a, @b)", connection);
                dataAdapter.InsertCommand.Parameters.Add("@n", SqlDbType.VarChar).Value = this.clientNameBox.Text;
                dataAdapter.InsertCommand.Parameters.Add("@s", SqlDbType.VarChar).Value = this.clientSurnameBox.Text;
                dataAdapter.InsertCommand.Parameters.Add("@g", SqlDbType.VarChar).Value = this.clientGenderBox.Text;
                dataAdapter.InsertCommand.Parameters.Add("@a", SqlDbType.Int).Value = Int32.Parse(ageBox.Text);
                dataAdapter.InsertCommand.Parameters.Add("@b", SqlDbType.Int).Value = Int32.Parse(favBakeryBox.Text);

                connection.Open();
                dataAdapter.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Inserted Successfully in the Database!", "", MessageBoxButtons.OK, MessageBoxIcon.Information);
                connection.Close();

                dataSet = new DataSet();
                dataAdapter.Fill(dataSet, "Client");
                dgvClients.DataSource = this.dataSet.Tables["Client"];

                this.clearTextBoxes();


            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "", MessageBoxButtons.OK, MessageBoxIcon.Error);
                connection.Close();
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            int index = this.dgvClients.SelectedRows[0].Index;
            DialogResult dialogResult;
            dialogResult = MessageBox.Show("Are you sure?\n You can't undo this action.", "Please confirm deletion", MessageBoxButtons.YesNo, MessageBoxIcon.Question);

            if (dialogResult.Equals(DialogResult.Yes))
            {
                dataAdapter.DeleteCommand = new SqlCommand("DELETE FROM Client WHERE id=@d");
                dataAdapter.DeleteCommand.Parameters.Add("@d", SqlDbType.Int).Value = dataSet.Tables["Client"].Rows[index][0];
                dataAdapter.DeleteCommand.Connection = connection;
                this.connection.Open();
                dataAdapter.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show("Successfully deleted from database", "", MessageBoxButtons.OK, MessageBoxIcon.Information);
                this.connection.Close();

                this.dataSet = new DataSet();
                this.dataAdapter.Fill(dataSet, "Client");
                dgvClients.DataSource = dataSet.Tables["Client"];

                this.clearTextBoxes();
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            try
            {
                // take the index of the selected row
                int index = dgvClients.SelectedRows[0].Index;

                // create the update cmd and add its parameters
                dataAdapter.UpdateCommand = new SqlCommand("UPDATE Client SET name = @n, surname = @s, gender = @g, age = @a, bakeryId = @b WHERE id = @id", connection);

                dataAdapter.UpdateCommand.Parameters.Add("@n", SqlDbType.VarChar).Value = this.clientNameBox.Text;
                dataAdapter.UpdateCommand.Parameters.Add("@s", SqlDbType.VarChar).Value = this.clientSurnameBox.Text;
                dataAdapter.UpdateCommand.Parameters.Add("@g", SqlDbType.VarChar).Value = this.clientGenderBox.Text;
                dataAdapter.UpdateCommand.Parameters.Add("@a", SqlDbType.Int).Value = Int32.Parse(ageBox.Text);
                dataAdapter.UpdateCommand.Parameters.Add("@b", SqlDbType.Int).Value = Int32.Parse(favBakeryBox.Text);

                dataAdapter.UpdateCommand.Parameters.Add("@id", SqlDbType.Int).Value = dataSet.Tables["Client"].Rows[index][0];

                // open connection and execute the command
                this.connection.Open();
                dataAdapter.UpdateCommand.ExecuteNonQuery();
                MessageBox.Show("Updated succesfull", "", MessageBoxButtons.OK, MessageBoxIcon.Information);
                this.connection.Close();

                // repopulate child table
                this.dataSet = new DataSet();
                this.dataAdapter.Fill(dataSet, "Client");
                dgvClients.DataSource = dataSet.Tables["Client"];
                this.clearTextBoxes();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.connection.Close();
            }
        }
    }
}