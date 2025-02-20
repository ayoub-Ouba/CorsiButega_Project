package Controlleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.LoginModel;
import View.LoginView;

public class LoginControlleur{
    private LoginView view;
    private LoginModel model;

    public LoginControlleur(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;

        // Ajouter un écouteur pour le bouton de connexion
        this.view.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = view.getEmail();
                String password = view.getPassword();

                if (email.isEmpty() || email.equals("Email")) {
                    view.showMessage("Veuillez entrer un email valide.");
                    return;
                }

                if (password.isEmpty()) {
                    view.showMessage("Veuillez entrer un mot de passe.");
                    return;
                }

                // Authentification via le modèle
                if (model.authenticate(email, password)) {
                    view.showMessage("Connexion réussie !");
                } else {
                    view.showMessage("Email ou mot de passe incorrect.");
                }
            }
        });
    }
}