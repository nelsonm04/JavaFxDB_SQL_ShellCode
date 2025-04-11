package org.example.javafxdb_sql_shellcode.db;

import org.example.javafxdb_sql_shellcode.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnDbOps {
    final String MYSQL_SERVER_URL = "jdbc:mysql://csc311mojica04.mysql.database.azure.com/";
    final String DB_URL = MYSQL_SERVER_URL + "DBname";
    final String USERNAME = "mojin";
    final String PASSWORD = "FARM123$";

    public boolean connectToDatabase() {
        boolean hasRegisteredUsers = false;

        try {
            // Create database if not exists
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS DBname");
            statement.close();
            conn.close();

            // Create users table
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sqlUsers = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(200) NOT NULL," +
                    "email VARCHAR(200) NOT NULL UNIQUE," +
                    "phone VARCHAR(200)," +
                    "address VARCHAR(200)," +
                    "password VARCHAR(200) NOT NULL)";
            statement.executeUpdate(sqlUsers);

            // Create people table
            String sqlPeople = "CREATE TABLE IF NOT EXISTS people (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "first_name VARCHAR(100)," +
                    "last_name VARCHAR(100)," +
                    "dept VARCHAR(100)," +
                    "major VARCHAR(100))";
            statement.executeUpdate(sqlPeople);

            // Check for users in users table
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");
            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasRegisteredUsers = true;
                }
            }

            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasRegisteredUsers;
    }

    public void queryUserByName(String name) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM users WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Address: " + address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listAllUsers() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Address: " + address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(String name, String email, String phone, String address, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO users (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setString(5, password);
            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("A new user was inserted successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ======= NEW METHODS FOR GUI + PERSON =======

    public void insertPerson(Person p) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO people (first_name, last_name, dept, major) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getFirstName());
            stmt.setString(2, p.getLastName());
            stmt.setString(3, p.getDept());
            stmt.setString(4, p.getMajor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getAllPeople() {
        List<Person> people = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM people";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Person p = new Person(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("dept"),
                        rs.getString("major")
                );
                people.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public void deletePersonById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM people WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
