package service;

import dao.DBConnection;

import java.sql.*;
import java.util.Scanner;

public class AdminService {
    Scanner sc = new Scanner(System.in);

    public void approveBooks() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM books WHERE approved=FALSE";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                System.out.println(id + ". " + rs.getString("title") + " by " + rs.getString("author"));
                System.out.print("Approve? (y/n): ");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("y")) {
                    String updateSQL = "UPDATE books SET approved=TRUE WHERE id=?";
                    PreparedStatement updatePS = con.prepareStatement(updateSQL);
                    updatePS.setInt(1, id);
                    updatePS.executeUpdate();
                    System.out.println("Approved.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewApprovedBooks() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM books WHERE approved=TRUE";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("Approved Books:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " + rs.getString("title") + " by " + rs.getString("author"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
