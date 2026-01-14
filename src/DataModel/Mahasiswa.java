package DataModel;

// Data model ini digunakan untuk melakukan akses terhadap data
public class Mahasiswa {
    //attribut dari data model
    private String nim,nama;
    private int umur;
    private String aksi;
    //ini digunakan untuk field table
    public static String[] kolom={"Nim", "Nama", "Umur","Aksi"};

    //contructor dari data mahasiswa
    public Mahasiswa(String nim, String nama, int umur){
        this.nim=nim;
        this.nama=nama;
        this.umur=umur;
        this.aksi="kosong";
    }
    //getter 
    public String getNama() {
        return nama;
    }
    public String getNim() {
        return nim;
    }
    public int getUmur() {
        return umur;
    }
    public String getAksi() {
        return aksi;
    }
    
}
