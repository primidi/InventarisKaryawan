package inventariskaryawan.views;

import inventariskaryawan.controllers.BarangController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class BarangForms extends JFrame implements ActionListener {
    private JLabel lbDataBarang;
    private JPanel mainPanel;
    private JPanel panelTable;
    private JTable tableKaryawan;
    private JPanel btnPanel;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnAdd;

    JPanel manipulatePanel = new JPanel();
    JLabel lbNama = new JLabel("Nama Barang");
    JTextField tfNama = new JTextField(15);
    JLabel lbStok = new JLabel("Stok");
    JSpinner sStok = new JSpinner();

    BarangController controller = new BarangController();

    public BarangForms(String appName) throws SQLException {
        super(appName);

        add(mainPanel);
        this.btnAdd.addActionListener(this);
        this.btnEdit.addActionListener(this);
        this.btnDelete.addActionListener(this);

        this.manipulatePanel.setLayout(new GridLayout(2, 2));
        this.manipulatePanel.add(lbNama);
        this.manipulatePanel.add(tfNama);
        this.manipulatePanel.add(lbStok);
        this.manipulatePanel.add(sStok);

        getBarang();

        setSize(720, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        new BarangForms("Inventaris Karyawan");
    }

    private void getBarang() throws SQLException {
        DefaultTableModel result = this.controller.getBarang();
        this.tableKaryawan.setModel(result);
    }

    private void createBarang() throws SQLException {
        this.tfNama.setText("");
        this.sStok.setValue(0);

        int dialog = JOptionPane.showConfirmDialog(
                this,
                manipulatePanel,
                "Tambah Data",
                JOptionPane.OK_CANCEL_OPTION
        );

        String namaBarang = this.tfNama.getText();
        int stok = (int) this.sStok.getValue();

        if (dialog == JOptionPane.OK_OPTION) {
            this.controller.createBarang(
                    namaBarang,
                    stok
            );
        }
    }

    private void updateBarang() throws SQLException {
        int id = getIdSelectedRow();
        String namaBarang = getValueNamaBarang();
        int stok = getValueStokBarang();

        this.tfNama.setText(namaBarang);
        this.sStok.setValue(stok);

        int dialog = JOptionPane.showConfirmDialog(
                this,
                manipulatePanel,
                "Ubah Data",
                JOptionPane.OK_CANCEL_OPTION
        );

        String newNamaBarang = this.tfNama.getText();
        int newStok = (int) this.sStok.getValue();

        if (dialog == JOptionPane.OK_OPTION) {
            this.controller.updateBarang(
                    id,
                    newNamaBarang,
                    newStok
            );
        }
    }

    private void deleteBarang() throws SQLException {
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
            this.controller.deleteBarang(id);
        }
    }

    private int getIdSelectedRow() {
        int column = 0;
        int row = this.tableKaryawan.getSelectedRow();
        return (int) this.tableKaryawan.getModel().getValueAt(row, column);
    }

    private String getValueNamaBarang() {
        int column = 1;
        int row = this.tableKaryawan.getSelectedRow();
        return (String) this.tableKaryawan.getModel().getValueAt(row, column);
    }

    private int getValueStokBarang() {
        int column = 2;
        int row = this.tableKaryawan.getSelectedRow();
        return (int) this.tableKaryawan.getModel().getValueAt(row, column);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            try {
                createBarang();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == btnEdit) {
            try {
                updateBarang();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == btnDelete) {
            try {
                deleteBarang();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        try {
            getBarang();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
