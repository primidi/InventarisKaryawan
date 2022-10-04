package inventariskaryawan.views;

import inventariskaryawan.controllers.BarangController;
import inventariskaryawan.controllers.KaryawanController;
import inventariskaryawan.controllers.PeminjamanController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class PeminjamanForms extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JLabel lbDataPeminjaman;
    private JPanel panelTable;
    private JTable tablePeminjaman;
    private JPanel btnPanel;
    private JButton btnDelete;
    private JButton btnAdd;
    private JButton btnEdit;


    KaryawanController karyawanController = new KaryawanController();
    BarangController barangController = new BarangController();
    PeminjamanController peminjamanController = new PeminjamanController();

    ArrayList<Integer> karyawanIds = karyawanController.getKaryawanIds();
    ArrayList<Integer> barangIds = barangController.getBarangIds();
    ArrayList<String> statusChoices = setStatusChoices();

    JPanel manipulatePanelAdd = new JPanel();
    JPanel manipulatePanelEdit = new JPanel();
    JLabel lbPeminjam = new JLabel("Id Karyawan");
    JComboBox cbPeminjam = new JComboBox(karyawanIds.toArray());
    JLabel lbBarang = new JLabel("Id Barang");
    JComboBox cbBarang = new JComboBox(barangIds.toArray());
    JLabel lbStatusAdd = new JLabel("Status");
    JComboBox cbStatusAdd = new JComboBox(statusChoices.toArray());
    JLabel lbStatusEdit = new JLabel("Status");
    JComboBox cbStatusEdit = new JComboBox(statusChoices.toArray());

    public PeminjamanForms(String appName) throws SQLException {
        super(appName);

        add(mainPanel);
        this.btnAdd.addActionListener(this);
        this.btnEdit.addActionListener(this);
        this.btnDelete.addActionListener(this);

        this.manipulatePanelAdd.setLayout(new GridLayout(3, 2));
        this.manipulatePanelAdd.add(lbPeminjam);
        this.manipulatePanelAdd.add(cbPeminjam);
        this.manipulatePanelAdd.add(lbBarang);
        this.manipulatePanelAdd.add(cbBarang);
        this.manipulatePanelAdd.add(lbStatusAdd);
        this.manipulatePanelAdd.add(cbStatusAdd);

        this.manipulatePanelEdit.setLayout(new GridLayout(1, 2));
        this.manipulatePanelEdit.add(lbStatusEdit);
        this.manipulatePanelEdit.add(cbStatusEdit);

        getPeminjaman();

        setSize(720, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        new PeminjamanForms("Inventaris Karyawan");
    }

    private void getPeminjaman() throws SQLException {
        DefaultTableModel result = this.peminjamanController.getPeminjaman();
        this.tablePeminjaman.setModel(result);
    }

    private void createPeminjaman() throws SQLException {
        this.cbPeminjam.setSelectedIndex(0);
        this.cbBarang.setSelectedIndex(0);
        this.cbStatusAdd.setSelectedIndex(0);
        this.cbStatusAdd.setEnabled(false);

        int dialog = JOptionPane.showConfirmDialog(
                this,
                manipulatePanelAdd,
                "Tambah Data Peminjaman",
                JOptionPane.OK_CANCEL_OPTION
        );

        int idPeminjam = (int) this.cbPeminjam.getSelectedItem();
        int idBarang = (int) this.cbBarang.getSelectedItem();
        String status = (String) this.cbStatusAdd.getSelectedItem();

        if (dialog == JOptionPane.OK_OPTION) {
            this.peminjamanController.createPeminjaman(
                    idPeminjam,
                    idBarang,
                    status
            );
        }
    }

    private void updatePeminjamanStatus() throws SQLException {
        int id = getIdSelectedRow();
        String status = getValueStatus();

        this.cbStatusEdit.setSelectedIndex(this.statusChoices.indexOf(status));

        int dialog = JOptionPane.showConfirmDialog(
                this,
                manipulatePanelEdit,
                "Ubah Status Peminjaman",
                JOptionPane.OK_CANCEL_OPTION
        );

        String newStatus = (String) this.cbStatusEdit.getSelectedItem();

        if (dialog == JOptionPane.OK_OPTION) {
            this.peminjamanController.updatePeminjamanStatus(id, newStatus);
        }
    }

    private void deletePeminjaman() throws SQLException {
        int id = getIdSelectedRow();

        JPanel confirmPanel = new JPanel();
        confirmPanel.add(new JLabel("Apakah anda yakin ingin menghapus data ini?"));
        int dialog = JOptionPane.showConfirmDialog(
                this,
                confirmPanel,
                "Hapus Data Peminjaman",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (dialog == JOptionPane.OK_OPTION) {
            this.peminjamanController.deletePeminjaman(id);
        }
    }

    private ArrayList<String> setStatusChoices() {
        ArrayList<String> status = new ArrayList<>();
        status.add("Sedang dipinjam");
        status.add("Sudah dikembalikan");

        return status;
    }

    private int getIdSelectedRow() {
        int column = 0;
        int row = this.tablePeminjaman.getSelectedRow();
        return (int) this.tablePeminjaman.getModel().getValueAt(row, column);
    }

    private String getValueStatus() {
        int column = 3;
        int row = this.tablePeminjaman.getSelectedRow();
        return (String) this.tablePeminjaman.getModel().getValueAt(row, column);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            try {
                createPeminjaman();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == btnEdit) {
            try {
                updatePeminjamanStatus();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == btnDelete) {
            try {
                deletePeminjaman();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        try {
            getPeminjaman();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
