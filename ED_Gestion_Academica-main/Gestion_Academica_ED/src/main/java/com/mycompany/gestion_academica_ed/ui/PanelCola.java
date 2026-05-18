package com.mycompany.gestion_academica_ed.ui;

import com.mycompany.gestion_academica_ed.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class PanelCola extends JPanel {

    private final AppContext ctx;
    private JPanel panelItems;
    private JLabel lblCount;
    private Runnable onProcessed; // callback para refrescar otras pantallas

    public PanelCola(AppContext ctx) {
        this.ctx = ctx;
        setBackground(Tema.BG_MAIN);
        setLayout(new BorderLayout(0, 16));
        setBorder(new EmptyBorder(24, 24, 24, 24));
        build();
    }

    public void setOnProcessed(Runnable r) { this.onProcessed = r; }

    private void build() {
        // ── Top ───────────────────────────────────────────────────────────────
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        titleRow.setOpaque(false);

        JLabel title = new JLabel("Cola de Solicitudes");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Tema.TEXT_PRIMARY);

        lblCount = new JLabel("0 pendientes");
        lblCount.setFont(Tema.FONT_BODY);
        lblCount.setForeground(Tema.ACCENT_ORANGE);

        titleRow.add(title);
        titleRow.add(lblCount);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);

        Componentes.DarkButton btnProcesar = new Componentes.DarkButton("▶ Procesar siguiente", Tema.ACCENT_GREEN);
        Componentes.DarkButton btnTodos    = new Componentes.DarkButton("Procesar todos", Tema.ACCENT_BLUE);

        btnProcesar.addActionListener(e -> procesarSiguiente());
        btnTodos.addActionListener(e -> procesarTodos());

        btns.add(btnProcesar);
        btns.add(btnTodos);

        top.add(titleRow, BorderLayout.WEST);
        top.add(btns, BorderLayout.EAST);

        // ── Contenido ─────────────────────────────────────────────────────────
        Componentes.CardPanel card = new Componentes.CardPanel();
        card.setLayout(new BorderLayout(0, 10));

        JPanel cardTop = new JPanel(new BorderLayout());
        cardTop.setOpaque(false);
        cardTop.add(Componentes.sectionTitle("Solicitudes en cola (FIFO)"), BorderLayout.WEST);

        JLabel hint = new JLabel("Las solicitudes se procesan en orden de llegada");
        hint.setFont(Tema.FONT_SMALL);
        hint.setForeground(Tema.TEXT_MUTED);
        cardTop.add(hint, BorderLayout.EAST);

        card.add(cardTop, BorderLayout.NORTH);

        panelItems = new JPanel();
        panelItems.setLayout(new BoxLayout(panelItems, BoxLayout.Y_AXIS));
        panelItems.setOpaque(false);

        card.add(Componentes.darkScroll(panelItems), BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(card, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        panelItems.removeAll();

        // Obtener solicitudes de la cola
        int cant = ctx.getSistemaCalificaciones().getCantidadEnCola();
        lblCount.setText(cant + " pendiente" + (cant != 1 ? "s" : ""));

        java.util.List<SolicitudCalificacion> lista = ctx.getSistemaCalificaciones().verSolicitudes();

        if (lista.isEmpty()) {
            JLabel empty = new JLabel("No hay solicitudes pendientes", SwingConstants.CENTER);
            empty.setFont(Tema.FONT_BODY);
            empty.setForeground(Tema.TEXT_MUTED);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelItems.add(Box.createVerticalGlue());
            panelItems.add(empty);
            panelItems.add(Box.createVerticalGlue());
        } else {
            int i = 1;
            for (SolicitudCalificacion s : lista) {
                panelItems.add(itemSolicitud(i++, s));
                panelItems.add(Box.createVerticalStrut(8));
            }
        }

        panelItems.revalidate();
        panelItems.repaint();
    }

    private JPanel itemSolicitud(int pos, SolicitudCalificacion s) {
        JPanel p = new JPanel(new BorderLayout(12, 0));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        p.setPreferredSize(new Dimension(100, 58));
        p.setOpaque(false);

        // Número de posición
        JLabel num = new JLabel(String.valueOf(pos));
        num.setFont(new Font("Segoe UI", Font.BOLD, 16));
        num.setForeground(pos == 1 ? Tema.ACCENT_GREEN : Tema.TEXT_MUTED);
        num.setPreferredSize(new Dimension(36, 40));
        num.setHorizontalAlignment(SwingConstants.CENTER);

        // Info
        JPanel info = new JPanel(new GridLayout(2, 1, 0, 2));
        info.setOpaque(false);

        JLabel nombre = new JLabel(s.getEstudiante().getNomCompleto() + "  (" + s.getEstudiante().getMatricula() + ")");
        nombre.setFont(Tema.FONT_BOLD);
        nombre.setForeground(Tema.TEXT_PRIMARY);

        JLabel detalle = new JLabel("Nueva calificación: " + s.getNuevaCalificacion());
        detalle.setFont(Tema.FONT_SMALL);
        detalle.setForeground(Tema.TEXT_SECONDARY);

        info.add(nombre);
        info.add(detalle);

        // Badge "Siguiente"
        JLabel badge = new JLabel(pos == 1 ? "Siguiente" : "#" + pos);
        badge.setFont(Tema.FONT_SMALL);
        badge.setForeground(pos == 1 ? Tema.ACCENT_GREEN : Tema.TEXT_MUTED);
        badge.setHorizontalAlignment(SwingConstants.RIGHT);

        // Fondo de la fila
        JPanel row = new JPanel(new BorderLayout(12, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(pos == 1 ? new Color(0x1A3020) : new Color(0x2A2A2A));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                if (pos == 1) {
                    g2.setColor(Tema.ACCENT_GREEN);
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(10, 14, 10, 14));
        row.add(num, BorderLayout.WEST);
        row.add(info, BorderLayout.CENTER);
        row.add(badge, BorderLayout.EAST);

        p.add(row, BorderLayout.CENTER);
        return p;
    }

    private void procesarSiguiente() {
        if (ctx.getSistemaCalificaciones().getCantidadEnCola() == 0) {
            JOptionPane.showMessageDialog(this, "No hay solicitudes en la cola.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ctx.getSistemaCalificaciones().procesarSiguiente();
        ctx.reconstruirAVL();
        ctx.registrarActividad("Solicitud de calificacion procesada");
        refresh();
        if (onProcessed != null) onProcessed.run();
    }

    private void procesarTodos() {
        int cant = ctx.getSistemaCalificaciones().getCantidadEnCola();
        if (cant == 0) {
            JOptionPane.showMessageDialog(this, "No hay solicitudes en la cola.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for (int i = 0; i < cant; i++) {
            ctx.getSistemaCalificaciones().procesarSiguiente();
        }
        ctx.reconstruirAVL();
        ctx.registrarActividad(cant + " solicitudes procesadas");
        refresh();
        if (onProcessed != null) onProcessed.run();
        JOptionPane.showMessageDialog(this, cant + " solicitudes procesadas.", "Listo", JOptionPane.INFORMATION_MESSAGE);
    }
}
