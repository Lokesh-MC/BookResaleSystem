package service;

import dao.DBConnection;
import model.User;

import java.sql.*;
import java.util.Scanner;

public class BookService {
    Scanner sc = new Scanner(System.in);

    public void requestBook(User user) {
        System.out.print("Enter title: ");
        String title = sc.nextLine();
        System.out.print("Enter author: ");
        String author = sc.nextLine();
        System.out.print("Enter price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO books(title, author, price, approved, added_by) VALUES (?, ?, ?, FALSE, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setDouble(3, price);
            ps.setInt(4, user.getId());
            ps.executeUpdate();
            System.out.println("Book requested for admin approval.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buyBook() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM books WHERE approved=TRUE";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " + rs.getString("title") + " - Rs." + rs.getDouble("price"));
            }

            System.out.print("Enter Book ID to buy: ");
            int bookId = sc.nextInt();
            sc.nextLine();

            String deleteSQL = "DELETE FROM books WHERE id=?";
            PreparedStatement deletePS = con.prepareStatement(deleteSQL);
            deletePS.setInt(1, bookId);
            deletePS.executeUpdate();
            System.out.println("Book purchased successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
