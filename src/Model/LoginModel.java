package Model;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class LoginModel {
	    private static final String URL = "jdbc:mysql://localhost:3306/corsibuttega";
	    private static final String USER = "root";
	    private static final String PASSWORD = "";

	    //Fonction qui verifier l'authentification
	    public boolean authenticate(String email, String password) {
	        String query = "SELECT password FROM user WHERE Email = ?";
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, email);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                String hashedPassword = rs.getString("password");
	                return BCrypt.checkpw(password, hashedPassword);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    //Fonction pour ajouter des utilisateurs pour tester
	    public boolean insertUser(String email, String password) {
	        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
	        String query = "INSERT INTO `user`(`Nom_complet`, `Email`, `password`, `Type`) VALUES (?, ?, ?, ?)";

	        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, "ayoub oubakki");
	            stmt.setString(2, email);
	            stmt.setString(3, hashedPassword);
	            stmt.setString(4, "admin");

	            int rowsInserted = stmt.executeUpdate();
	            return rowsInserted > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	}


