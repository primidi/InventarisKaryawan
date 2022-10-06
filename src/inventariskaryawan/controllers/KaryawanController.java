package inventariskaryawan.controllers;

import inventariskaryawan.config.DatabaseConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class KaryawanController {
    DatabaseConnection conn;
    Connection database;

    public KaryawanController() throws SQLException {
        this.conn = new DatabaseConnection();
        this.database = conn.getDbConnection();
    }
    public DefaultTableModel getKaryawan() throws SQLException {
        Statement statement = this.database.createStatement();

        String query = "SELECT * FROM karyawan";
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

    public ArrayList<Integer> getKaryawanIds() throws SQLException {
        Statement statement = this.database.createStatement();

        String query = "SELECT id_karyawan FROM karyawan";
        ResultSet result = statement.executeQuery(query);

        ArrayList<Integer> ids = new ArrayList<>();
        while (result.next()) {
            ids.add(result.getInt("id_karyawan"));
        }

        return ids;
    }

    public void createKaryawan(
            String namaKaryawan,
            String jabatan,
            String noTlp,
            String email
    ) throws SQLException {
        Statement statement = this.database.createStatement();
        String query = "INSERT INTO karyawan (`id_karyawan`, `nama_karyawan`, `jabatan`, `no_tlp`, `email`) " +
                "VALUES (" + null + ", '" + namaKaryawan + "', '" + jabatan + "', '" + noTlp + "', '" + email + "')";
        statement.executeUpdate(query);
    }

    public void updateKaryawan(
            int id,
            String namaKaryawan,
            String jabatan,
            String noTlp,
            String email
    ) throws SQLException {
        Statement statement = this.database.createStatement();
        String query = "UPDATE karyawan SET `nama_karyawan` = '" + namaKaryawan + "', " +
                "`jabatan` = '" + jabatan + "', `no_tlp` = '" + noTlp + "', " +
                "`email` = '" + email + "' WHERE `id_karyawan` = " + id;
        statement.executeUpdate(query);
    }

    public void deleteKaryawan(int id) throws SQLException {
        Statement statement = this.database.createStatement();
        String query = "DELETE from karyawan WHERE id_karyawan = " + id;
        statement.executeUpdate(query);
    }
}
