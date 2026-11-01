
import java.sql.*;
import java.util.Scanner;

public class Main {

    public static boolean isPrime(int num) {
        if (num < 2) return false;

        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0)
                return false;
        }

        return true;
    }

    public static int nthPrime(int n) {
        int count = 0;
        int num = 1;

        while (count < n) {
            num++;

            if (isPrime(num))
                count++;
        }

        return num;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter n: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Invalid input!");
            return;
        }

        int prime = nthPrime(n);

        System.out.println("Nth prime: " + prime);

        try {
            String url = "jdbc:mysql://localhost:3306/prime_db";
            String user = "root";
            String password = "1177@";

            Connection con = DriverManager.getConnection(
                    url, user, password
            );

            String sql = "INSERT INTO prime VALUES (?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, n);
            ps.setInt(2, prime);

            ps.executeUpdate();

            System.out.println("Data inserted successfully!");

            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

        sc.close();
    }
}
