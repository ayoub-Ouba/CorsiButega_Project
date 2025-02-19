	import javax.swing.*;
	import java.awt.*;

public class dashbord_commande extends JFrame {

	    public dashbord_commande() {
	        setTitle("Dashboard");
	        setSize(950, 700);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        getContentPane().setLayout(new BorderLayout());
	        
	        // ⚡ Header
	        JPanel header = new JPanel();
	        header.setBackground(Color.WHITE);
	        header.setPreferredSize(new Dimension(getWidth(), 70));
	        header.setLayout(new BorderLayout());
	        
	        JPanel panel_logo = new JPanel();
	        panel_logo.setBackground(new Color(66, 133, 244));
	        panel_logo.setPreferredSize(new Dimension(210, 10));
	        panel_logo.setLayout(new GridBagLayout()); // Pour centrer le texte
	        
	        JLabel logoLabel=new JLabel("CORSIBUTEGGA");
	        logoLabel.setFont(new Font("Serif", Font.BOLD, 20)); // Taille et style du texte
	        logoLabel.setForeground(Color.WHITE); // Couleur du texte
	        panel_logo.add(logoLabel);
	        
	        header.add(panel_logo, BorderLayout.WEST);
	        

	        // ⚡ Sidebar (Menu latéral)
	        JPanel sidebar = new JPanel();
	        sidebar.setBackground(new Color(66, 133, 244)); // Bleu
	        sidebar.setPreferredSize(new Dimension(210, getHeight()));
	        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS)); 
	      

	        JButton btnHome = createSidebarButton("🏠 Accueil");
	        JButton btnProfile = createSidebarButton("👤 Clients");
	        JButton btnSettings = createSidebarButton("⚙ Commandes ");
	        JButton btnLogout = createSidebarButton("🚪 Déconnexion");

	        sidebar.add(btnHome);
	        sidebar.add(btnProfile);
	        sidebar.add(btnSettings);
	        sidebar.add(btnLogout);

	     

	        

	        // ⚡ Zone de Contenu
	        JPanel contentPanel = new JPanel();
	        //contentPanel.setBackground(Color.WHITE);
	        //contentPanel.setLayout(new GridLayout(2, 2, 10, 10));

	        // Ajout de cartes dans le Dashboard
	        //contentPanel.add(createCard("📊 Statistiques", "Données récentes"));
	        //contentPanel.add(createCard("📅 Calendrier", "Événements à venir"));
	        //contentPanel.add(createCard("🔔 Notifications", "Aucune nouvelle alerte"));
	        //contentPanel.add(createCard("💬 Messages", "3 nouveaux messages"));

	        // 🔥 Ajout des composants principaux
	        getContentPane().add(sidebar, BorderLayout.WEST);
	        getContentPane().add(header, BorderLayout.NORTH);
	        getContentPane().add(contentPanel, BorderLayout.CENTER);
	     
	    }

	    // 📌 Méthode pour créer un bouton
	    private JButton createSidebarButton(String text) {
	        JButton button = new JButton(text);
	        button.setFont(new Font("Serif", Font.BOLD, 14));
	        button.setForeground(Color.WHITE);
	        button.setBackground(new Color(66, 133, 244)); 
	        button.setMinimumSize(new Dimension(210, 50));
	        button.setMaximumSize(new Dimension(210, 50));
	        button.setBorderPainted(false);
	        button.setFocusPainted(false);
	        
	        button.addMouseListener(new java.awt.event.MouseAdapter() {
	            @Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                button.setBackground(new Color(50, 120, 230));
	            }

	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                button.setBackground(new Color(66, 133, 244));
	            }
	        });
	        return button;
	    }

	    
	    // 🎨 Méthode pour créer une carte
	    private JPanel createCard(String title, String description) {
	        JPanel card = new JPanel();
	        card.setPreferredSize(new Dimension(150, 100));
	        card.setBorder(BorderFactory.createLineBorder(new Color(66, 133, 244), 2));
	        card.setLayout(new BorderLayout());
	        card.setBackground(Color.WHITE);

	        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
	        titleLabel.setFont(new Font("Serif", Font.BOLD, 16));

	        JLabel descLabel = new JLabel(description, JLabel.CENTER);
	        descLabel.setFont(new Font("Serif", Font.PLAIN, 12));

	        card.add(titleLabel, BorderLayout.NORTH);
	        card.add(descLabel, BorderLayout.CENTER);

	        return card;
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new dashbord_commande().setVisible(true));
	    }
	}

