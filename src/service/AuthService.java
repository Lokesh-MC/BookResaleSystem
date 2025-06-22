package service;

import dao.DBConnection;
import model.User;

import java.sql.*;
import java.util.Scanner;

public class AuthService {
    Scanner sc = new Scanner(System.in);
    public User login() {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("role"));
            } else {
                System.out.println("Invalid credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void register() {
        System.out.print("Choose Username: ");
        String username = sc.nextLine();
        System.out.print("Choose Password: ");
        String password = sc.nextLine();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, 'user')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
