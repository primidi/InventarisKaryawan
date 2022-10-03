package inventariskaryawan.controllers;

import inventariskaryawan.config.DatabaseConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class BarangController {
    DatabaseConnection conn;
    Connection database;

    public BarangController() throws SQLException {
        this.conn = new DatabaseConnection();
        this.database = conn.getConnection();
    }

    public DefaultTableModel getBarang() throws SQLException {
        Statement statement = this.database.createStatement();

        String query = "SELECT * FROM barang";
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

    public void createBarang(
            String namaBarang,
            int stok
    ) throws SQLException {
        Statement statement = this.database.createStatement();
        String query = "INSERT INTO barang (`id_barang`, `nama_barang`, `stok`) " +
                "VALUES (" + null + ", '" + namaBarang + "', '" + stok + "')";
        statement.executeUpdate(query);
    }

    public void updateBarang(
            int id,
            String namaBarang,
            int stok
    ) throws SQLException {
        Statement statement = this.database.createStatement();
        String query = "UPDATE barang SET `nama_barang` = '" + namaBarang + "', " +
                "`stok` = '" + stok + "' WHERE `id_barang` = " + id;
        statement.executeUpdate(query);
    }

    public void deleteBarang(int id) throws SQLException {
        Statement statement = this.database.createStatement();
        String query = "DELETE from barang WHERE id_barang = " + id;
        statement.executeUpdate(query);
    }
}
