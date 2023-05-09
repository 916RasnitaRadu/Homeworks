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

namespace A1
{
    public partial class Form1 : Form
    {
        private SqlConnection connection;
        private SqlDataAdapter dataAdapterParent;
        private SqlDataAdapter dataAdapterChild;
        private DataSet dataSet;
        private List<TextBox> textBoxes;

        public Form1()
        {
            this.connection = new SqlConnection(getConnectionString());
            this.dataAdapterParent = new SqlDataAdapter();
            this.dataAdapterChild = new SqlDataAdapter();
            this.textBoxes = new List<TextBox>();
            this.dataSet = new DataSet();
           
            InitializeComponent();
            parentTableLoad();
            loadTextBoxes();

            this.childTable.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void loadTextBoxes()
        {
            try
            {
                // We create a list of strings which contains the columnNames
                // The columnNames are splitted by ','
                List<string> columnNames = new List<string>(ConfigurationSettings.AppSettings["ColumnNames"].Split(','));

                // We fix 2 points for X and Y, in order to place the textBoxes
                int pointX = 30;
                int pointY = 40;

                this.panel1.Controls.Clear();

                foreach (string column in columnNames)
                {
                    // we create a new text box
                    TextBox textBox = new TextBox();
                    textBoxes.Add(textBox);
                    textBox.Text = column;
                    textBox.Name = column;
                    textBox.Location = new Point(pointX, pointY);
                    textBox.Visible = true;
                    textBox.Parent = this.panel1;
                    textBox.Width = 160;
                    panel1.Show();
                    // and we move the point
                    pointY += 50;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void parentTableLoad()
        {
            this.parentTable.SelectionMode = DataGridViewSelectionMode.FullRowSelect;

            // we take the select query and the parent table name from the app settings
            string select = ConfigurationSettings.AppSettings["selectParent"];
            string parentTableName = ConfigurationSettings.AppSettings["ParentTableName"];

            dataAdapterParent.SelectCommand = new SqlCommand(select, connection);

            this.dataSet.Clear();
            this.dataAdapterParent.Fill(dataSet, parentTableName);
            this.parentTable.DataSource = dataSet.Tables[parentTableName];
        }

        private static String getConnectionString()
        {
            return "Data Source= DESKTOP-8131C0C\\SQLEXPRESS; Initial Catalog= GymStore;Integrated Security=true;";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                // we take the insert command from the app settings
                string insertCmd = ConfigurationSettings.AppSettings["InsertQuery"];
                dataAdapterChild.InsertCommand = new SqlCommand(insertCmd,connection);

                // We take the childTable's name 
                string childTableName = ConfigurationSettings.AppSettings["ChildTableName"];
                List<string> columnNames = new List<string>(ConfigurationSettings.AppSettings["ColumnNames"].Split(','));

                // we go throguh all these columnNames
                // and then we parse the list of textBoxes in order to find the one whose name is the same as the columnName 
                foreach (string columnName in columnNames)
                {
                    foreach (TextBox tb in textBoxes)
                    {
                        if (tb.Name.Equals(columnName) && tb.Name != "id")
                        {
                            dataAdapterChild.InsertCommand.Parameters.AddWithValue("@"+columnName, tb.Text);
                        }
                    }
                }

                // we execute the insert command
                connection.Open();
                dataAdapterChild.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Inserted Successfully in the Database!", "",MessageBoxButtons.OK, MessageBoxIcon.Information);
                connection.Close();

                // we update the child table
                dataSet = new DataSet();
                dataAdapterChild.Fill(dataSet, childTableName);
                childTable.DataSource = this.dataSet.Tables[childTableName];

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
            DialogResult dialogResult;
            dialogResult = MessageBox.Show("Are you sure?\n You can't undo this action.", "Please confirm deletion", MessageBoxButtons.YesNo, MessageBoxIcon.Question);

            if (dialogResult.Equals(DialogResult.Yes))
            {
                try
                {
                    string deleteQuery = ConfigurationSettings.AppSettings["DeleteQuery"];


                    dataAdapterChild.DeleteCommand = new SqlCommand(deleteQuery);
                    dataAdapterChild.DeleteCommand.Connection = connection;

                    string childTableName = ConfigurationSettings.AppSettings["ChildTableName"];
                    List<string> columnNames = new List<string>(ConfigurationSettings.AppSettings["ColumnNames"].Split(','));

                    foreach (TextBox tb in textBoxes)
                    {
                        if (tb.Name == "id")
                        {
                            this.dataAdapterChild.DeleteCommand.Parameters.AddWithValue("@id", tb.Text);
                        }
                        break;
                    }

                    this.connection.Open();
                    dataAdapterChild.DeleteCommand.ExecuteNonQuery();
                    MessageBox.Show("Successfully deleted from database", "", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    this.connection.Close();

                    this.dataSet = new DataSet();
                    this.dataAdapterChild.Fill(dataSet, childTableName);
                    childTable.DataSource = dataSet.Tables[childTableName];

                    this.clearTextBoxes();
                }
                catch(Exception ex)
                {
                    MessageBox.Show(ex.Message, "", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    this.connection.Close();
                }
            }
        }

        private void updateButton_Click(object sender, EventArgs e)
        {
            try
            {
                // take the update command from app settings
                string updateCmd = ConfigurationSettings.AppSettings["UpdateQuery"];

                // create the update cmd and add its parameters
                dataAdapterChild.UpdateCommand = new SqlCommand(updateCmd, connection);

                string childTableName = ConfigurationSettings.AppSettings["ChildTableName"];
                List<string> columnNames = new List<string>(ConfigurationSettings.AppSettings["ColumnNames"].Split(','));

                foreach (string columnName in columnNames)
                {
                    foreach (TextBox tb in textBoxes)
                    {
                        if (tb.Name.Equals(columnName))
                        {
                            this.dataAdapterChild.UpdateCommand.Parameters.AddWithValue("@" + columnName, tb.Text);
                        }
                    }
                }


                // open connection and execute the command
                this.connection.Open();
                dataAdapterChild.UpdateCommand.ExecuteNonQuery();
                MessageBox.Show("Updated succesfull", "", MessageBoxButtons.OK, MessageBoxIcon.Information);
                this.connection.Close();

                // repopulate child table
                this.dataSet = new DataSet();
                this.dataAdapterChild.Fill(dataSet, childTableName);
                childTable.DataSource = dataSet.Tables[childTableName];
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
            // we clear the context of each textbox
            foreach (TextBox tb in textBoxes)
            {
                tb.Clear();
            }
        }

        private void parentTable_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            clearTextBoxes(); // we clear the text boxes

            // take the current selected row
            DataGridViewRow selectedParent = parentTable.SelectedRows[0];

            // we take the name of the foreign key
            string foreignKey = ConfigurationSettings.AppSettings["ForeignKey"];

            string selectChild = ConfigurationSettings.AppSettings["selectChild"];
            string childTableName = ConfigurationSettings.AppSettings["ChildTableName"];
            List<string> columnNames = new List<string>(ConfigurationSettings.AppSettings["ColumnNames"].Split(','));
           

            if (selectedParent.Cells[0].Value.ToString() != String.Empty)
            {
                // We go through our textBoxes and look for the one with the name as the foreign key, and we populate it with the value of the foreign key

                foreach (TextBox tb in textBoxes)
                {
                    if (tb.Name == foreignKey)
                    {
                        tb.Text = selectedParent.Cells[0].Value.ToString();
                    }
                }

                if (this.parentTable.SelectedRows.Count > 0)
                {
                    // we take the id of the library 
                    string id = selectedParent.Cells[0].Value.ToString();

                    // create a new sql command with the productTypeId parameter
                    dataAdapterChild.SelectCommand = new SqlCommand(selectChild + id, connection);

                    // create a new data set and repopulate the child table
                    dataSet = new DataSet();
                    this.dataAdapterChild.Fill(dataSet, childTableName);
                    this.childTable.DataSource = dataSet.Tables[childTableName];


                }
            }
            
            
        }

        private void childTable_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            int index = childTable.SelectedRows[0].Index;

            
            string childTableName = ConfigurationSettings.AppSettings["ChildTableName"];

            try
            {
                for (int i = 0; i < textBoxes.Count; i++)
                {
                    this.textBoxes[i].Text = dataSet.Tables[childTableName].Rows[index][i].ToString();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "", MessageBoxButtons.OK, MessageBoxIcon.Error);

            }




        }

    }
}
