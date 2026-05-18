package com.mycompany.gestion_academica_ed.ui;

import com.mycompany.gestion_academica_ed.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class PanelPrincipal extends JPanel {

    private final AppContext ctx;
    private Componentes.StatCard cardEstudiantes;
    private Componentes.StatCard cardCursos;
    private Componentes.StatCard cardSolicitudes;
    private Componentes.StatCard cardPromedio;
    private JPanel panelAVL;
    private JPanel panelActividad;

    public PanelPrincipal(AppContext ctx) {
        this.ctx = ctx;
        setLayout(new BorderLayout(0, 20));
        setBackground(Tema.BG_MAIN);
        setBorder(new EmptyBorder(24, 24, 24, 24));
        build();
    }

    private void build() {
        // ── Stats Row ──────────────────────────────────────────────────────────
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 14, 0));
        statsRow.setOpaque(false);

        cardEstudiantes = new Componentes.StatCard("Estudiantes\nregistrados", "0", Tema.ACCENT_BLUE);
        cardCursos      = new Componentes.StatCard("Cursos\nactivos", "0", Tema.ACCENT_BLUE);
        cardSolicitudes = new Componentes.StatCard("Solicitudes\npendientes", "0", Tema.ACCENT_ORANGE);
        cardPromedio    = new Componentes.StatCard("Promedio\ngeneral", "—", Tema.TEXT_PRIMARY);

        // Multi-line title hack: use HTML
        setCardTitle(cardEstudiantes, "Estudiantes registrados");
        setCardTitle(cardCursos, "Cursos activos");
        setCardTitle(cardSolicitudes, "Solicitudes pendientes");
        setCardTitle(cardPromedio, "Promedio general");

        statsRow.add(cardEstudiantes);
        statsRow.add(cardCursos);
        statsRow.add(cardSolicitudes);
        statsRow.add(cardPromedio);

        // ── Bottom Row ────────────────────────────────────────────────────────
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 14, 0));
        bottomRow.setOpaque(false);

        // Mejores promedios (AVL)
        Componentes.CardPanel cardAVL = new Componentes.CardPanel();
        cardAVL.setLayout(new BorderLayout(0, 10));

        JLabel lblAVL = Componentes.sectionTitle("Mejores promedios (árbol AVL)");
        cardAVL.add(lblAVL, BorderLayout.NORTH);

        panelAVL = new JPanel();
        panelAVL.setLayout(new BoxLayout(panelAVL, BoxLayout.Y_AXIS));
        panelAVL.setOpaque(false);
        mostrarAVLVacio();
        cardAVL.add(Componentes.darkScroll(panelAVL), BorderLayout.CENTER);

        // Actividad reciente
        Componentes.CardPanel cardAct = new Componentes.CardPanel();
        cardAct.setLayout(new BorderLayout(0, 10));

        JLabel lblAct = Componentes.sectionTitle("Actividad reciente");
        cardAct.add(lblAct, BorderLayout.NORTH);

        panelActividad = new JPanel();
        panelActividad.setLayout(new BoxLayout(panelActividad, BoxLayout.Y_AXIS));
        panelActividad.setOpaque(false);
        mostrarActividadVacia();
        cardAct.add(Componentes.darkScroll(panelActividad), BorderLayout.CENTER);

        bottomRow.add(cardAVL);
        bottomRow.add(cardAct);

        add(statsRow, BorderLayout.NORTH);
        add(bottomRow, BorderLayout.CENTER);
    }

    private void setCardTitle(Componentes.StatCard card, String title) {
        // Override the title label in the card (it's the first component)
        for (Component c : card.getComponents()) {
            if (c instanceof JLabel) {
                ((JLabel) c).setText("<html><span style='line-height:1.4'>" + title + "</span></html>");
                break;
            }
        }
    }

    private void mostrarAVLVacio() {
        panelAVL.removeAll();
        JLabel lbl = new JLabel("Agrega estudiantes con calificaciones", SwingConstants.CENTER);
        lbl.setFont(Tema.FONT_BODY);
        lbl.setForeground(Tema.TEXT_MUTED);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelAVL.add(Box.createVerticalGlue());
        panelAVL.add(lbl);
        panelAVL.add(Box.createVerticalGlue());
        panelAVL.revalidate();
        panelAVL.repaint();
    }

    private void mostrarActividadVacia() {
        panelActividad.removeAll();
        JLabel lbl = new JLabel("Sin actividad aún", SwingConstants.CENTER);
        lbl.setFont(Tema.FONT_BODY);
        lbl.setForeground(Tema.TEXT_MUTED);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelActividad.add(Box.createVerticalGlue());
        panelActividad.add(lbl);
        panelActividad.add(Box.createVerticalGlue());
        panelActividad.revalidate();
        panelActividad.repaint();
    }

    public void refresh() {
        // Actualizar stats
        int nEst = ctx.getBst().obtenerEstudiantes().obtenerTamaño();
        int nCur = ctx.getCursos().getTamaño();
        int nSol = ctx.getCantidadSolicitudes();

        cardEstudiantes.setValue(String.valueOf(nEst));
        cardCursos.setValue(String.valueOf(nCur));
        cardSolicitudes.setValue(String.valueOf(nSol));

        // Promedio general
        Arreglo_Dinamico ordenados = ctx.getAvl().obtenerEstudiantesOrdenados();
        if (ordenados.obtenerTamaño() > 0) {
            double suma = 0;
            int n = ordenados.obtenerTamaño();
            for (int i = 0; i < n; i++) {
                Estudiante e = (Estudiante) ordenados.obtener(i);
                suma += e.calcularPromedio();
            }
            cardPromedio.setValue(String.format("%.1f", suma / n));
            cardPromedio.getValueLabel().setForeground(promedioColor(suma / n));

            // Mostrar top AVL (de mayor a menor)
            panelAVL.removeAll();
            int top = Math.min(n, 8);
            for (int i = n - 1; i >= n - top; i--) {
                Estudiante e = (Estudiante) ordenados.obtener(i);
                panelAVL.add(avlRow(i == n - 1 ? 1 : n - i, e));
                panelAVL.add(Box.createVerticalStrut(4));
            }
            panelAVL.revalidate();
            panelAVL.repaint();
        } else {
            cardPromedio.setValue("—");
            cardPromedio.getValueLabel().setForeground(Tema.TEXT_PRIMARY);
            mostrarAVLVacio();
        }

        // Actividad reciente desde pila
        List<String> actividad = ctx.getActividad();
        if (actividad.isEmpty()) {
            mostrarActividadVacia();
        } else {
            panelActividad.removeAll();
            int max = Math.min(actividad.size(), 8);
            for (int i = actividad.size() - 1; i >= actividad.size() - max; i--) {
                panelActividad.add(actRow(actividad.get(i)));
                panelActividad.add(Box.createVerticalStrut(4));
            }
            panelActividad.revalidate();
            panelActividad.repaint();
        }
    }

    private JPanel avlRow(int rank, Estudiante e) {
        JPanel p = new JPanel(new BorderLayout(10, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        JLabel rankLbl = new JLabel("#" + rank);
        rankLbl.setFont(Tema.FONT_BOLD);
        rankLbl.setForeground(Tema.ACCENT_GREEN);
        rankLbl.setPreferredSize(new Dimension(28, 20));

        JLabel nameLbl = new JLabel(e.getNomCompleto());
        nameLbl.setFont(Tema.FONT_BODY);
        nameLbl.setForeground(Tema.TEXT_PRIMARY);

        JLabel promLbl = new JLabel(String.format("%.1f", e.calcularPromedio()));
        promLbl.setFont(Tema.FONT_BOLD);
        promLbl.setForeground(promedioColor(e.calcularPromedio()));

        p.add(rankLbl, BorderLayout.WEST);
        p.add(nameLbl, BorderLayout.CENTER);
        p.add(promLbl, BorderLayout.EAST);
        return p;
    }

    private JPanel actRow(String msg) {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel dot = new JLabel("●");
        dot.setForeground(Tema.ACCENT_GREEN);
        dot.setFont(new Font("Segoe UI", Font.PLAIN, 8));

        JLabel lbl = new JLabel(msg);
        lbl.setFont(Tema.FONT_SMALL);
        lbl.setForeground(Tema.TEXT_SECONDARY);

        p.add(dot, BorderLayout.WEST);
        p.add(lbl, BorderLayout.CENTER);
        return p;
    }

    private Color promedioColor(double prom) {
        if (prom >= 9) return Tema.ACCENT_GREEN;
        if (prom >= 7) return Tema.ACCENT_BLUE;
        if (prom >= 6) return Tema.ACCENT_ORANGE;
        return Tema.ACCENT_RED;
    }
}
