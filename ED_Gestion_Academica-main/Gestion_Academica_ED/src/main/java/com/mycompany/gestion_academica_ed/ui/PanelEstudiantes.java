package com.mycompany.gestion_academica_ed.ui;

import com.mycompany.gestion_academica_ed.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class PanelEstudiantes extends JPanel {

    private final AppContext ctx;
    private DefaultTableModel tableModel;
    private JTable table;
    private Componentes.DarkField txtBuscar;

    public PanelEstudiantes(AppContext ctx) {
        this.ctx = ctx;
        setBackground(Tema.BG_MAIN);
        setLayout(new BorderLayout(0, 16));
        setBorder(new EmptyBorder(24, 24, 24, 24));
        build();
    }

    private void build() {
        // ── Top bar ───────────────────────────────────────────────────────────
        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.setOpaque(false);

        JLabel title = new JLabel("Estudiantes");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Tema.TEXT_PRIMARY);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        acciones.setOpaque(false);

        txtBuscar = new Componentes.DarkField("Buscar por matrícula...");
        txtBuscar.setPreferredSize(new Dimension(220, 34));

        Componentes.DarkButton btnBuscar = new Componentes.DarkButton("Buscar", Tema.ACCENT_BLUE);
        Componentes.DarkButton btnAgregar = new Componentes.DarkButton("+ Agregar");

        btnBuscar.addActionListener(e -> buscar());
        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());

        acciones.add(txtBuscar);
        acciones.add(btnBuscar);
        acciones.add(btnAgregar);

        top.add(title, BorderLayout.WEST);
        top.add(acciones, BorderLayout.EAST);

        // ── Tabla ─────────────────────────────────────────────────────────────
        String[] cols = {"Matrícula", "Nombre completo", "Teléfono", "Correo", "Dirección", "Promedio"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scroll = Componentes.darkScroll(table);

        Componentes.CardPanel card = new Componentes.CardPanel();
        card.setLayout(new BorderLayout());
        card.add(scroll, BorderLayout.CENTER);

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
        t.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = t.getTableHeader();
        header.setBackground(new Color(0x222222));
        header.setForeground(Tema.TEXT_SECONDARY);
        header.setFont(Tema.FONT_BOLD);
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        // Renderer con padding
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, val, sel, foc, r, c);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                if (!sel) {
                    setBackground(r % 2 == 0 ? Tema.BG_CARD : new Color(0x2F2F2F));
                    setForeground(Tema.TEXT_PRIMARY);
                }
                // Color promedio
                if (c == 5 && val != null) {
                    try {
                        double p = Double.parseDouble(val.toString());
                        setForeground(promedioColor(p));
                    } catch (NumberFormatException ignore) {}
                }
                return this;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(90);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
    }

    public void refresh() {
        tableModel.setRowCount(0);
        Arreglo_Dinamico lista = ctx.getBst().obtenerEstudiantes();
        for (int i = 0; i < lista.obtenerTamaño(); i++) {
            Estudiante e = (Estudiante) lista.obtener(i);
            double prom = e.calcularPromedio();
            tableModel.addRow(new Object[]{
                e.getMatricula(),
                e.getNomCompleto(),
                e.getTelefono(),
                e.getCorreo(),
                e.getDireccion(),
                prom > 0 ? String.format("%.1f", prom) : "—"
            });
        }
    }

    private void buscar() {
        String mat = txtBuscar.getValor("Buscar por matrícula...");
        if (mat.isEmpty()) { refresh(); return; }
        Estudiante e = ctx.getBst().buscar(mat);
        tableModel.setRowCount(0);
        if (e != null) {
            double prom = e.calcularPromedio();
            tableModel.addRow(new Object[]{
                e.getMatricula(), e.getNomCompleto(), e.getTelefono(),
                e.getCorreo(), e.getDireccion(),
                prom > 0 ? String.format("%.1f", prom) : "—"
            });
        } else {
            JOptionPane.showMessageDialog(this,
                "No se encontró estudiante con matrícula: " + mat,
                "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarDialogoAgregar() {
        JDialog dialog = crearDialog("Agregar Estudiante");
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Tema.BG_CARD);
        form.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Matrícula *", "Nombre completo *", "Teléfono", "Correo", "Dirección"};
        String[] placeholders = {"Ej: A12345", "Ej: Juan Pérez", "Ej: 6441234567", "Ej: juan@correo.com", "Ej: Av. Principal 123"};
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(Tema.FONT_BODY);
            lbl.setForeground(Tema.TEXT_SECONDARY);

            fields[i] = new JTextField(22);
            fields[i].setBackground(Tema.BG_INPUT);
            fields[i].setForeground(Tema.TEXT_PRIMARY);
            fields[i].setCaretColor(Tema.TEXT_PRIMARY);
            fields[i].setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Tema.BORDER, 1, true),
                new EmptyBorder(7, 10, 7, 10)
            ));
            fields[i].setFont(Tema.FONT_BODY);

            gc.gridx = 0; gc.gridy = i; gc.weightx = 0;
            form.add(lbl, gc);
            gc.gridx = 1; gc.weightx = 1;
            form.add(fields[i], gc);
        }

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setBackground(Tema.BG_CARD);

        Componentes.DarkButton btnCancel = new Componentes.DarkButton("Cancelar", new Color(0x4B4B4B));
        Componentes.DarkButton btnSave   = new Componentes.DarkButton("Guardar");

        btnCancel.addActionListener(e -> dialog.dispose());
        btnSave.addActionListener(e -> {
            String mat  = fields[0].getText().trim();
            String nom  = fields[1].getText().trim();
            String tel  = fields[2].getText().trim();
            String cor  = fields[3].getText().trim();
            String dir  = fields[4].getText().trim();

            if (mat.isEmpty() || nom.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Matrícula y nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (ctx.getBst().buscar(mat) != null) {
                JOptionPane.showMessageDialog(dialog, "Ya existe un estudiante con esa matrícula.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Estudiante est = new Estudiante(mat, nom, tel, cor, dir);
            ctx.agregarEstudiante(est);
            refresh();
            dialog.dispose();
        });

        btnPanel.add(btnCancel);
        btnPanel.add(btnSave);

        gc.gridx = 0; gc.gridy = labels.length; gc.gridwidth = 2;
        gc.anchor = GridBagConstraints.EAST;
        form.add(btnPanel, gc);

        dialog.add(form);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JDialog crearDialog(String titulo) {
        JDialog d = new JDialog(SwingUtilities.getWindowAncestor(this), titulo, Dialog.ModalityType.APPLICATION_MODAL);
        d.getContentPane().setBackground(Tema.BG_CARD);
        d.setResizable(false);
        return d;
    }

    private Color promedioColor(double p) {
        if (p >= 9) return Tema.ACCENT_GREEN;
        if (p >= 7) return Tema.ACCENT_BLUE;
        if (p >= 6) return Tema.ACCENT_ORANGE;
        return Tema.ACCENT_RED;
    }
}
