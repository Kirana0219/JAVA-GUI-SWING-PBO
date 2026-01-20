public class App {
    public static void main(String[] args) {
        System.out.println("=== SISTEM DATA MAHASISWA ===");
        System.out.println("Memulai aplikasi...");
        
        try {
            InterfaceProject app = new InterfaceProject();
            app.InterfaceUtama();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage().toString());
        }
    }
}