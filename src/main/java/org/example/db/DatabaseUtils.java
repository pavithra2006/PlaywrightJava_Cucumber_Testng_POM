package org.example.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils implements AutoCloseable {
    private final Connection connection;

    public DatabaseUtils(String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public int update(String sql, Object... parameters) throws SQLException {
        try (PreparedStatement statement = prepare(sql, parameters)) {
            return statement.executeUpdate();
        }
    }

    public int delete(String sql, Object... parameters) throws SQLException {
        return update(sql, parameters);
    }

    public List<List<Object>> query(String sql, Object... parameters) throws SQLException {
        try (PreparedStatement statement = prepare(sql, parameters);
             ResultSet results = statement.executeQuery()) {
            List<List<Object>> rows = new ArrayList<>();
            ResultSetMetaData metadata = results.getMetaData();
            while (results.next()) {
                List<Object> row = new ArrayList<>();
                for (int column = 1; column <= metadata.getColumnCount(); column++) {
                    row.add(results.getObject(column));
                }
                rows.add(row);
            }
            return rows;
        }
    }

    public void execute(String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private PreparedStatement prepare(String sql, Object... parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
        return statement;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
