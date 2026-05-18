package com.mycompany.gestion_academica_ed.ui;

import java.awt.*;

public class Tema {
    // Colores principales (dark theme como en la imagen)
    public static final Color BG_SIDEBAR      = new Color(0x2D2D2D);
    public static final Color BG_MAIN         = new Color(0x1E1E1E);
    public static final Color BG_CARD         = new Color(0x2A2A2A);
    public static final Color BG_CARD_HOVER   = new Color(0x323232);
    public static final Color BG_TOPBAR       = new Color(0x252525);
    public static final Color BG_INPUT        = new Color(0x383838);
    public static final Color BG_SIDEBAR_SEL  = new Color(0x1A3A32);

    public static final Color ACCENT_GREEN    = new Color(0x22C55E);
    public static final Color ACCENT_BLUE     = new Color(0x3B82F6);
    public static final Color ACCENT_ORANGE   = new Color(0xF59E0B);
    public static final Color ACCENT_RED      = new Color(0xEF4444);

    public static final Color TEXT_PRIMARY    = new Color(0xE5E5E5);
    public static final Color TEXT_SECONDARY  = new Color(0x9CA3AF);
    public static final Color TEXT_MUTED      = new Color(0x6B7280);

    public static final Color BORDER          = new Color(0x3F3F3F);
    public static final Color SIDEBAR_SEL_TEXT= new Color(0x22C55E);

    // Fuentes
    public static final Font FONT_TITLE   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BOLD    = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_NUM     = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FONT_HEADER  = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font FONT_SIDEBAR = new Font("Segoe UI", Font.PLAIN, 13);

    public static void applyGlobalDefaults() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}
        UIManager.put("Panel.background", BG_MAIN);
        UIManager.put("Label.foreground", TEXT_PRIMARY);
        UIManager.put("TextField.background", BG_INPUT);
        UIManager.put("TextField.foreground", TEXT_PRIMARY);
        UIManager.put("TextField.caretForeground", TEXT_PRIMARY);
        UIManager.put("TextField.border", BorderFactory.createEmptyBorder(6, 10, 6, 10));
        UIManager.put("ScrollPane.background", BG_MAIN);
        UIManager.put("ScrollBar.background", BG_CARD);
        UIManager.put("ScrollBar.thumb", new Color(0x4B4B4B));
        UIManager.put("ScrollBar.thumbHighlight", new Color(0x555555));
        UIManager.put("ScrollBar.thumbShadow", new Color(0x3A3A3A));
        UIManager.put("ScrollBar.track", BG_CARD);
        UIManager.put("Table.background", BG_CARD);
        UIManager.put("Table.foreground", TEXT_PRIMARY);
        UIManager.put("Table.gridColor", BORDER);
        UIManager.put("Table.selectionBackground", new Color(0x1A3A32));
        UIManager.put("Table.selectionForeground", ACCENT_GREEN);
        UIManager.put("TableHeader.background", new Color(0x222222));
        UIManager.put("TableHeader.foreground", TEXT_SECONDARY);
        UIManager.put("OptionPane.background", BG_CARD);
        UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        UIManager.put("Button.background", ACCENT_GREEN);
        UIManager.put("Button.foreground", Color.WHITE);
    }
}
