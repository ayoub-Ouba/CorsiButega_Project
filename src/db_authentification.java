import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class db_authentification {
    private static final String URL = "jdbc:mysql://localhost:3306/corsibtega_test";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static void main(String[] args) {
       
    }
    public boolean authenticate(String email, String password) {
        String query = "SELECT password_hash FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password_hash");
                return BCrypt.checkpw(password, hashedPassword);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public  boolean insertUser(String email, String password) {
        // Hacher le mot de passe
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        // Afficher le mot de passe haché pour vérification
        System.out.println("Mot de passe haché : " + hashedPassword);

        // Appel à la méthode pour insérer l'utilisateur avec le mot de passe haché dans la base de données
        // Requête SQL corrigée avec des paramètres de type placeholder
        String query = "INSERT INTO `users`(`email`, `password_hash`, `type`) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            // Ajouter l'email, le mot de passe haché et le type dans la requête
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, "admin");

            // Exécuter la requête
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
