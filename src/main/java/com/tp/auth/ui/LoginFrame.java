
package com.tp.auth.ui;

import com.tp.auth.service.AuthService;

import com.tp.auth.dao.UserDao;
import com.tp.auth.model.User;
import com.tp.auth.ui.ContactFrame;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("Authentification");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 220);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nom d'utilisateur :"));
        panel.add(usernameField);

        panel.add(new JLabel("Mot de passe :"));
        panel.add(passwordField);

        JButton loginButton = new JButton("Se connecter");
        JButton registerButton = new JButton("S'inscrire");

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> register());
    }

    private void register() {
        try {
            boolean success = authService.register(
                usernameField.getText().trim(),
                new String(passwordField.getPassword())
            );

            JOptionPane.showMessageDialog(this,
                success ? "Compte créé !" : "Inscription impossible.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erreur : " + e.getMessage());
        }
    }


    private void login() {
    	try {
    		String username = usernameField.getText().trim();
    		String password = new String(passwordField.getPassword());

    		boolean success = authService.login(username, password);

    		if (success) {
    			User user = new UserDao().findByUsername(username);

    			if (user != null) {
    				new ContactFrame(user).setVisible(true);
    				dispose();
    			}
    		} else {
    			JOptionPane.showMessageDialog(
    					this, "Identifiants incorrects.");
    		}

    	} catch (Exception e) {
    		JOptionPane.showMessageDialog(
    				this, "Erreur : " + e.getMessage());
    	}
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
