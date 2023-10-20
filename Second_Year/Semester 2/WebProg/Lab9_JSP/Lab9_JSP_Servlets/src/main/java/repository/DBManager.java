package repository;

import domain.Pair;
import domain.URL;
import domain.User;


import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    public DBManager() {
        try {
            Class.forName("org.postgresql.Driver"); // loads the postgresql jdbc driver in memory
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUserFromName(String username) {
        User user = null;
        String statement = "SELECT * FROM users WHERE username='" + username + "'";

        try (var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jsp", "postgres", "postgres");
             var preparedStatement = connection.prepareStatement(statement);
             var result = preparedStatement.executeQuery()) {
            if (result.next()) {
                user = new User(result.getInt("id"), result.getString("username"), result.getString("password"));
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return user;
    }

    public User authenticate(String username, String password) {
        User user =getUserFromName(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<URL> getUrlsOfUser(String username) {
        List<URL> urls = new ArrayList<>();
        String statement = "SELECT * FROM urls join users on urls.user_id = users.id WHERE users.username = '" + username +"'";
        try(var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jsp", "postgres", "postgres");
            var preparedStatement = connection.prepareStatement(statement);
            var result = preparedStatement.executeQuery()) {
            while (result.next()) {
                URL newUrl = new URL(result.getInt("id"),result.getInt("user_id"), result.getString("url"));
                urls.add(newUrl);
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return urls;
    }

    public void deleteUrl(int id) {
        String statement = "DELETE FROM urls where id = '" + id + "'";
        try (var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jsp", "postgres", "postgres");
            var preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.execute();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

    public void addUrl(int userId, String url) {
        String statement = "INSERT INTO urls(user_id, url) values (" + userId + ", '" + url + "')";
        try (var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jsp", "postgres", "postgres");
             var preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.execute();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<Pair<String,Integer>> getPopularUrls(int number) {
        String statement = "SELECT urls.url as url, count(*) as cnt from urls group by urls.url order by count(*) desc limit " + number;
        List<Pair<String,Integer>> urls = new ArrayList<>();
        try(var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jsp", "postgres", "postgres");
            var preparedStatement = connection.prepareStatement(statement);
            var result = preparedStatement.executeQuery()) {
            while (result.next()) {
                urls.add(new Pair<>(result.getString("url"), result.getInt("cnt")));
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return urls;
    }
}
