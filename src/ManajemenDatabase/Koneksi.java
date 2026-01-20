package ManajemenDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    static String username = "project1";
    static String password = "12345678";
    static String url = "jdbc:mysql://localhost:3306/mahasiswa";

    public static Connection getConn() {
        try {
            // ini adalah code untuk koneksi
            Connection connection = DriverManager.getConnection(url, username, password);
            // jika proses tidak bermasalah maka tampilkan koneksi berhasil
            System.out.println("Koneksi Berhasil...");
            return connection;

        } catch (SQLException e) {
            // jika dalam proses koneksi terjadi masalah, maka tampilkan koneksi gagal
            System.out.println("Koneksi Gagal..." + e.getMessage().toString());
        }
        return null;
    }
}
