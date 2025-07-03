import java.sql.*;
import java.util.Scanner;

public class EmployeeDBApp {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/employee_db";
        String username = "root"; // Change if you have a different MySQL username
        String password = "root";     // Add your MySQL password if set

        try {
            // This is correct for MySQL JDBC 5.1.49
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, username, password);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== Employee DB Menu ===");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addEmployee(conn, sc);
                        break;
                    case 2:
                        viewEmployees(conn);
                        break;
                    case 3:
                        updateEmployee(conn, sc);
                        break;
                    case 4:
                        deleteEmployee(conn, sc);
                        break;
                    case 5:
                        conn.close();
                        sc.close();
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error.");
            e.printStackTrace();
        }
    }

    static void addEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter employee name: ");
        String name = sc.nextLine();
        System.out.print("Enter employee role: ");
        String role = sc.nextLine();

        String sql = "INSERT INTO employees (name, role) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, role);
        stmt.executeUpdate();
        System.out.println("Employee added.");
    }

    static void viewEmployees(Connection conn) throws SQLException {
        String sql = "SELECT * FROM employees";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("ID\tName\t\tRole");
        while (rs.next()) {
            System.out.printf("%d\t%s\t\t%s\n", rs.getInt("id"), rs.getString("name"), rs.getString("role"));
        }
    }

    static void updateEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter employee ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        System.out.print("Enter new role: ");
        String role = sc.nextLine();

        String sql = "UPDATE employees SET name=?, role=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, role);
        stmt.setInt(3, id);
        stmt.executeUpdate();
        System.out.println("Employee updated.");
    }

    static void deleteEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter employee ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM employees WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        System.out.println("Employee deleted.");
    }
}



