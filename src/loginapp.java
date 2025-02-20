import javax.swing.SwingUtilities;

import View.LoginView;
import Model.LoginModel;
import Controlleur.LoginControlleur;

public class loginapp {
	
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            // Créer le modèle
	        	LoginModel model = new LoginModel();

	            // Créer la vue
	            LoginView view = new LoginView();
	            view.setVisible(true);

	            // Créer le contrôleur
	            new LoginControlleur(view, model);
	        });
	    }
	

}
