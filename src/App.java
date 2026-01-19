public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                System.out.println("=== SISTEM DATA MAHASISWA ===");
                System.out.println("Memulai aplikasi...");
                
                try {
                    InterfaceProject app = new InterfaceProject();
                    app.InterfaceUtama();
                    System.out.println("Aplikasi berjalan!");
                    System.out.println("Data awal: " + new MahasiswaDAO().getTotalMahasiswa() + " mahasiswa");
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}