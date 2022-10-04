package inventariskaryawan.controllers;

import inventariskaryawan.config.DatabaseConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class PeminjamanController {
    DatabaseConnection conn;
    Connection database;

    public PeminjamanController() throws SQLException {
        this.conn = new DatabaseConnection();
        this.database = conn.getConnection();
    }

    public DefaultTableModel getPeminjaman() throws SQLException {
        Statement statement = this.database.createStatement();

        String query = "SELECT peminjaman.id_peminjaman, karyawan.nama_karyawan, barang.nama_barang, peminjaman.status " +
                "FROM peminjaman " +
                "LEFT JOIN karyawan ON peminjaman.id_karyawan = karyawan.id_karyawan " +
                "LEFT JOIN barang ON peminjaman.id_barang = barang.id_barang " +
                "ORDER BY peminjaman.id_peminjaman";
        ResultSet result = statement.executeQuery(query);

        ResultSetMetaData metaData = result.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<>();
        while (result.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(result.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    public void createPeminjaman(
            int idKaryawan,
            int idBarang,
            String status
    ) throws SQLException {
        Statement statement = this.database.createStatement();
        String query = "INSERT INTO peminjaman (`id_peminjaman`, `id_karyawan`, `id_barang`, `status`) " +
                "VALUES (" + null + ", '" + idKaryawan + "', '" + idBarang + "', '" + status + "')";
        statement.executeUpdate(query);
    }

    public void updatePeminjamanStatus(int id, String status) throws SQLException {
        Statement statement = this.database.createStatement();
        String query = "UPDATE peminjaman SET `status` = '" + status + "' WHERE `id_peminjaman` = " + id;
        statement.executeUpdate(query);
    }

    public void deletePeminjaman(int id) throws SQLException {
        Statement statement = this.database.createStatement();
        String query = "DELETE from peminjaman WHERE id_peminjaman = " + id;
        statement.executeUpdate(query);
    }
}
