using System;
using System.Collections.Generic;
using RoomReservation.Model;
using MySql.Data.MySqlClient;
using System.Net;
using System.Net.Sockets;

namespace RoomReservation.DataAbstractionLayer
{
    public class DBManager
    {
        private string connectionString = "server=localhost;uid=root;pwd=;database=reservationsdb";
        MySqlConnection conn;

        public List<User> GetUsers()
        {
            List<User> userList = new List<User>();

            try
            {
                conn = new MySqlConnection(connectionString);
                conn.Open();

                MySqlCommand cmd = new MySqlCommand
                {
                    Connection = conn,
                    CommandText = "SELECT * FROM users"
                };
                MySqlDataReader dataReader = cmd.ExecuteReader();

                while (dataReader.Read())
                {
                    User user = new()
                    {
                        id = dataReader.GetInt32("id"),
                        username = dataReader.GetString("username"),
                        password = dataReader.GetString("password")
                    };
                    userList.Add(user);
                }
                dataReader.Close();
                conn.Close();
            }
            catch (MySqlException ex)
            {
                conn.Close();
                Console.WriteLine(ex.Message);
            }
            return userList;
        }

        public User getUserByUsername(string username)
        {
            if (username == null)
            {
                return null;
            }
          
            List<User> users = GetUsers();
            foreach (User user in users)
            {
                if (user.username == username)
                {
                    return user;
                }
            }

            return null;
        }

        public Reservation addReservation(Reservation reservation)
        {
            try
            {
                conn = new MySqlConnection
                {
                    ConnectionString = connectionString
                };
                conn.Open();

                MySqlCommand cmd = new MySqlCommand
                {
                    Connection = conn,
                    CommandText = "INSERT INTO Reservations (`roomID`, `check_in`, `check_out`) VALUES('" + reservation.roomID + "','" + reservation.check_in + "','" + reservation.check_out + "')"
                };
                cmd.ExecuteNonQuery();
                conn.Close();
                return reservation;
            }
            catch (MySqlException ex)
            {
                conn.Close();
                Console.WriteLine(ex.Message);
            }
            return null;
        }

        public void deleteReservation(int id)
        {
            try
            {
                conn = new MySqlConnection
                {
                    ConnectionString = connectionString
                };
                conn.Open();

                MySqlCommand cmd = new MySqlCommand
                {
                    Connection = conn,
                    CommandText = "DELETE FROM Reservations WHERE id = " + id
                };
                cmd.ExecuteNonQuery();
                conn.Close();
            
            }
            catch (MySqlException ex)
            {
                conn.Close();
                Console.WriteLine(ex.Message);
            }
        }
    }
}
