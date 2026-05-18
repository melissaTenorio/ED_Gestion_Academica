package com.mycompany.gestion_academica_ed.ui;

import com.mycompany.gestion_academica_ed.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class PanelCalificaciones extends JPanel {

    private final AppContext ctx;
    private DefaultTableModel tableModel;

    public PanelCalificaciones(AppContext ctx) {
        this.ctx = ctx;
        setBackground(Tema.BG_MAIN);
        setLayout(new BorderLayout(0, 16));
        setBorder(new EmptyBorder(24, 24, 24, 24));
        build();
    }

    private void build() {
        // ── Top ───────────────────────────────────────────────────────────────
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel title = new JLabel("Calificaciones");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Tema.TEXT_PRIMARY);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);

        Componentes.DarkButton btnAgregar = new Componentes.DarkButton("+ Agregar calificación");
        Componentes.DarkButton btnDeshacer = new Componentes.DarkButton("↩ Deshacer", Tema.ACCENT_ORANGE);

        btnAgregar.addActionListener(e -> dialogoAgregarCalif());
        btnDeshacer.addActionListener(e -> deshacer());

        btns.add(btnDeshacer);
        btns.add(btnAgregar);

        top.add(title, BorderLayout.WEST);
        top.add(btns, BorderLayout.EAST);

        // ── Tabla ─────────────────────────────────────────────────────────────
        String[] cols = {"Matrícula", "Nombre", "Calificaciones", "Promedio", "Estado"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tableModel);
        styleTable(table);

        Componentes.CardPanel card = new Componentes.CardPanel();
        card.setLayout(new BorderLayout(0, 8));
        card.add(Componentes.sectionTitle("Registro de calificaciones"), BorderLayout.NORTH);
        card.add(Componentes.darkScroll(table), BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(card, BorderLayout.CENTER);

        refresh();
    }

    private void styleTable(JTable t) {
        t.setBackground(Tema.BG_CARD);
        t.setForeground(Tema.TEXT_PRIMARY);
        t.setFont(Tema.FONT_BODY);
        t.setRowHeight(36);
        t.setShowVerticalLines(false);
        t.setGridColor(Tema.BORDER);
        t.setSelectionBackground(new Color(0x1A3A32));
        t.setSelectionForeground(Tema.ACCENT_GREEN);

        JTableHeader h = t.getTableHeader();
        h.setBackground(new Color(0x222222));
        h.setForeground(Tema.TEXT_SECONDARY);
        h.setFont(Tema.FONT_BOLD);
        h.setReorderingAllowed(false);

        DefaultTableCellRenderer r = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable tbl, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                if (!sel) {
                    setBackground(row % 2 == 0 ? Tema.BG_CARD : new Color(0x2F2F2F));
                    setForeground(Tema.TEXT_PRIMARY);
                }
                if (col == 3 && val != null && !val.toString().equals("—")) {
                    try {
                        double p = Double.parseDouble(val.toString());
                        setForeground(promedioColor(p));
                    } catch (NumberFormatException ignore) {}
                }
                if (col == 4 && val != null) {
                    String s = val.toString();
                    setForeground(s.equals("Aprobado") ? Tema.ACCENT_GREEN :
                                  s.equals("En riesgo") ? Tema.ACCENT_ORANGE : Tema.ACCENT_RED);
                }
                return this;
            }
        };
        for (int i = 0; i < t.getColumnCount(); i++)
            t.getColumnModel().getColumn(i).setCellRenderer(r);

        t.getColumnModel().getColumn(0).setPreferredWidth(90);
        t.getColumnModel().getColumn(2).setPreferredWidth(200);
        t.getColumnModel().getColumn(3).setPreferredWidth(80);
        t.getColumnModel().getColumn(4).setPreferredWidth(90);
    }

    public void refresh() {
        tableModel.setRowCount(0);
        Arreglo_Dinamico lista = ctx.getBst().obtenerEstudiantes();
        for (int i = 0; i < lista.obtenerTamaño(); i++) {
            Estudiante e = (Estudiante) lista.obtener(i);
            double prom = e.calcularPromedio();
            String estado = prom == 0 ? "Sin califs." : prom >= 7 ? "Aprobado" : prom >= 6 ? "En riesgo" : "Reprobado";
            tableModel.addRow(new Object[]{
                e.getMatricula(),
                e.getNomCompleto(),
                formatCalificaciones(e),
                prom > 0 ? String.format("%.1f", prom) : "—",
                estado
            });
        }
    }

    private String formatCalificaciones(Estudiante e) {
        Arreglo_Dinamico califs = e.getCalificaciones();
        if (califs.obtenerTamaño() == 0) return "Sin calificaciones";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < califs.obtenerTamaño(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(califs.obtener(i));
        }
        return sb.toString();
    }

    private void dialogoAgregarCalif() {
        JDialog d = new JDialog(SwingUtilities.getWindowAncestor(this), "Agregar Calificación", Dialog.ModalityType.APPLICATION_MODAL);
        d.getContentPane().setBackground(Tema.BG_CARD);
        d.setResizable(false);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Tema.BG_CARD);
        form.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JTextField fMat   = styledField("Ej: A12345");
        JTextField fCalif = styledField("0.0 - 10.0");

        addFormRow(form, gc, 0, "Matrícula del estudiante", fMat);
        addFormRow(form, gc, 1, "Calificación (0-10)", fCalif);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setBackground(Tema.BG_CARD);
        Componentes.DarkButton btnC = new Componentes.DarkButton("Cancelar", new Color(0x4B4B4B));
        Componentes.DarkButton btnS = new Componentes.DarkButton("Enviar solicitud");
        btnC.addActionListener(e -> d.dispose());
        btnS.addActionListener(e -> {
            String mat = fMat.getText().trim();
            Estudiante est = ctx.getBst().buscar(mat);
            if (est == null) {
                JOptionPane.showMessageDialog(d, "Estudiante no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double cal;
            try {
                cal = Double.parseDouble(fCalif.getText().trim());
                if (cal < 0 || cal > 10) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(d, "Calificación inválida (0-10).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ctx.getSistemaCalificaciones().enviarSolicitud(new SolicitudCalificacion(est, cal));
            ctx.registrarActividad("Solicitud enviada: " + est.getNomCompleto() + " → " + cal);
            JOptionPane.showMessageDialog(d, "Solicitud enviada a la cola.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            d.dispose();
        });
        btns.add(btnC);
        btns.add(btnS);

        gc.gridx = 0; gc.gridy = 2; gc.gridwidth = 2;
        form.add(btns, gc);

        d.add(form);
        d.pack();
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }

    private void deshacer() {
        ctx.getSistemaCalificaciones().deshacerUltimaAccion();
        ctx.reconstruirAVL();
        ctx.registrarActividad("Acción deshecha");
        refresh();
        JOptionPane.showMessageDialog(this, "Última acción deshecha.", "Deshacer", JOptionPane.INFORMATION_MESSAGE);
    }

    private JTextField styledField(String placeholder) {
        JTextField f = new JTextField(22);
        f.setBackground(Tema.BG_INPUT);
        f.setForeground(Tema.TEXT_MUTED);
        f.setCaretColor(Tema.TEXT_PRIMARY);
        f.setFont(Tema.FONT_BODY);
        f.setText(placeholder);
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Tema.BORDER, 1, true),
            new EmptyBorder(7, 10, 7, 10)
        ));
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (f.getText().equals(placeholder)) { f.setText(""); f.setForeground(Tema.TEXT_PRIMARY); }
            }
            @Override public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) { f.setForeground(Tema.TEXT_MUTED); f.setText(placeholder); }
            }
        });
        return f;
    }

    private void addFormRow(JPanel form, GridBagConstraints gc, int row, String label, JComponent field) {
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0; gc.gridwidth = 1;
        JLabel lbl = new JLabel(label);
        lbl.setFont(Tema.FONT_BODY);
        lbl.setForeground(Tema.TEXT_SECONDARY);
        form.add(lbl, gc);
        gc.gridx = 1; gc.weightx = 1;
        form.add(field, gc);
    }

    private Color promedioColor(double p) {
        if (p >= 9) return Tema.ACCENT_GREEN;
        if (p >= 7) return Tema.ACCENT_BLUE;
        if (p >= 6) return Tema.ACCENT_ORANGE;
        return Tema.ACCENT_RED;
    }
}
