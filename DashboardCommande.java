import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardCommande extends JFrame {

    public DashboardCommande() {
        setTitle("Dashboard");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // ⚡ HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(getWidth(), 70));

        JLabel titleLabel = new JLabel("CORSIBUTTEGA", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(66, 133, 244));
        header.add(titleLabel, BorderLayout.CENTER);

        JButton btnAjouter = new JButton("➕ Ajouter Client");
        
        btnAjouter.setFont(new Font("Serif", Font.BOLD, 14));
        btnAjouter.setBackground(new Color(66, 133, 244));
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.setPreferredSize(new Dimension(180, 40));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(btnAjouter);
        header.add(rightPanel, BorderLayout.EAST);

        // ⚡ SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(66, 133, 244));
        sidebar.setPreferredSize(new Dimension(210, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        sidebar.add(createSidebarButton("🏠 Accueil"));
        sidebar.add(createSidebarButton("👤 Clients"));
        sidebar.add(createSidebarButton("🛒 Commandes "));
        sidebar.add(createSidebarButton("🚪 Déconnexion"));

        // ⚡ TABLEAU PRINCIPAL
        String[] columnNames = {"Numéro Client", "Nom", "Prénom", "Commande", "Prix Total (€)"};
        Object[][] data = {
            {"001", "Dupont", "Jean", new CommandePanel(), "20.00"},
            {"002", "Martin", "Sophie", new CommandePanel(), "20.00"},
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(100); // Augmenter la hauteur de ligne

        // ✅ Ajuster la largeur des colonnes
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // Numéro Client
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Nom
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // Prénom
        table.getColumnModel().getColumn(3).setPreferredWidth(650); // 🛒 Commande (Grande colonne)
        table.getColumnModel().getColumn(4).setPreferredWidth(50); // Prix Total (€)

        table.getColumnModel().getColumn(3).setCellRenderer(new CommandeRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(sidebar, BorderLayout.WEST);
        getContentPane().add(header, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Serif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(66, 133, 244));
        button.setMinimumSize(new Dimension(210, 50));
        button.setMaximumSize(new Dimension(210, 50));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }
    private JPanel clientsPanel = new JPanel();
    


    class CommandePanel extends JPanel {
        public CommandePanel() {
            setLayout(new BorderLayout());

            String[] columnNames = {"N° Produit", "Nom Produit", "Quantité", "Prix (€)", "Actions"};
            Object[][] data = {
                {"P001", "Pain", "2", "5.00", ""},
                {"P002", "Fromage", "1", "10.00", ""},
            };

            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 4;
                }
            };

            JTable table = new JTable(model);
            table.setRowHeight(30);
            table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
            table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor());

            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
        }
    }
    private void openAddClientDialog(DefaultTableModel model) {
        // Créer une boîte de dialogue
        JDialog dialog = new JDialog(this, "Ajouter un Client", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setLocationRelativeTo(this);
    
        // Champs pour saisir les informations du client
        JTextField txtNumero = new JTextField();
        JTextField txtNom = new JTextField();
        JTextField txtPrenom = new JTextField();
    
        // Boutons
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnAnnuler = new JButton("Annuler");
    
        // Ajouter les composants au dialogue
        dialog.add(new JLabel("Numéro:"));
        dialog.add(txtNumero);
        dialog.add(new JLabel("Nom:"));
        dialog.add(txtNom);
        dialog.add(new JLabel("Prénom:"));
        dialog.add(txtPrenom);
        dialog.add(new JLabel()); // Espace vide
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnAnnuler);
        dialog.add(buttonPanel);
    
        // Action pour le bouton "Ajouter"
        btnAjouter.addActionListener(e -> {
            String numero = txtNumero.getText().trim();
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
    
            if (!numero.isEmpty() && !nom.isEmpty() && !prenom.isEmpty()) {
                // Ajouter les informations dans le tableau principal
                model.addRow(new Object[]{numero, nom, prenom, new CommandePanel(), "0.00"});
                
                // Mettre à jour la liste des clients dans le menu de gauche
                addClientToSidebar(numero, nom, prenom);
    
                dialog.dispose(); // Fermer le dialogue
            } else {
                JOptionPane.showMessageDialog(dialog, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        // Action pour le bouton "Annuler"
        btnAnnuler.addActionListener(e -> dialog.dispose());
    
        dialog.setVisible(true);
    }
    private void addClientToSidebar(String numero, String nom, String prenom) {
        JButton clientButton = new JButton(numero + " - " + nom + " " + prenom);
        clientButton.setFont(new Font("Serif", Font.PLAIN, 14));
        clientButton.setForeground(Color.WHITE);
        clientButton.setBackground(new Color(66, 133, 244));
        clientButton.setHorizontalAlignment(SwingConstants.LEFT);
        clientButton.setBorderPainted(false);
        clientButton.setFocusPainted(false);
        clientButton.setMaximumSize(new Dimension(200, 30));
    
        // Ajouter l'action au bouton si nécessaire (ex : afficher les détails du client)
        clientButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Détails du client:\nNuméro: " + numero + "\nNom: " + nom + "\nPrénom: " + prenom, 
                "Informations Client", JOptionPane.INFORMATION_MESSAGE);
        });
    
        // Ajouter le bouton à la liste des clients
        clientsPanel.add(clientButton);
        clientsPanel.revalidate();
        clientsPanel.repaint();
    }
    

    class CommandeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof CommandePanel) {
                return (CommandePanel) value;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton btnModifier, btnSupprimer, btnAjouter;
    
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            btnModifier = createButton("✏️");
            btnSupprimer = createButton("🗑");
            btnAjouter = createButton("➕");
            add(btnModifier);
            add(btnSupprimer);
            add(btnAjouter);
        }
    
        private JButton createButton(String text) {
            JButton button = new JButton(text);
            button.setPreferredSize(new Dimension(40, 30));
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setEnabled(false); // Renderer should not allow interaction
            return button;
        }
    
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Highlight row if selected
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(table.getBackground());
            }
            return this;
        }
    }
    
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton btnModifier, btnSupprimer, btnAjouter;
        private int row;
    
        public ButtonEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            btnModifier = createButton("✏️", "Modifier");
            btnSupprimer = createButton("🗑", "Supprimer");
            btnAjouter = createButton("➕", "Ajouter");
            panel.add(btnModifier);
            panel.add(btnSupprimer);
            panel.add(btnAjouter);
        }
    
        private JButton createButton(String text, String action) {
            JButton button = new JButton(text);
            button.setPreferredSize(new Dimension(40, 30));
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.addActionListener(e -> handleAction(action));
            return button;
        }
    
        private void handleAction(String action) {
            System.out.println(action + " sur la ligne " + row);
            fireEditingStopped(); // Stop editing when action is triggered
        }
    
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row; // Track the row being edited
            panel.setBackground(table.getSelectionBackground());
            return panel;
        }
    
        @Override
        public Object getCellEditorValue() {
            return panel;
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardCommande().setVisible(true));
    }
    
}
