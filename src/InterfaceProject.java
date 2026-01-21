import DataModel.Mahasiswa;
import ManajemenDatabase.MaintainData;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class InterfaceProject {
    private CardLayout cardLayout;
    private JPanel panelCenter;
    private DefaultTableModel dmTableModel;
    private JTable jTable;

    //Untuk memberikan warrna pada apps, dimana seperti root pada css jadi tinggal dipanggil saja 
    private final Color COLOR_PURPLE = new Color(128, 0, 128);
    private final Color COLOR_LIGHT_PURPLE = new Color(230, 230, 250);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_LIGHT_GRAY = new Color(245, 245, 245);

    private JTextField inputNimField, inputNamaField, inputUmurField;
    private JTextField editNimField, editNamaField, editUmurField, searchEditField;

    /* ========================= Sidebar & Navigasi ========================= */

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBackground(COLOR_PURPLE);

        //judul menu pada sidebar
        JLabel headerLabel = new JLabel("MENU");
        //mengatur font dan ukurannya 
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        //memberikan warna putih pada teks header
        headerLabel.setForeground(Color.WHITE);
        //label agar berada ditengah
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //memberikan padding bawah pada label agar ada jarak antara label dengan tombol 
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        //panel tombol navigasi
        JPanel buttonPanel = new JPanel();
        //agar button posisi layoutnya kebawah
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        //memberikan warna latar belakang pada background
        buttonPanel.setBackground(COLOR_PURPLE);
        //memberikan padding atas dan bawah pada panel tombol 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        //membuat tombol navigasi
        JButton[] buttons = {
            createSidebarButton("Beranda"),
            createSidebarButton("Input Data"),
            createSidebarButton("View Data"),
            createSidebarButton("Edit Data")
        };

        //listener untuk berpindah panel jika tombol di klik
        buttons[0].addActionListener(e -> showBeranda());
        buttons[1].addActionListener(e -> showInputData());
        buttons[2].addActionListener(e -> showViewData());
        buttons[3].addActionListener(e -> showEditData());

        sidebar.add(headerLabel);
        //memberikan jarak antara header dengan buttonnya 
        sidebar.add(Box.createVerticalStrut(10));

        for (JButton button : buttons) {
            buttonPanel.add(button);
            //memberikan jarak antar buttonnya
            buttonPanel.add(Box.createVerticalStrut(8));
        }

        //tambhkan panel button
        sidebar.add(buttonPanel);
        //memberikan ruang kosong pada bawah button sehingga ada jarak antara panel button dengan footer 
        sidebar.add(Box.createVerticalGlue());

        //footer
        JLabel footerLabel = new JLabel("© 2026 Kiyuti | All Right Reserved");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        footerLabel.setForeground(new Color(200, 200, 200));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        sidebar.add(footerLabel);

        return sidebar;
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        //memberikan ukuran maksimum button
        button.setMaximumSize(new Dimension(180, 45));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        //memberikan warna latar belakang tombol
        button.setBackground(COLOR_WHITE);
        //memberikan warna pada teks
        button.setForeground(COLOR_PURPLE);
        //memberikan border luar
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_WHITE, 1),
            //memberikan padding agar teks berada di tengah tombol
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        //set kursornya agar ketika mengarah ke button berbentuk tangan
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            //ketika mouse masuk atau berada di button
            public void mouseEntered(MouseEvent e) {
                button.setBackground(COLOR_LIGHT_PURPLE);
                button.setForeground(Color.BLACK);
            }
            @Override
            //ketika mouse diluar dari button
            public void mouseExited(MouseEvent e) {
                button.setBackground(COLOR_WHITE);
                button.setForeground(COLOR_PURPLE);
            }
        });

        return button;
    }

    private void showBeranda() {
        cardLayout.show(panelCenter, "Beranda");
    }
    private void showInputData() {
        cardLayout.show(panelCenter, "Input");
        inputNimField.requestFocus();
    }
    private void showViewData() {
        cardLayout.show(panelCenter, "View");
        refreshTableData();
    }
    private void showEditData() {
        cardLayout.show(panelCenter, "Edit");
        searchEditField.requestFocus();
    }

    /* ========================= Panel Beranda ========================= */

    //membuat panel branda
    private JPanel createBerandaPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        //memberikan latar belakang warnanya putih
        panel.setBackground(COLOR_WHITE);


        JPanel mainPanel = new JPanel();
        //konten panel utama itu akan kebawah
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(COLOR_WHITE);
        //memberikan padding pada panel sehingga berada ditengah atau berada di posisi yang diinginkan
        mainPanel.setBorder(BorderFactory.createEmptyBorder(150, 50, 50, 50));

        //judul utama tabel
        JLabel titleLabel = new JLabel("SELAMAT DATANG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(COLOR_PURPLE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //sub judul tabel
        JLabel subTitleLabel = new JLabel("Sistem Manajemen Data Mahasiswa");
        subTitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subTitleLabel.setForeground(Color.DARK_GRAY);
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel fitur dengan GridLayout (1 baris, 3 kolom, jarak horizontal 20px)
        JPanel featuresPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        featuresPanel.setMaximumSize(new Dimension(800, 150));
        featuresPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //agar latar belakang panel berwarna transparan sehingga warna latar belakang itu keliatan 
        featuresPanel.setOpaque(false);

        //membeerikan detail informasi menngenai fitur didalamnya 
        String[][] features = {
            //fitur[0], fitur [1]
            {"Input Data", "Tambah data mahasiswa baru"}, 
            {"View Data", "Lihat dan kelola data mahasiswa"},
            {"Edit Data", "Ubah data mahasiswa"}
        };

        //loop untuk membuat card pada setiap fiturnya
        for (String[] feature : features) {
            featuresPanel.add(createFeatureCard(feature[0], feature[1]));
        }

        //memberikan jarak antar konten didalamnya
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(subTitleLabel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(featuresPanel);

        //agar mainpanel berada ditengah
        panel.add(mainPanel, BorderLayout.CENTER);
        //mengembalikan panel branda
        return panel;
    }

    //method membuat fitur card yang berisi judul dan deskripsinya 
    private JPanel createFeatureCard(String title, String desc) {
        JPanel card = new JPanel();
        //membuat konten card agar judul dan deskripsinya itu kebawah
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COLOR_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            //set ketebalan dan warna border 
            BorderFactory.createLineBorder(COLOR_LIGHT_PURPLE, 3),
            //set padding antar konten didalam card 
            BorderFactory.createEmptyBorder(30, 20, 20, 20)
        ));

        //judul 
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(COLOR_PURPLE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //deksripsi pada card, dan agar memberikan format teks berada ditengah tabel sehingga teks tidak terpotong akibat panjang melebihi dari ukuran cardnya 
        JLabel descLabel = new JLabel("<html><center>" + desc + "</center></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(Color.DARK_GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(titleLabel);
        //memberikan jarak antara judul dengan deskripsi
        card.add(Box.createVerticalStrut(15));
        card.add(descLabel);

        return card;
    }

    /* ========================= Panel Input ========================= */
    
    //membuat panel untuk tampilan input data mahasiswa
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        //judul panel dalam bentuk grid 
        JLabel titleLabel = new JLabel("INPUT DATA MAHASISWA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_PURPLE);
        //memberikan padding atau jarak bawah antara judul dengan form agar tidak menempel 
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        //form dalam panel, agar komponen dalam form itu tersusun rapi menggunakan gridbaglayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COLOR_WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_LIGHT_PURPLE, 2),
            BorderFactory.createEmptyBorder(20, 40, 40, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        //mengatur jarak antar komponen
        gbc.insets = new Insets(20, 10, 10, 10);
        //posisinya agar rata bawah 
        gbc.anchor = GridBagConstraints.SOUTH; 

        //label NIM
        JLabel nimLabel = new JLabel("NIM:");
        nimLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nimLabel.setForeground(COLOR_PURPLE);

        //field input NIM
        inputNimField = new JTextField(20);
        inputNimField.setFont(new Font("Arial", Font.PLAIN, 18));
        inputNimField.setPreferredSize(new Dimension(250, 35));

        //Label Nama 
        JLabel namaLabel = new JLabel("Nama:");
        namaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        namaLabel.setForeground(COLOR_PURPLE);

        //field input Nama 
        inputNamaField = new JTextField(20);
        inputNamaField.setFont(new Font("Arial", Font.PLAIN, 18));
        inputNamaField.setPreferredSize(new Dimension(250, 35));

        //Label Umur
        JLabel umurLabel = new JLabel("Umur:");
        umurLabel.setFont(new Font("Arial", Font.BOLD, 16));
        umurLabel.setForeground(COLOR_PURPLE);

        //Field input Umur
        inputUmurField = new JTextField(20);
        inputUmurField.setFont(new Font("Arial", Font.PLAIN, 18));
        inputUmurField.setPreferredSize(new Dimension(100, 35));

        //panel uuntuk button simpan dan clear agar berada ditengah dan gap antar button 15
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(COLOR_WHITE);
        //memberikan jarak atau padding atas 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        //membuat tombol simpan dan clear, memberikan teks dan warna pada tombol 
        JButton simpanButton = createActionButton("Simpan Data", COLOR_PURPLE);
        JButton clearButton = createActionButton("Bersihkan", Color.GRAY);

        // Event listener: saat tombol Simpan diklik, jalankan method simpanData()
        simpanButton.addActionListener(e -> simpanData());
        // Event listener: saat tombol Bersihkan diklik, jalankan method clearInputFields()
        clearButton.addActionListener(e -> clearInputFields());

        //tambahkan tombol ke panel tombol 
        buttonPanel.add(simpanButton);
        buttonPanel.add(clearButton);

        //menambahkan komponen ke form panel berdasarkan posisi grid yang sudah diatur
        gbc.gridx = 0; gbc.gridy = 0; //kolom 0, baris 0
        formPanel.add(nimLabel, gbc); //tambahkan label NIM
        gbc.gridx = 1;  //kolom 1
        formPanel.add(inputNimField, gbc); //tambahkan field nim
        gbc.gridx = 0; gbc.gridy = 1; //kolom 0, baris 1
        formPanel.add(namaLabel, gbc); //tambahkan label nama 
        gbc.gridx = 1;  //kolom 1
        formPanel.add(inputNamaField, gbc); //tambahkan field nama 
        gbc.gridx = 0; gbc.gridy = 2; //kolom 0, baris 2
        formPanel.add(umurLabel, gbc); //tambahkan label umur
        gbc.gridx = 1; //kolom 1
        formPanel.add(inputUmurField, gbc); //tambahkan field umur
        gbc.gridx = 1; gbc.gridy = 3; //kolom 1, baris 3
        gbc.anchor = GridBagConstraints.CENTER;  //posisi tombol agar rata tengah
        formPanel.add(buttonPanel, gbc);

        // Menambahkan judul di atas dan form di tengah panel utama
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }

    /* ========================= Panel View ========================= */

    //membuat panel view
    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //header panel 
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        //judul dalam panel
        JLabel titleLabel = new JLabel("DATA MAHASISWA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_PURPLE);

        //membuat panel search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        searchPanel.setBackground(COLOR_WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        //membuat teksfield pada panel seach 
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(250, 35));

        //membuat button search dan refresh 
        JButton searchButton = createActionButton("Cari", COLOR_PURPLE);
        JButton refreshButton = createActionButton("Refresh", new Color(70, 130, 180));

        //membuat teks label sebelum search panel untuk memberitahukan fungsi search tersebut untuk mencaari data mahasiswa 
        JLabel label = new JLabel("Cari Mahasiswa: ");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        searchPanel.add(label);

        //membuat placeholder didalam searchfieldnya 
        String PLACEHOLDER = "Masukkan NIM atau Nama";
        searchField.setText(PLACEHOLDER);
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            //jika mouse klik pada bagian searchfield 
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(PLACEHOLDER)) {
                    //maka placeholder akan menghilang
                    searchField.setText("");
                    //dan ketika user mengetik maka memberikan warna teksnya hitam 
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            //jika tidak ada di searchfield
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    //jika dia teksnya tidak ada atau kosong maka munculkan placeholder
                    searchField.setText(PLACEHOLDER);
                    //set warna teks placeholdernya itu warna abu-abu
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        //event listener tombol cari 
        searchButton.addActionListener(e -> {
            //membuat variable string dengan nama keywoard yang disingkat kw 
            String kw = searchField.getText();
            //jika keywoardnya noll atau dia kosong dan hanya ada placeholder
            if (kw == null || kw.trim().isEmpty() || PLACEHOLDER.equals(kw)) {
                //maka tampilkan seluruh data
                refreshTableData();
            } else {
                //jika ada keywoardnya keluarkan dia hasilnya 
                searchData(kw.trim()); //trim untuk menghapus spasi diawal dan diakhir jadi jika user tidak sengaja ada spasi maka sistem tetap bisa membacanya dan mengeluarkan data yang diinginkan
            }
        });
        //listener untuk button refresh
        refreshButton.addActionListener(e -> {
            //set field ke placeholder 
            searchField.setText(PLACEHOLDER);
            searchField.setForeground(Color.GRAY);
            //muat ulang data tabel
            refreshTableData();
        });

        //tambahkan seluruh panel 
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        //memberikan layout agar judul berada diatas dan search berada dibawah 
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);

        //membuat array nama kolom yang akan digunakan pada tabel data mahasiswa 
        String[] columns = {"NIM", "Nama", "Umur", "Aksi"};
        // Membuat model tabel (DefaultTableModel) dengan kolom di atas dan 0 baris awal
        dmTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //cell hanya bisa diedit pada kolom 3, sehingga kolom 1-2 tidak bisa menghindari terjadinya kesalahan pada data 
                return column == 3; 
            }
        };

        //membuat tabel 
        jTable = new JTable(dmTableModel);
        //set tinggi barisnya menjadi 35
        jTable.setRowHeight(35);
        //set font dan ukurannya 
        jTable.setFont(new Font("Arial", Font.PLAIN, 14));
        //set font dan ukuran pada judul headernya 
        jTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        //memberikan latar belakang warna pada headernya 
        jTable.getTableHeader().setBackground(COLOR_PURPLE);
        //memberikan warna teks pada headernya 
        jTable.getTableHeader().setForeground(Color.WHITE);
        jTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        jTable.getColumnModel().getColumn(3).setCellRenderer(new ActionCellRenderer());
        jTable.getColumnModel().getColumn(3).setCellEditor(new ActionCellEditor());

        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_LIGHT_PURPLE, 1));

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /* ========================= Panel Edit ========================= */

    private JPanel createEditPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("EDIT DATA MAHASISWA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_PURPLE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(COLOR_WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_LIGHT_PURPLE, 2),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 30));
        searchPanel.setBackground(COLOR_WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JLabel searchLabel = new JLabel("Cari NIM:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 16));

        searchEditField = new JTextField(15);
        searchEditField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchEditField.setPreferredSize(new Dimension(250, 35));
        
        JButton searchButton = createActionButton("Cari", COLOR_PURPLE);
        
        final String PLACEHOLDER = "Masukkan NIM";
        searchEditField.setText(PLACEHOLDER);
        searchEditField.setForeground(Color.GRAY);
        searchEditField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchEditField.getText().equals(PLACEHOLDER)) {
                    searchEditField.setText("");
                    searchEditField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchEditField.getText().isEmpty()) {
                    searchEditField.setText(PLACEHOLDER);
                    searchEditField.setForeground(Color.GRAY);
                }
            }
        });

        searchButton.addActionListener(e -> cariDataUntukEdit());

        searchPanel.add(searchLabel);
        searchPanel.add(searchEditField);
        searchPanel.add(searchButton);

        JLabel nimLabel = new JLabel("NIM:");
        nimLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nimLabel.setForeground(COLOR_PURPLE);

        editNimField = new JTextField(15);
        editNimField.setFont(new Font("Arial", Font.PLAIN, 16));
        editNimField.setEditable(false);
        editNimField.setBackground(COLOR_LIGHT_GRAY);
        editNimField.setPreferredSize(new Dimension(250,35));

        JLabel namaLabel = new JLabel("Nama:");
        namaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        namaLabel.setForeground(COLOR_PURPLE);

        editNamaField = new JTextField(15);
        editNamaField.setFont(new Font("Arial", Font.PLAIN, 16));
        editNamaField.setPreferredSize(new Dimension(250,35));

        JLabel umurLabel = new JLabel("Umur:");
        umurLabel.setFont(new Font("Arial", Font.BOLD, 16));
        umurLabel.setForeground(COLOR_PURPLE);

        editUmurField = new JTextField(15);
        editUmurField.setFont(new Font("Arial", Font.PLAIN, 16));
        editUmurField.setPreferredSize(new Dimension(250,35));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(COLOR_WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton updateButton = createActionButton("Update Data", COLOR_PURPLE);
        JButton clearButton = createActionButton("Bersihkan", Color.GRAY);

        updateButton.addActionListener(e -> updateData());
        clearButton.addActionListener(e -> clearEditFields());

        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        contentPanel.add(searchPanel, gbc);
        gbc.gridwidth = 1;

        gbc.gridy = 1; gbc.gridx = 0;
        contentPanel.add(nimLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(editNimField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        contentPanel.add(namaLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(editNamaField, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        contentPanel.add(umurLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(editUmurField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(buttonPanel, gbc);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setBackground(color.darker()); }
            @Override public void mouseExited(MouseEvent e)  { button.setBackground(color); }
        });

        return button;
    }

    private void simpanData() {
        String nim = inputNimField.getText().trim();
        String nama = inputNamaField.getText().trim();
        String umurStr = inputUmurField.getText().trim();

        if (nim.isEmpty() || nama.isEmpty() || umurStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int umur = Integer.parseInt(umurStr);

            int confirm = JOptionPane.showConfirmDialog(null,
                "Apakah Anda yakin ingin menambahkan data mahasiswa?\n" +
                "NIM: " + nim + "\n" +
                "Nama: " + nama + "\n" +
                "Umur: " + umur,
                "Konfirmasi Tambah Data",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Mahasiswa mhs = new Mahasiswa(nim, nama, umur);
                MaintainData.simpanData(mhs); 

                JOptionPane.showMessageDialog(null, "Proses Simpan Berhasil.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                clearInputFields();
                refreshTableData();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Umur harus berupa angka!",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInputFields() {
        inputNimField.setText("");
        inputNamaField.setText("");
        inputUmurField.setText("");
        inputNimField.requestFocus();
    }

    private void clearEditFields() {
        editNimField.setText("");
        editNamaField.setText("");
        editUmurField.setText("");
        searchEditField.setText("");
        searchEditField.requestFocus();
    }

    private void cariDataUntukEdit() {
        String nim = searchEditField.getText().trim();


        //Cari Data Mahasiswa
        List<Mahasiswa> hasil = MaintainData.getData("filter", nim);
        if (hasil != null && !hasil.isEmpty()) {
            Mahasiswa mhs = hasil.get(0);
            editNimField.setText(mhs.getNim());
            editNamaField.setText(mhs.getNama());
            editUmurField.setText(String.valueOf(mhs.getUmur()));
            searchEditField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
            clearEditFields();
        }
    }

    
    private void updateData() {
        String nim = editNimField.getText().trim();
        String nama = editNamaField.getText().trim();
        String umurStr = editUmurField.getText().trim();

        if (nim.isEmpty() || nama.isEmpty() || umurStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!");
            return;
        }

        try {
            int umur = Integer.parseInt(umurStr);

            int confirm = JOptionPane.showConfirmDialog(null,
                "Apakah Anda yakin ingin mengupdate data mahasiswa?\n" +
                "NIM: " + nim + "\n" +
                "Nama: " + nama + "\n" +
                "Umur: " + umur,
                "Konfirmasi Update Data",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Mahasiswa mhs = new Mahasiswa(nim, nama, umur);
                MaintainData.updateData(mhs); // void

                JOptionPane.showMessageDialog(null, "Proses Update Berhasil.");
                clearEditFields();
                refreshTableData();
                // refreshBerandaMetrics();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Umur harus berupa angka!");
        }
    }

    private void refreshTableData() {
        dmTableModel.setRowCount(0);
        List<Mahasiswa> list = MaintainData.getData("all", null);

        for (Mahasiswa mhs : list) {
            dmTableModel.addRow(new Object[]{
                mhs.getNim(),
                mhs.getNama(),
                mhs.getUmur(),
                "Hapus"
            });
        }
    }

    private void searchData(String keyword) {
        dmTableModel.setRowCount(0);

        List<Mahasiswa> all = MaintainData.getData("all", null);
        String kw = keyword.toLowerCase();

        List<Mahasiswa> list = all.stream()
            .filter(m -> m.getNim().toLowerCase().contains(kw)
                      || m.getNama().toLowerCase().contains(kw))
            .collect(Collectors.toList());

        for (Mahasiswa mhs : list) {
            dmTableModel.addRow(new Object[]{
                mhs.getNim(),
                mhs.getNama(),
                mhs.getUmur(),
                "Hapus"
            });
        }
    }


    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row % 2 == 0) c.setBackground(COLOR_WHITE);
            else              c.setBackground(COLOR_LIGHT_GRAY);

            if (isSelected)   c.setBackground(COLOR_LIGHT_PURPLE);

            return c;
        }
    }

    class ActionCellRenderer extends JButton implements TableCellRenderer {
        public ActionCellRenderer() { setOpaque(true); }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            return this;
        }
    }

    class ActionCellEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
        private String action;
        private int currentRow;

        public ActionCellEditor() {
            button = new JButton();
            button.addActionListener(e -> {
                String nim = (String) dmTableModel.getValueAt(currentRow, 0);
                String nama = (String) dmTableModel.getValueAt(currentRow, 1);

                if (action.contains("Hapus")) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                        "Apakah Anda yakin ingin menghapus data mahasiswa?\n" +
                        "NIM: " + nim + "\n" +
                        "Nama: " + nama,
                        "Konfirmasi Hapus Data",
                        JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        Mahasiswa target = new Mahasiswa();
                        target.setNim(nim);
                        MaintainData.deleteData(target); 
                        JOptionPane.showMessageDialog(null, "Proses Delete Berhasil.");
                        refreshTableData();
                    }
                }
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            action = value.toString();
            button.setText(action);
            currentRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() { return action; }
    }

    /* ========================= Interface Utama ========================= */

    public void InterfaceUtama() {
        JFrame frame = new JFrame("Sistem Data Mahasiswa");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelCenter = new JPanel(cardLayout);

        panelCenter.add(createBerandaPanel(), "Beranda");
        panelCenter.add(createInputPanel(), "Input");
        panelCenter.add(createViewPanel(), "View");
        panelCenter.add(createEditPanel(), "Edit");

        frame.add(createSidebar(), BorderLayout.WEST);
        frame.add(panelCenter, BorderLayout.CENTER);

        cardLayout.show(panelCenter, "Beranda");

        frame.setVisible(true);
    }
}
