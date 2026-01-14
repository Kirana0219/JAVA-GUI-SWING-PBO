import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import DataModel.Mahasiswa;

public class InterfaceProject {
    private CardLayout cardLayout;
    private JPanel panelCenter;
    private DefaultTableModel dmTableModel;

    private JPanel tampilanSampingButton(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel.setPreferredSize(new Dimension((int)(screen.width * 0.15), 0));
        jPanel.setBackground(new Color(200, 220, 240)); // Warna berbeda untuk sidebar

        JButton btInputData = new JButton("Input Data");
        btInputData.setAlignmentX(Component.LEFT_ALIGNMENT);
        btInputData.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        btInputData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCenter, "Input");
                System.out.println("Input Data");
            }
        });

        JButton btViewData = new JButton("View Data");
        btViewData.setAlignmentX(Component.LEFT_ALIGNMENT);
        btViewData.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        btViewData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCenter, "View");
                System.out.println("View Data");
            }
        });

        // Tambahan tombol baru untuk perbedaan
        JButton btEditData = new JButton("Edit Data");
        btEditData.setAlignmentX(Component.LEFT_ALIGNMENT);
        btEditData.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        btEditData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCenter, "Edit");
                System.out.println("Edit Data");
            }
        });

        jPanel.add(btInputData);
        jPanel.add(Box.createVerticalStrut(5));
        jPanel.add(btViewData);
        jPanel.add(Box.createVerticalStrut(5));
        jPanel.add(btEditData); // Tambahkan tombol edit

        return jPanel;
    }

    private JPanel centerFill(Dimension screen) {
        cardLayout = new CardLayout();
        panelCenter = new JPanel(cardLayout);
        panelCenter.add(inputCenter(screen), "Input");
        panelCenter.add(viewCenter(screen), "View");
        panelCenter.add(editCenter(screen), "Edit"); // Tambahkan panel edit
        return panelCenter;
    }

    private JPanel inputCenter(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(255, 250, 205)); // Warna berbeda
        jPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        jPanel.setLayout(new GridBagLayout()); // Gunakan GridBagLayout untuk perbedaan
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);

        Font fntLabel = new Font("Arial", Font.BOLD, 24); // Font sedikit berbeda

        JLabel jLabelNim = new JLabel("NIM Mahasiswa:");
        JTextField jTextFieldNim = new JTextField(20);
        jTextFieldNim.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel jLabelNama = new JLabel("Nama Mahasiswa:");
        JTextField jTextFieldNama = new JTextField(20);
        jTextFieldNama.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel jLabelUmur = new JLabel("Umur Mahasiswa:");
        JTextField jTextFieldUmur = new JTextField(20);
        jTextFieldUmur.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton btSubmit = new JButton("Submit");
        btSubmit.setPreferredSize(new Dimension(150, 40));

        gbc.gridx = 0; gbc.gridy = 0; jPanel.add(jLabelNim, gbc);
        gbc.gridx = 1; jPanel.add(jTextFieldNim, gbc);
        gbc.gridx = 0; gbc.gridy = 1; jPanel.add(jLabelNama, gbc);
        gbc.gridx = 1; jPanel.add(jTextFieldNama, gbc);
        gbc.gridx = 0; gbc.gridy = 2; jPanel.add(jLabelUmur, gbc);
        gbc.gridx = 1; jPanel.add(jTextFieldUmur, gbc);
        gbc.gridx = 1; gbc.gridy = 3; jPanel.add(btSubmit, gbc);

        return jPanel;
    }

    private JPanel viewCenter(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(240, 248, 255)); // Warna berbeda
        jPanel.setLayout(new BorderLayout());

        dmTableModel = new DefaultTableModel(Mahasiswa.kolom, 0);
        JTable jTable = new JTable(dmTableModel);
        JTableHeader jtHeader = jTable.getTableHeader();
        jtHeader.setFont(new Font("Arial", Font.BOLD, 16)); // Font berbeda
        jtHeader.setPreferredSize(new Dimension(0, (int)(screen.height * 0.05))); // Tinggi berbeda

        JScrollPane scrollPane = new JScrollPane(jTable);
        jPanel.add(scrollPane);

        return jPanel;
    }

    // Panel edit baru untuk perbedaan
    private JPanel editCenter(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(255, 228, 225)); // Warna berbeda
        jPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        Font fntLabel = new Font("Arial", Font.BOLD, 24);

        JLabel jLabelSelect = new JLabel("Pilih NIM untuk Edit:");
        JTextField jTextFieldSelect = new JTextField();
        jTextFieldSelect.setMaximumSize(new Dimension(300, 40));
        jTextFieldSelect.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton btLoad = new JButton("Load Data");
        btLoad.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Placeholder untuk field edit (mirip input tapi untuk edit)
        JLabel jLabelNim = new JLabel("NIM:");
        JTextField jTextFieldNim = new JTextField();
        jTextFieldNim.setMaximumSize(new Dimension(300, 40));

        JLabel jLabelNama = new JLabel("Nama:");
        JTextField jTextFieldNama = new JTextField();
        jTextFieldNama.setMaximumSize(new Dimension(300, 40));

        JLabel jLabelUmur = new JLabel("Umur:");
        JTextField jTextFieldUmur = new JTextField();
        jTextFieldUmur.setMaximumSize(new Dimension(300, 40));

        JButton btUpdate = new JButton("Update");
        btUpdate.setAlignmentX(Component.LEFT_ALIGNMENT);

        jPanel.add(jLabelSelect);
        jPanel.add(jTextFieldSelect);
        jPanel.add(Box.createVerticalStrut(10));
        jPanel.add(btLoad);
        jPanel.add(Box.createVerticalStrut(20));
        jPanel.add(jLabelNim);
        jPanel.add(jTextFieldNim);
        jPanel.add(Box.createVerticalStrut(10));
        jPanel.add(jLabelNama);
        jPanel.add(jTextFieldNama);
        jPanel.add(Box.createVerticalStrut(10));
        jPanel.add(jLabelUmur);
        jPanel.add(jTextFieldUmur);
        jPanel.add(Box.createVerticalStrut(20));
        jPanel.add(btUpdate);

        return jPanel;
    }

    public void InterfaceUtama() {
        JFrame jF = new JFrame("Master Data Mahasiswa"); // Judul sedikit berbeda
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jF.setSize(screen.width, screen.height);
        jF.setLayout(new BorderLayout(10, 10)); // Gap berbeda
        ((JComponent) jF.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding berbeda
        jF.add(centerFill(screen), BorderLayout.CENTER);
        jF.add(tampilanSampingButton(screen), BorderLayout.WEST);
        jF.setVisible(true);
    }
}