import DataModel.Mahasiswa;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaDAO {
    
    private static List<Mahasiswa> dataMahasiswa = new ArrayList<>();
    
    static {
        dataMahasiswa.add(new Mahasiswa("20210001", "Budi Santoso", 20));
        dataMahasiswa.add(new Mahasiswa("20210002", "Siti Nurhaliza", 21));
        dataMahasiswa.add(new Mahasiswa("20210003", "Ahmad Rizki", 19));
        dataMahasiswa.add(new Mahasiswa("20210004", "Dewi Lestari", 22));
        dataMahasiswa.add(new Mahasiswa("20210005", "Rudi Hartono", 20));
    }
    
    public boolean insertMahasiswa(Mahasiswa mahasiswa) {
        try {
            for (Mahasiswa mhs : dataMahasiswa) {
                if (mhs.getNim().equals(mahasiswa.getNim())) {
                    return false;
                }
            }
            dataMahasiswa.add(mahasiswa);
            System.out.println("Data ditambahkan: " + mahasiswa);
            return true;
        } catch (Exception e) {
            System.out.println("Error insert mahasiswa: " + e.getMessage());
            return false;
        }
    }
    
    public List<Mahasiswa> getAllMahasiswa() {
        return new ArrayList<>(dataMahasiswa);
    }
    
    public List<Mahasiswa> searchMahasiswa(String keyword) {
        List<Mahasiswa> hasil = new ArrayList<>();
        keyword = keyword.toLowerCase();
        
        for (Mahasiswa mhs : dataMahasiswa) {
            if (mhs.getNim().toLowerCase().contains(keyword) || 
                mhs.getNama().toLowerCase().contains(keyword)) {
                hasil.add(mhs);
            }
        }
        return hasil;
    }
    
    public Mahasiswa getMahasiswaByNim(String nim) {
        for (Mahasiswa mhs : dataMahasiswa) {
            if (mhs.getNim().equals(nim)) {
                return mhs;
            }
        }
        return null;
    }
    
    public boolean updateMahasiswa(Mahasiswa mahasiswa) {
        try {
            for (int i = 0; i < dataMahasiswa.size(); i++) {
                if (dataMahasiswa.get(i).getNim().equals(mahasiswa.getNim())) {
                    dataMahasiswa.set(i, mahasiswa);
                    System.out.println("Data diupdate: " + mahasiswa);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error update mahasiswa: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteMahasiswa(String nim) {
        try {
            for (int i = 0; i < dataMahasiswa.size(); i++) {
                if (dataMahasiswa.get(i).getNim().equals(nim)) {
                    Mahasiswa removed = dataMahasiswa.remove(i);
                    System.out.println("Data dihapus: " + removed);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error delete mahasiswa: " + e.getMessage());
            return false;
        }
    }
    
    public int getTotalMahasiswa() {
        return dataMahasiswa.size();
    }
}