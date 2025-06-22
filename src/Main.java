import model.User;
import service.AuthService;
import service.BookService;
import service.AdminService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService authService = new AuthService();
        BookService bookService = new BookService();
        AdminService adminService = new AdminService();

        while (true) {
            System.out.println("1. Register\n2. Login\n3. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) authService.register();
            else if (choice == 2) {
                User user = authService.login();
                if (user == null) continue;

                if (user.getRole().equals("admin")) {
                    while (true) {
                        System.out.println("1. Approve Books\n2. View Approved Books\n3. Logout");
                        int c = sc.nextInt();
                        sc.nextLine();
                        if (c == 1) adminService.approveBooks();
                        else if (c == 2) adminService.viewApprovedBooks();
                        else break;
                    }
                } else {
                    while (true) {
                        System.out.println("1. Request Book\n2. Buy Book\n3. Logout");
                        int c = sc.nextInt();
                        sc.nextLine();
                        if (c == 1) bookService.requestBook(user);
                        else if (c == 2) bookService.buyBook();
                        else break;
                    }
                }
            } else break;
        }
    }
}
