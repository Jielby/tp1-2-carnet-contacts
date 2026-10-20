
package com.tp.auth.service;

import com.tp.auth.dao.UserDao;
import com.tp.auth.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private final UserDao userDao = new UserDao();

    public boolean register(String username, String password)
            throws Exception {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return false;
        }

        if (userDao.findByUsername(username) != null) {
            return false;
        }

        String passwordHash = BCrypt.hashpw(
            password, BCrypt.gensalt(12));

        return userDao.createUser(username, passwordHash);
    }

    public boolean login(String username, String password)
            throws Exception {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return false;
        }

        User user = userDao.findByUsername(username);

        return user != null
            && BCrypt.checkpw(password, user.getPasswordHash());
    }
}
