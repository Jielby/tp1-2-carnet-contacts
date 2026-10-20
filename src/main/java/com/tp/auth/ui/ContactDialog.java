
package com.tp.auth.ui;

import com.tp.auth.model.Contact;
import com.tp.auth.model.User;
import com.tp.auth.service.ContactService;

import javax.swing.*;
import java.awt.*;

public class ContactDialog extends JDialog {

    private final JTextField nomField = new JTextField(20);
    private final JTextField prenomField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JTextField telephoneField = new JTextField(20);
    private final JLabel errorLabel = new JLabel(" ");
    private final ContactService contactService = new ContactService();

    private final User user;
    private final Contact contact;
    private boolean saved = false;

    public ContactDialog(JFrame parent, User user, Contact contact) {
        super(parent, true);

        this.user = user;
        this.contact = contact;

        setTitle(contact == null ? "Ajouter un contact" : "Modifier un contact");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        form.add(new JLabel("Nom :"));
        form.add(nomField);

        form.add(new JLabel("Prénom :"));
        form.add(prenomField);

        form.add(new JLabel("Email :"));
        form.add(emailField);

        form.add(new JLabel("Téléphone :"));
        form.add(telephoneField);

        form.add(new JLabel("Erreur :"));
        errorLabel.setForeground(Color.RED);
        form.add(errorLabel);

        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        JPanel buttons = new JPanel();
        buttons.add(saveButton);
        buttons.add(cancelButton);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        if (contact != null) {
            nomField.setText(contact.getNom());
            prenomField.setText(contact.getPrenom());
            emailField.setText(contact.getEmail());
            telephoneField.setText(contact.getTelephone());
        }

        saveButton.addActionListener(e -> saveContact());
        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    private void saveContact() {
        Contact c;

        if (contact == null) {
            c = new Contact(
                user.getId(),
                nomField.getText(),
                prenomField.getText(),
                emailField.getText(),
                telephoneField.getText()
            );
        } else {
            contact.setNom(nomField.getText());
            contact.setPrenom(prenomField.getText());
            contact.setEmail(emailField.getText());
            contact.setTelephone(telephoneField.getText());
            c = contact;
        }

        String error = contactService.save(c);

        if (error != null) {
            errorLabel.setText(error);
            pack();
            return;
        }

        saved = true;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }
}
