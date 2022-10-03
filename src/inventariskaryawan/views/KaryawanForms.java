package inventariskaryawan.views;

import inventariskaryawan.controllers.KaryawanController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class KaryawanForms extends JFrame implements ActionListener {
    private JLabel lbDataKaryawan;
    private JPanel mainPanel;
    private JPanel panelTable;
    private JTable tableKaryawan;
    private JButton btnEdit;
    private JButton btnDelete;
    private JPanel btnPanel;
    private JButton btnAdd;

    JPanel manipulatePanel = new JPanel();
    JLabel lbNama = new JLabel("Nama Karyawan");
    JTextField tfNama = new JTextField(15);
    JLabel lbJabatan = new JLabel("Jabatan");
    JTextField tfJabatan = new JTextField(15);
    JLabel lbNoTlp = new JLabel("No Telepon");
    JTextField tfNoTlp = new JTextField(15);
    JLabel lbEmail = new JLabel("Email");
    JTextField tfEmail = new JTextField(15);
    KaryawanController controller = new KaryawanController();

    public KaryawanForms(String appName) throws SQLException {
        super(appName);

        add(mainPanel);
        this.btnAdd.addActionListener(this);
        this.btnEdit.addActionListener(this);
        this.btnDelete.addActionListener(this);

        this.manipulatePanel.setLayout(new GridLayout(4, 2));
        this.manipulatePanel.add(lbNama);
        this.manipulatePanel.add(tfNama);
        this.manipulatePanel.add(lbJabatan);
        this.manipulatePanel.add(tfJabatan);
        this.manipulatePanel.add(lbNoTlp);
        this.manipulatePanel.add(tfNoTlp);
        this.manipulatePanel.add(lbEmail);
        this.manipulatePanel.add(tfEmail);

        getKaryawan();

        setSize(720, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        new KaryawanForms("Inventaris Karyawan");
    }

    private void getKaryawan() throws SQLException {
        DefaultTableModel result = this.controller.getKaryawan();
        this.tableKaryawan.setModel(result);
    }

    private void createKaryawan() throws SQLException {
        this.tfNama.setText("");
        this.tfJabatan.setText("");
        this.tfNoTlp.setText("");
        this.tfEmail.setText("");

        int dialog = JOptionPane.showConfirmDialog(
                this,
                manipulatePanel,
                "Tambah Data",
                JOptionPane.OK_CANCEL_OPTION
        );

        String namaKaryawan = this.tfNama.getText();
        String jabatan = this.tfJabatan.getText();
        String noTlp = this.tfNoTlp.getText();
        String email = this.tfEmail.getText();

        if (dialog == JOptionPane.OK_OPTION) {
            this.controller.createKaryawan(
                    namaKaryawan,
                    jabatan,
                    noTlp,
                    email
            );
        }
    }

    private void updateKaryawan() throws SQLException {
        int id = getIdSelectedRow();
        String namaKaryawan = getValueSelectedRow(1);
        String jabatan = getValueSelectedRow(2);
        String noTlp = getValueSelectedRow(3);
        String email = getValueSelectedRow(4);

        this.tfNama.setText(namaKaryawan);
        this.tfJabatan.setText(jabatan);
        this.tfNoTlp.setText(noTlp);
        this.tfEmail.setText(email);

        int dialog = JOptionPane.showConfirmDialog(
                this,
                manipulatePanel,
                "Ubah Data",
                JOptionPane.OK_CANCEL_OPTION
        );

        String newNamaKaryawan = this.tfNama.getText();
        String newJabatan = this.tfJabatan.getText();
        String newNoTlp = this.tfNoTlp.getText();
        String newEmail = this.tfEmail.getText();

        if (dialog == JOptionPane.OK_OPTION) {
            this.controller.updateKaryawan(
                    id,
                    newNamaKaryawan,
                    newJabatan,
                    newNoTlp,
                    newEmail
            );
        }
    }

    private void deleteKaryawan() throws SQLException {
        int id = getIdSelectedRow();

        JPanel confirmPanel = new JPanel();
        confirmPanel.add(new JLabel("Apakah anda yakin ingin menghapus data ini?"));
        int dialog = JOptionPane.showConfirmDialog(
                this,
                confirmPanel,
                "Hapus Data",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (dialog == JOptionPane.OK_OPTION) {
            this.controller.deleteKaryawan(id);
        }
    }

    private int getIdSelectedRow() {
        int column = 0;
        int row = this.tableKaryawan.getSelectedRow();
        return (int) this.tableKaryawan.getModel().getValueAt(row, column);
    }

    private String getValueSelectedRow(int col) {
        int column = col;
        int row = this.tableKaryawan.getSelectedRow();
        return (String) this.tableKaryawan.getModel().getValueAt(row, column);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            try {
                createKaryawan();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == btnEdit) {
            try {
                updateKaryawan();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == btnDelete) {
            try {
                deleteKaryawan();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        try {
            getKaryawan();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
