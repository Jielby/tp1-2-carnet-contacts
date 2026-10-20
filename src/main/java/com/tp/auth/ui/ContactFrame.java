
package com.tp.auth.ui;

import com.tp.auth.model.User;
import com.tp.auth.model.Contact;
import com.tp.auth.service.ContactService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ContactFrame extends JFrame {

    private final User user;
    private final ContactService contactService = new ContactService();
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField searchField = new JTextField(20);
    private List<Contact> contacts;

    public ContactFrame(User user) {
        this.user = user;

        setTitle("Mes contacts");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Connecté en tant que " + user.getUsername()),
                BorderLayout.WEST);

        JButton logoutButton = new JButton("Se déconnecter");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        top.add(logoutButton, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Rechercher :"));
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(e -> searchContacts());
        searchPanel.add(searchButton);

        tableModel = new DefaultTableModel(
                new String[]{"Nom", "Prénom", "Email", "Téléphone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        JPanel center = new JPanel(new BorderLayout());
        center.add(searchPanel, BorderLayout.NORTH);
        center.add(new JScrollPane(table), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        JButton addButton = new JButton("Ajouter");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");

        addButton.addActionListener(e -> openDialog(null));
        editButton.addActionListener(e -> editContact());
        deleteButton.addActionListener(e -> deleteContact());

        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);

        add(buttons, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        try {
            contacts = contactService.findByUser(user.getId());
            displayContacts(contacts);
        } catch (Exception e) {
            showError(e);
        }
    }

    private void displayContacts(List<Contact> list) {
        tableModel.setRowCount(0);

        for (Contact c : list) {
            tableModel.addRow(new Object[]{
                    c.getNom(),
                    c.getPrenom(),
                    c.getEmail(),
                    c.getTelephone()
            });
        }
    }

    private void searchContacts() {
        try {
            contacts = contactService.search(user.getId(), searchField.getText());
            displayContacts(contacts);
        } catch (Exception e) {
            showError(e);
        }
    }

    private void openDialog(Contact contact) {
        ContactDialog dialog = new ContactDialog(this, user, contact);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            searchField.setText("");
            refreshTable();
        }
    }

    private void editContact() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un contact");
            return;
        }

        openDialog(contacts.get(row));
    }

    private void deleteContact() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un contact");
            return;
        }

        Contact c = contacts.get(row);

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Supprimer " + c.getNom() + " " + c.getPrenom() + " ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                contactService.delete(c.getId(), user.getId());
                refreshTable();
            } catch (Exception e) {
                showError(e);
            }
        }
    }

    private void showError(Exception e) {
        JOptionPane.showMessageDialog(this,
                "Erreur : " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }
}
