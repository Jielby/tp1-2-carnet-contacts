
package com.tp.auth.model;

public class Contact {

    private int id;
    private int userId;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    public Contact(int id, int userId, String nom, String prenom,
                   String email, String telephone) {
        this.id = id;
        this.userId = userId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    public Contact(int userId, String nom, String prenom,
                   String email, String telephone) {
        this(0, userId, nom, prenom, email, telephone);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
