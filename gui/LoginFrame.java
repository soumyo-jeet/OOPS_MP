package gui;

import managers.UserManager;
import models.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final UserManager userManager;

    private final JTextField usernameField = Theme.styleField(new JTextField());
    private final JPasswordField passwordField = Theme.styleField(new JPasswordField());
    private final JButton loginButton = Theme.primaryButton("Log In");
    private final JButton registerLink = Theme.linkButton("Don't have an account? Register");
    private final JLabel messageLabel = new JLabel(" ");

    public LoginFrame(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Expense Tracker - Log In");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel card = Theme.createCard();

        JLabel title = new JLabel("Welcome back");
        title.setFont(Theme.TITLE);
        title.setForeground(Theme.TEXT);
        messageLabel.setFont(Theme.SMALL);

        Theme.addRow(card, title, 0, 0);
        Theme.addRow(card, Theme.muted("Log in to manage your expenses"), 1, 4);
        Theme.addRow(card, Theme.label("Username"), 2, 24);
        Theme.addRow(card, usernameField, 3, 6);
        Theme.addRow(card, Theme.label("Password"), 4, 14);
        Theme.addRow(card, passwordField, 5, 6);
        Theme.addRow(card, messageLabel, 6, 12);
        Theme.addRow(card, loginButton, 7, 8);
        Theme.addRow(card, registerLink, 8, 8);

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Theme.BACKGROUND);
        background.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        background.add(card);

        setContentPane(background);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        loginButton.addActionListener(e -> onLogin());
        registerLink.addActionListener(e -> openRegister());
        getRootPane().setDefaultButton(loginButton);   // Enter key clicks Log In
    }

    private void onLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please enter your username and password.", Theme.ERROR);
            return;
        }

        try {
            // authenticateUser returns the user, or throws if the login is wrong
            User user = userManager.authenticateUser(new User(username, password));
            new MainFrame(user, userManager).setVisible(true);
            dispose();
        } catch (RuntimeException ex) {
            passwordField.setText("");
            showMessage(ex.getMessage(), Theme.ERROR);
        }
    }

    private void openRegister() {
        RegisterDialog dialog = new RegisterDialog(this, userManager);
        dialog.setVisible(true);   // blocks until the dialog closes

        String created = dialog.getCreatedUsername();
        if (created != null) {
            usernameField.setText(created);
            passwordField.setText("");
            passwordField.requestFocusInWindow();
            showMessage("Account created. Please log in.", Theme.SUCCESS);
        }
    }

    private void showMessage(String text, Color color) {
        messageLabel.setText(text);
        messageLabel.setForeground(color);
    }
}