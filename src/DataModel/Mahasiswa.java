package DataModel;

public class Mahasiswa {
    private String nim;
    private String nama;
    private int umur;
    
    // Konstanta untuk kolom tabel
    public static final String[] kolom = {"NIM", "Nama", "Umur", "Aksi"};
    
    // Constructor
    public Mahasiswa() {}
    
    public Mahasiswa(String nim, String nama, int umur) {
        this.nim = nim;
        this.nama = nama;
        this.umur = umur;
    }
    
    // Getter dan Setter
    public String getNim() {
        return nim;
    }
    
    public void setNim(String nim) {
        this.nim = nim;
    }
    
    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public int getUmur() {
        return umur;
    }
    
    public void setUmur(int umur) {
        this.umur = umur;
    }
    
    @Override
    public String toString() {
        return "Mahasiswa{" +
                "nim='" + nim + '\'' +
                ", nama='" + nama + '\'' +
                ", umur=" + umur +
                '}';
    }
}