package framework.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseUtils implements AutoCloseable {
    private final Connection connection;

    public DatabaseUtils(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
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
             ResultSet result = statement.executeQuery()) {
            List<List<Object>> rows = new ArrayList<>();
            while (result.next()) {
                List<Object> row = new ArrayList<>();
                for (int column = 1; column <= result.getMetaData().getColumnCount(); column++) {
                    row.add(result.getObject(column));
                }
                rows.add(row);
            }
            return rows;
        }
    }

    private PreparedStatement prepare(String sql, Object... parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int index = 0; index < parameters.length; index++) {
            statement.setObject(index + 1, parameters[index]);
        }
        return statement;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
