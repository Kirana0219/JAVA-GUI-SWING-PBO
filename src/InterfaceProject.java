import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import DataModel.Mahasiswa;
import java.util.List;

public class InterfaceProject {
    private CardLayout cardLayout;
    private JPanel panelCenter;
    private DefaultTableModel dmTableModel;
    private JTable jTable;
    private MahasiswaDAO mahasiswaDAO;
    
    private final Color COLOR_PURPLE = new Color(128, 0, 128);
    private final Color COLOR_LIGHT_PURPLE = new Color(230, 230, 250);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_LIGHT_GRAY = new Color(245, 245, 245);
    
    private JTextField inputNimField, inputNamaField, inputUmurField;
    private JTextField editNimField, editNamaField, editUmurField, searchEditField;
    
    public InterfaceProject() {
        mahasiswaDAO = new MahasiswaDAO();
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBackground(COLOR_PURPLE);
        
        JLabel headerLabel = new JLabel("MENU");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(COLOR_PURPLE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton[] buttons = {
            createSidebarButton("Beranda"),
            createSidebarButton("Input Data"),
            createSidebarButton("View Data"),
            createSidebarButton("Edit Data")
        };
        
        buttons[0].addActionListener(e -> showBeranda());
        buttons[1].addActionListener(e -> showInputData());
        buttons[2].addActionListener(e -> showViewData());
        buttons[3].addActionListener(e -> showEditData());
        
        sidebar.add(headerLabel);
        sidebar.add(Box.createVerticalStrut(10));
        
        for (JButton button : buttons) {
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(8));
        }
        
        sidebar.add(buttonPanel);
        sidebar.add(Box.createVerticalGlue());
        
        JLabel footerLabel = new JLabel("Sistem Mahasiswa v1.0");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(200, 200, 200));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        sidebar.add(footerLabel);
        
        return sidebar;
    }
    
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 45));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(COLOR_WHITE);
        button.setForeground(COLOR_PURPLE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_WHITE, 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(COLOR_LIGHT_PURPLE);
                button.setForeground(Color.BLACK);
            }
            
            @Override
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
    
    private JPanel createBerandaPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_WHITE);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(COLOR_WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel titleLabel = new JLabel("SELAMAT DATANG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(COLOR_PURPLE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subTitleLabel = new JLabel("Sistem Manajemen Data Mahasiswa");
        subTitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        subTitleLabel.setForeground(Color.DARK_GRAY);
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel featuresPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        featuresPanel.setMaximumSize(new Dimension(800, 150));
        featuresPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        featuresPanel.setOpaque(false);
        
        String[][] features = {
            {"Input Data", "Tambah data mahasiswa baru"},
            {"View Data", "Lihat dan kelola data mahasiswa"},
            {"Edit Data", "Ubah data mahasiswa"}
        };
        
        for (String[] feature : features) {
            featuresPanel.add(createFeatureCard(feature[0], feature[1]));
        }
        
        
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(subTitleLabel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(featuresPanel);
        
        panel.add(mainPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createFeatureCard(String title, String desc) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COLOR_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_LIGHT_PURPLE, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(COLOR_PURPLE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel("<html><center>" + desc + "</center></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(Color.DARK_GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(descLabel);
        
        return card;
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        JLabel titleLabel = new JLabel("INPUT DATA MAHASISWA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_PURPLE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COLOR_WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_LIGHT_PURPLE, 2),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel nimLabel = new JLabel("NIM:");
        nimLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nimLabel.setForeground(COLOR_PURPLE);
        
        inputNimField = new JTextField(20);
        inputNimField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputNimField.setPreferredSize(new Dimension(250, 35));
        
        JLabel namaLabel = new JLabel("Nama:");
        namaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        namaLabel.setForeground(COLOR_PURPLE);
        
        inputNamaField = new JTextField(20);
        inputNamaField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputNamaField.setPreferredSize(new Dimension(250, 35));
        
        JLabel umurLabel = new JLabel("Umur:");
        umurLabel.setFont(new Font("Arial", Font.BOLD, 16));
        umurLabel.setForeground(COLOR_PURPLE);
        
        inputUmurField = new JTextField(20);
        inputUmurField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputUmurField.setPreferredSize(new Dimension(100, 35));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(COLOR_WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton simpanButton = createActionButton("Simpan Data", COLOR_PURPLE);
        JButton clearButton = createActionButton("Bersihkan", Color.GRAY);
        
        simpanButton.addActionListener(e -> simpanData());
        clearButton.addActionListener(e -> clearInputFields());
        
        buttonPanel.add(simpanButton);
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(nimLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(inputNimField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(namaLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(inputNamaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(umurLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(inputUmurField, gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("DATA MAHASISWA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_PURPLE);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setBackground(COLOR_WHITE);
        
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(250, 35));
        
        JButton searchButton = createActionButton("Cari", COLOR_PURPLE);
        JButton refreshButton = createActionButton("Refresh", new Color(70, 130, 180));
        
        searchButton.addActionListener(e -> searchData(searchField.getText()));
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            refreshTableData();
        });
        
        searchPanel.add(new JLabel("Cari berdasarkan NIM atau Nama:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);
        
        dmTableModel = new DefaultTableModel(Mahasiswa.kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        
        jTable = new JTable(dmTableModel);
        jTable.setRowHeight(35);
        jTable.setFont(new Font("Arial", Font.PLAIN, 14));
        jTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        jTable.getTableHeader().setBackground(COLOR_PURPLE);
        jTable.getTableHeader().setForeground(Color.WHITE);
        jTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        
        jTable.getColumnModel().getColumn(3).setCellRenderer(new ActionCellRenderer());
        jTable.getColumnModel().getColumn(3).setCellEditor(new ActionCellEditor());
        
        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_LIGHT_PURPLE, 1));
        
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setBackground(COLOR_LIGHT_PURPLE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel totalLabel = new JLabel();
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(COLOR_PURPLE);
        footerPanel.add(totalLabel);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);
        
        updateTotalLabel(totalLabel);
        
        return panel;
    }
    
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
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setBackground(COLOR_WHITE);
        
        JLabel searchLabel = new JLabel("Cari NIM:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        searchEditField = new JTextField(15);
        searchEditField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchEditField.setPreferredSize(new Dimension(150, 30));
        
        JButton searchButton = createActionButton("Cari", COLOR_PURPLE);
        
        searchButton.addActionListener(e -> cariDataUntukEdit());
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchEditField);
        searchPanel.add(searchButton);
        
        JLabel nimLabel = new JLabel("NIM:");
        nimLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nimLabel.setForeground(COLOR_PURPLE);
        
        editNimField = new JTextField(15);
        editNimField.setFont(new Font("Arial", Font.PLAIN, 14));
        editNimField.setEditable(false);
        editNimField.setBackground(COLOR_LIGHT_GRAY);
        
        JLabel namaLabel = new JLabel("Nama:");
        namaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        namaLabel.setForeground(COLOR_PURPLE);
        
        editNamaField = new JTextField(15);
        editNamaField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel umurLabel = new JLabel("Umur:");
        umurLabel.setFont(new Font("Arial", Font.BOLD, 14));
        umurLabel.setForeground(COLOR_PURPLE);
        
        editUmurField = new JTextField(15);
        editUmurField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(COLOR_WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton updateButton = createActionButton("Update Data", COLOR_PURPLE);
        JButton clearButton = createActionButton("Bersihkan", Color.GRAY);
        
        updateButton.addActionListener(e -> updateData());
        clearButton.addActionListener(e -> clearEditFields());
        
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
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
        
        gbc.gridy = 4; gbc.gridx = 0;
        gbc.gridwidth = 2;
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
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
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
            // TIDAK ADA BATASAN UMUR
            
            // Tampilkan konfirmasi
            int confirm = JOptionPane.showConfirmDialog(null,
                "Apakah Anda yakin ingin menambahkan data mahasiswa?\n" +
                "NIM: " + nim + "\n" +
                "Nama: " + nama + "\n" +
                "Umur: " + umur,
                "Konfirmasi Tambah Data",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                Mahasiswa mhs = new Mahasiswa(nim, nama, umur);
                boolean success = mahasiswaDAO.insertMahasiswa(mhs);
                
                if (success) {
                    JOptionPane.showMessageDialog(null, "Data berhasil disimpan!", 
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    clearInputFields();
                    refreshTableData();
                } else {
                    JOptionPane.showMessageDialog(null, "NIM sudah ada!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
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
        
        if (nim.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Masukkan NIM yang akan dicari!");
            return;
        }
        
        Mahasiswa mhs = mahasiswaDAO.getMahasiswaByNim(nim);
        if (mhs != null) {
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
            
            // Tampilkan konfirmasi
            int confirm = JOptionPane.showConfirmDialog(null,
                "Apakah Anda yakin ingin mengupdate data mahasiswa?\n" +
                "NIM: " + nim + "\n" +
                "Nama: " + nama + "\n" +
                "Umur: " + umur,
                "Konfirmasi Update Data",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                Mahasiswa mhs = new Mahasiswa(nim, nama, umur);
                boolean success = mahasiswaDAO.updateMahasiswa(mhs);
                
                if (success) {
                    JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
                    clearEditFields();
                    refreshTableData();
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal mengupdate data!");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Umur harus berupa angka!");
        }
    }
    
    private void refreshTableData() {
        dmTableModel.setRowCount(0);
        List<Mahasiswa> list = mahasiswaDAO.getAllMahasiswa();
        
        for (Mahasiswa mhs : list) {
            dmTableModel.addRow(new Object[]{
                mhs.getNim(),
                mhs.getNama(),
                mhs.getUmur(),
                "Hapus"
            });
        }
        
        updateTotalLabel();
    }
    
    private void searchData(String keyword) {
        dmTableModel.setRowCount(0);
        List<Mahasiswa> list = mahasiswaDAO.searchMahasiswa(keyword);
        
        for (Mahasiswa mhs : list) {
            dmTableModel.addRow(new Object[]{
                mhs.getNim(),
                mhs.getNama(),
                mhs.getUmur(),
                "Hapus"
            });
        }
        
        updateTotalLabel();
    }
    
    private void updateTotalLabel() {
        if (panelCenter != null) {
            Component[] comps = panelCenter.getComponents();
            for (Component comp : comps) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    if (panel.getLayout() instanceof BorderLayout) {
                        Component southComp = ((BorderLayout) panel.getLayout()).getLayoutComponent(BorderLayout.SOUTH);
                        if (southComp instanceof JPanel) {
                            JPanel footer = (JPanel) southComp;
                            for (Component footerComp : footer.getComponents()) {
                                if (footerComp instanceof JLabel) {
                                    ((JLabel) footerComp).setText("Jumlah Mahasiswa: " + mahasiswaDAO.getTotalMahasiswa());
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void updateTotalLabel(JLabel label) {
        label.setText("Jumlah Mahasiswa: " + mahasiswaDAO.getTotalMahasiswa());
    }
    
    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (row % 2 == 0) {
                c.setBackground(COLOR_WHITE);
            } else {
                c.setBackground(COLOR_LIGHT_GRAY);
            }
            
            if (isSelected) {
                c.setBackground(COLOR_LIGHT_PURPLE);
            }
            
            return c;
        }
    }
    
    class ActionCellRenderer extends JButton implements TableCellRenderer {
        public ActionCellRenderer() {
            setOpaque(true);
        }
        
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
                    // Tampilkan alert konfirmasi hapus
                    int confirm = JOptionPane.showConfirmDialog(null,
                        "Apakah Anda yakin ingin menghapus data mahasiswa?\n" +
                        "NIM: " + nim + "\n" +
                        "Nama: " + nama,
                        "Konfirmasi Hapus Data",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = mahasiswaDAO.deleteMahasiswa(nim);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                            refreshTableData();
                        } else {
                            JOptionPane.showMessageDialog(null, "Gagal menghapus data!");
                        }
                    }
                } else if (action.contains("Edit")) {
                    Mahasiswa mhs = mahasiswaDAO.getMahasiswaByNim(nim);
                    if (mhs != null) {
                        editNimField.setText(mhs.getNim());
                        editNamaField.setText(mhs.getNama());
                        editUmurField.setText(String.valueOf(mhs.getUmur()));
                        cardLayout.show(panelCenter, "Edit");
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
        public Object getCellEditorValue() {
            return action;
        }
    }
    
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