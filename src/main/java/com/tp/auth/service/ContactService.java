
package com.tp.auth.service;

import com.tp.auth.dao.ContactDao;
import com.tp.auth.model.Contact;

import java.util.List;

public class ContactService {

    private final ContactDao contactDao = new ContactDao();

    public List<Contact> findByUser(int userId) throws Exception {
        return contactDao.findByUser(userId);
    }

    public List<Contact> search(int userId, String text) throws Exception {
        if (text == null || text.isBlank()) {
            return findByUser(userId);
        }
        return contactDao.search(userId, text.trim());
    }

    public String save(Contact contact) {
        if (contact.getNom() == null || contact.getNom().trim().isEmpty()) {
            return "Le nom est obligatoire";
        }

        if (contact.getPrenom() == null || contact.getPrenom().trim().isEmpty()) {
            return "Le prénom est obligatoire";
        }

        String email = contact.getEmail();
        if (email != null && !email.isBlank()
                && (!email.contains("@") || !email.contains("."))) {
            return "Email invalide";
        }

        String telephone = contact.getTelephone();
        if (telephone != null && !telephone.isBlank()
                && !telephone.matches("\\+?[0-9 ]+")) {
            return "Téléphone invalide";
        }

        try {
            contact.setNom(contact.getNom().trim());
            contact.setPrenom(contact.getPrenom().trim());

            if (contact.getEmail() != null) {
                contact.setEmail(contact.getEmail().trim());
            }
            if (contact.getTelephone() != null) {
                contact.setTelephone(contact.getTelephone().trim());
            }

            if (contact.getId() == 0) {
                contactDao.insert(contact);
            } else {
                contactDao.update(contact);
            }

            return null;
        } catch (Exception e) {
            return "Erreur lors de l'enregistrement : " + e.getMessage();
        }
    }

    public void delete(int id, int userId) throws Exception {
        contactDao.delete(id, userId);
    }
}
