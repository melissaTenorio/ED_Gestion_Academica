package com.mycompany.gestion_academica_ed.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Componentes {

    // ── Card Panel redondeado ──────────────────────────────────────────────────
    public static class CardPanel extends JPanel {
        private final int radius;
        private Color bgColor;

        public CardPanel(int radius) {
            this.radius = radius;
            this.bgColor = Tema.BG_CARD;
            setOpaque(false);
            setBorder(new EmptyBorder(16, 16, 16, 16));
        }

        public CardPanel() { this(14); }

        public void setBgColor(Color c) { this.bgColor = c; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setColor(Tema.BORDER);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ── Stat Card (Panel principal stats) ────────────────────────────────────
    public static class StatCard extends CardPanel {
        private final JLabel lblTitle;
        private final JLabel lblValue;

        public StatCard(String title, String value, Color valueColor) {
            super(14);
            setLayout(new BorderLayout(0, 8));
            setPreferredSize(new Dimension(150, 100));

            lblTitle = new JLabel(title);
            lblTitle.setFont(Tema.FONT_SMALL);
            lblTitle.setForeground(Tema.TEXT_SECONDARY);

            lblValue = new JLabel(value);
            lblValue.setFont(Tema.FONT_NUM);
            lblValue.setForeground(valueColor);

            add(lblTitle, BorderLayout.NORTH);
            add(lblValue, BorderLayout.CENTER);
        }

        public void setValue(String v) { lblValue.setText(v); }
        public JLabel getValueLabel()  { return lblValue; }
    }

    // ── Botón estilizado ──────────────────────────────────────────────────────
    public static class DarkButton extends JButton {
        private final Color bg;
        private final Color bgHover;

        public DarkButton(String text, Color bg) {
            super(text);
            this.bg = bg;
            this.bgHover = bg.brighter();
            setFont(Tema.FONT_BOLD);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(8, 18, 8, 18));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { repaint(); }
                @Override public void mouseExited(MouseEvent e)  { repaint(); }
            });
        }

        public DarkButton(String text) { this(text, Tema.ACCENT_GREEN); }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color c = getModel().isRollover() ? bgHover : bg;
            g2.setColor(c);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ── TextField oscuro ──────────────────────────────────────────────────────
    public static class DarkField extends JTextField {
        public DarkField(String placeholder) {
            super(20);
            setBackground(Tema.BG_INPUT);
            setForeground(Tema.TEXT_PRIMARY);
            setCaretColor(Tema.TEXT_PRIMARY);
            setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Tema.BORDER, 1, true),
                new EmptyBorder(7, 12, 7, 12)
            ));
            setFont(Tema.FONT_BODY);
            setOpaque(true);

            // Placeholder
            setText(placeholder);
            setForeground(Tema.TEXT_MUTED);
            addFocusListener(new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) {
                    if (getText().equals(placeholder)) {
                        setText("");
                        setForeground(Tema.TEXT_PRIMARY);
                    }
                }
                @Override public void focusLost(FocusEvent e) {
                    if (getText().isEmpty()) {
                        setForeground(Tema.TEXT_MUTED);
                        setText(placeholder);
                    }
                }
            });
        }

        public String getValor(String placeholder) {
            String t = getText();
            return t.equals(placeholder) ? "" : t;
        }
    }

    // ── Label título de sección ───────────────────────────────────────────────
    public static JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 15));
        l.setForeground(Tema.TEXT_PRIMARY);
        return l;
    }

    // ── Separador oscuro ──────────────────────────────────────────────────────
    public static JSeparator separator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(Tema.BORDER);
        sep.setBackground(Tema.BORDER);
        return sep;
    }

    // ── ScrollPane sin borde ──────────────────────────────────────────────────
    public static JScrollPane darkScroll(JComponent inner) {
        JScrollPane sp = new JScrollPane(inner);
        sp.setBorder(null);
        sp.setBackground(Tema.BG_MAIN);
        sp.getViewport().setBackground(Tema.BG_MAIN);
        sp.getVerticalScrollBar().setBackground(Tema.BG_CARD);
        sp.getHorizontalScrollBar().setBackground(Tema.BG_CARD);
        sp.getVerticalScrollBar().setUI(new DarkScrollBarUI());
        return sp;
    }

    // ── ScrollBar UI oscura ────────────────────────────────────────────────────
    static class DarkScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override protected void configureScrollBarColors() {
            thumbColor   = new Color(0x4B4B4B);
            trackColor   = Tema.BG_CARD;
        }
        @Override protected JButton createDecreaseButton(int o) { return emptyBtn(); }
        @Override protected JButton createIncreaseButton(int o) { return emptyBtn(); }
        private JButton emptyBtn() {
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(0,0));
            return b;
        }
    }

    // ── Avatar circular con iniciales ─────────────────────────────────────────
    public static class AvatarLabel extends JLabel {
        private final String initials;
        private final Color color;

        public AvatarLabel(String initials, Color color) {
            this.initials = initials;
            this.color    = color;
            setPreferredSize(new Dimension(36, 36));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillOval(0, 0, 35, 35);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
            FontMetrics fm = g2.getFontMetrics();
            int x = (35 - fm.stringWidth(initials)) / 2;
            int y = (35 - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(initials, x, y);
            g2.dispose();
        }
    }

    // ── NavItem para sidebar ──────────────────────────────────────────────────
    public static class NavItem extends JPanel {
        private boolean selected = false;
        private final JLabel label;
        private Runnable onClick;

        public NavItem(String text, String icon) {
            setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));
            setOpaque(true);
            setBackground(Tema.BG_SIDEBAR);
            setPreferredSize(new Dimension(240, 42));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel ico = new JLabel(icon);
            ico.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            ico.setForeground(Tema.TEXT_SECONDARY);

            label = new JLabel(text);
            label.setFont(Tema.FONT_SIDEBAR);
            label.setForeground(Tema.TEXT_SECONDARY);

            add(ico);
            add(label);

            addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) {
                    if (onClick != null) onClick.run();
                }
                @Override public void mouseEntered(MouseEvent e) {
                    if (!selected) setBackground(new Color(0x363636));
                }
                @Override public void mouseExited(MouseEvent e) {
                    if (!selected) setBackground(Tema.BG_SIDEBAR);
                }
            });
        }

        public void setSelected(boolean s) {
            selected = s;
            setBackground(s ? Tema.BG_SIDEBAR_SEL : Tema.BG_SIDEBAR);
            label.setForeground(s ? Tema.ACCENT_GREEN : Tema.TEXT_SECONDARY);
        }

        public void setOnClick(Runnable r) { this.onClick = r; }
    }
}
