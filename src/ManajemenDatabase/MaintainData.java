package ManajemenDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DataModel.Mahasiswa;

public class MaintainData {
    public static void simpanData(Mahasiswa sw) {
        String query = "INSERT INTO `datamhs`(`nim`, `nama`, `umur`) VALUES (?,?,?)";
        try (
                // untuk memastikan koneksi masih ada
                Connection conn = Koneksi.getConn();
                // mempersiapkan value untuk rawquery params
                PreparedStatement pre = conn.prepareStatement(query);) {
            // digunakan untuk mengisikan nilai params
            // angka 1-3 itu urutan kemunculan params
            pre.setString(1, sw.getNim());
            pre.setString(2, sw.getNama());
            pre.setInt(3, sw.getUmur());
            // eksekusi query yang telah berisi value
            pre.executeUpdate();
            // tampilkan proses simpan
            System.out.println("Proses Simpan Data Berhasil!" + " {NIM: " + sw.getNim() + "; Nama: " +sw.getNama() + "; Umur: " + sw.getUmur() + "}" );
        } catch (Exception e) {
            // tampilkan jika terjadi kegagalan saat proses statement pada body try
            System.out.println("Error: " + e.getMessage().toString());
        }
    }

    public static ArrayList<Mahasiswa> getData(String Mode, String key) {
        String query = "";
        ArrayList<Mahasiswa> lsData;
        if (Mode.equals("filter")) {
            query = "SELECT `nim`, `nama`, `umur` FROM `datamhs` WHERE nim=?";
        } else {
            query = "SELECT `nim`, `nama`, `umur` FROM `datamhs`";
        }

        try (
                Connection conn = Koneksi.getConn();
                PreparedStatement pre = conn.prepareStatement(query);) {
            if (Mode.equals("filter")) {
                pre.setString(1, key);
            }
            try (
                    ResultSet rs = pre.executeQuery();) {

                lsData = new ArrayList<Mahasiswa>();
                Mahasiswa sw = null;
                while (rs.next()) {
                    sw = new Mahasiswa();
                    sw.setNim(rs.getString("nim"));
                    sw.setNama(rs.getString("nama"));
                    sw.setUmur(rs.getByte("umur"));
                    lsData.add(sw);
                }
            }
            return lsData;
        } catch (Exception e) {
            return new ArrayList<Mahasiswa>();
        }
    }

    public static void updateData(Mahasiswa sw) {
        // query SQL update dengan memanfaatkan konsep params '?'
        String query = "UPDATE `datamhs` SET `nama`=?,`umur`=? WHERE nim=? ";
        try (
                // untuk melakukan koneksi database
                Connection conn = Koneksi.getConn();
                // untuk mempersiapkan nilai params ?
                PreparedStatement pre = conn.prepareStatement(query);) {
            // input nilai params berdasarkan urutan kemunculan
            pre.setString(1, sw.getNama());
            pre.setInt(2, sw.getUmur());
            pre.setString(3, sw.getNim());
            // eksekusi object pre yagn berisi query nilai params
            pre.executeUpdate();
            // tampilkan proses update data berhasil
            System.out.println("Proses Update Berhasil!" + " {Nama: " + sw.getNama() + "; Umur: " + sw.getUmur() + "; Nim: " + sw.getNim() + "}");
        } catch (Exception e) {
            // tampilkan error jika block try mengalami kegagalan
            System.out.println("Error: " + e.getMessage().toString());
            // tampilkan proses update data gagal
            System.out.println("Proses Update Gagal");
        }
    }

    public static void deleteData(Mahasiswa sw) {
        String query = "DELETE FROM `datamhs` WHERE nim=?";
        try (
                Connection conn = Koneksi.getConn();
                PreparedStatement pre = conn.prepareStatement(query);) {
            pre.setString(1, sw.getNim());
            pre.executeUpdate();
            System.out.println("Proses Delete Berhasil!" + " {NIM: " + sw.getNim() + "}");
        } catch (Exception e) {
            System.out.println("Proses Delete Gagal");
        }
    }
}
