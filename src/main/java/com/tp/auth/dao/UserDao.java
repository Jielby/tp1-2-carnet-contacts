
package com.tp.auth.dao;

import com.tp.auth.db.Database;
import com.tp.auth.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDao {

    public boolean createUser(String username, String passwordHash)
            throws Exception {

        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, passwordHash);

            return stmt.executeUpdate() == 1;
        }
    }

    public User findByUsername(String username) throws Exception {

        String sql = "SELECT id, username, password_hash FROM users WHERE username = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password_hash")
                    );
                }
            }
        }

        return null;
    }
}
