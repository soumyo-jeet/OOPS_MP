package gui;

import managers.UserManager;

import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {

    private final UserManager userManager;
    private final JTextField usernameField = Theme.styleField(new JTextField());
    private final JPasswordField passwordField = Theme.styleField(new JPasswordField());
    private final JLabel messageLabel = new JLabel(" ");
    private String createdUsername;

    public RegisterDialog(JFrame owner, UserManager userManager) {
        super(owner, "Create Account", true);
        this.userManager = userManager;

        JPanel card = Theme.createCard();

        JLabel title = new JLabel("Create your account");
        title.setFont(Theme.TITLE);
        title.setForeground(Theme.TEXT);

        JButton createButton = Theme.primaryButton("Create Account");
        JButton cancelButton = Theme.linkButton("Cancel");
        messageLabel.setFont(Theme.SMALL);

        Theme.addRow(card, title, 0, 0);
        Theme.addRow(card, Theme.muted("Choose a username and password"), 1, 4);
        Theme.addRow(card, Theme.label("Username"), 2, 24);
        Theme.addRow(card, usernameField, 3, 6);
        Theme.addRow(card, Theme.label("Password"), 4, 14);
        Theme.addRow(card, passwordField, 5, 6);
        Theme.addRow(card, messageLabel, 6, 12);
        Theme.addRow(card, createButton, 7, 8);
        Theme.addRow(card, cancelButton, 8, 8);

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Theme.BACKGROUND);
        background.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        background.add(card);

        setContentPane(background);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);

        createButton.addActionListener(e -> onCreate());
        cancelButton.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(createButton);
    }

    /** @return the username that was created, or null if the user cancelled. */
    public String getCreatedUsername() {
        return createdUsername;
    }

    private void onCreate() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.length() < 3) {
            showMessage("Username must be at least 3 characters.", Theme.ERROR);
            return;
        }
        if (!username.matches("[A-Za-z0-9_.]+")) {
            showMessage("Username can only use letters, numbers, dots and underscores.", Theme.ERROR);
            return;
        }
        if (password.length() < 6) {
            showMessage("Password must be at least 6 characters.", Theme.ERROR);
            return;
        }

        try {
            userManager.addNewUser(username, password);   // throws if the name is taken
            createdUsername = username;
            dispose();
        } catch (RuntimeException ex) {
            showMessage(ex.getMessage(), Theme.ERROR);
        }
    }

    private void showMessage(String text, Color color) {
        messageLabel.setText(text);
        messageLabel.setForeground(color);
    }
}