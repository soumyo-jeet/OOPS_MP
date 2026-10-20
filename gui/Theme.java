package gui;

import javax.swing.*;
import java.awt.*;

public final class Theme {

    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color PRIMARY_HOVER = new Color(59, 130, 246);
    public static final Color PRIMARY_DARK = new Color(29, 78, 216);
    public static final Color BACKGROUND = new Color(243, 244, 246);
    public static final Color CARD = Color.WHITE;
    public static final Color BORDER = new Color(209, 213, 219);
    public static final Color TEXT = new Color(17, 24, 39);
    public static final Color MUTED = new Color(107, 114, 128);
    public static final Color ERROR = new Color(220, 38, 38);
    public static final Color SUCCESS = new Color(22, 163, 74);

    public static final Font TITLE = new Font("SansSerif", Font.BOLD, 22);
    public static final Font BODY = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font LABEL = new Font("SansSerif", Font.BOLD, 13);
    public static final Font SMALL = new Font("SansSerif", Font.PLAIN, 12);

    private Theme() {}

    /** Call once at startup, before creating any window. */
    public static void install() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
            // Fall back to the default look and feel
        }
    }

    /** White card with a subtle border and padding. */
    public static JPanel createCard() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(32, 36, 32, 36)));
        return card;
    }

    /** Adds a component on its own full-width row inside a GridBagLayout panel. */
    public static void addRow(JPanel panel, Component c, int row, int topInset) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = row;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(topInset, 0, 0, 0);
        panel.add(c, g);
    }

    public static <T extends JTextField> T styleField(T field) {
        field.setFont(BODY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        field.setColumns(20);
        return field;
    }

    public static JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(LABEL);
        l.setForeground(TEXT);
        return l;
    }

    public static JLabel muted(String text) {
        JLabel l = new JLabel(text);
        l.setFont(BODY);
        l.setForeground(MUTED);
        return l;
    }

    /** Solid blue button with rounded corners and hover/pressed colors. */
    public static JButton primaryButton(String text) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(PRIMARY_DARK);
                } else if (getModel().isRollover()) {
                    g2.setColor(PRIMARY_HOVER);
                } else {
                    g2.setColor(PRIMARY);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return b;
    }

    /** Text-only button that looks like a hyperlink. */
    public static JButton linkButton(String text) {
        JButton b = new JButton(text);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setForeground(PRIMARY);
        b.setFont(BODY);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}