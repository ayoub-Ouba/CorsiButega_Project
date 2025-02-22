import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import org.mindrot.jbcrypt.BCrypt;
 

public class login extends JFrame {

    private static final long serialVersionUID = 1L;
    private db_authentification dbService = new db_authentification();


    public login() {
        setTitle("Login");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal divisé en 2 parties
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        add(mainPanel, BorderLayout.CENTER);

        // Partie gauche (Bleue avec le logo)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(66, 133, 244)); 
        leftPanel.setLayout(new GridBagLayout()); 
        JLabel logo = new JLabel("CORSIBUTEGGA");
        logo.setFont(new Font("Serif", Font.BOLD, 25));
        logo.setForeground(Color.WHITE);
        leftPanel.add(logo);
        mainPanel.add(leftPanel);

        // Partie droite (Blanche avec le formulaire)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Benvinutu");
        title.setFont(new Font("Serif", Font.BOLD, 25));
        rightPanel.add(title, gbc);

        gbc.gridy++;
        JLabel subtitle = new JLabel("Connecter à votre compte");
        subtitle.setFont(new Font("Serif", Font.PLAIN, 13));
        rightPanel.add(subtitle, gbc);

        // Champ de texte pour l'email avec placeholder
        gbc.gridy++;
        JTextField emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(400, 35));
        emailField.setBorder(new RoundedBorder(10, "I"));
        emailField.setForeground(Color.GRAY);
        emailField.setText("Email"); 

        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("Email")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setForeground(Color.GRAY);
                    emailField.setText("Email");
                }
            }
        });

        rightPanel.add(emailField, gbc);

        // Champ de mot de passe avec placeholder
        gbc.gridy++;
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(400, 35));
        passwordField.setBorder(new RoundedBorder(10, "I"));

        rightPanel.add(passwordField, gbc);

        // Bouton de connexion
        gbc.gridy++;
        JButton loginButton = new JButton("Connexion");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(new RoundedBorder(10, "B"));
        loginButton.setBackground(new Color(66, 133, 244));

        // Effet de survol pour le bouton
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(52, 120, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(66, 133, 244));
            }
        });

        // ActionListener pour le bouton de connexion
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || email.equals("Email")) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un email valide.");
                    return;
                }

                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un mot de passe.");
                    return;
                }

                // Authentification
                if (dbService.authenticate(email, password)) {
                    JOptionPane.showMessageDialog(null, "Connexion réussie !");
                } else {
                     
                    JOptionPane.showMessageDialog(null, "Email ou mot de passe incorrect.");
                }
            }
        });

        rightPanel.add(loginButton, gbc);
        mainPanel.add(rightPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new login().setVisible(true);
        });
    }

    // Classe pour créer une bordure arrondie
    class RoundedBorder implements Border {
        private int radius;
        private String type_input;

        public RoundedBorder(int radius, String type_input) {
            this.radius = radius;
            this.type_input = type_input;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (type_input.equals("B")) {
                g2d.setColor(new Color(66, 133, 244)); // Couleur bleue pour le bouton
            } else {
                g2d.setColor(new Color(220, 236, 255)); // Couleur claire pour les champs de texte
            }

            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
    }
}