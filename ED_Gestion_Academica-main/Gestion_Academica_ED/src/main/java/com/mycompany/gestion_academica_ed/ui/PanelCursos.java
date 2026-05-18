package com.mycompany.gestion_academica_ed.ui;

import com.mycompany.gestion_academica_ed.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class PanelCursos extends JPanel {

    private final AppContext ctx;
    private DefaultTableModel modelCursos;
    private DefaultTableModel modelInscritos;
    private JTable tableCursos;
    private JLabel lblRolInfo;

    public PanelCursos(AppContext ctx) {
        this.ctx = ctx;
        setBackground(Tema.BG_MAIN);
        setLayout(new BorderLayout(0, 16));
        setBorder(new EmptyBorder(24, 24, 24, 24));
        build();
    }

    private void build() {
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel title = new JLabel("Cursos");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Tema.TEXT_PRIMARY);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);

        Componentes.DarkButton btnAgregar   = new Componentes.DarkButton("+ Nuevo curso");
        Componentes.DarkButton btnEliminar  = new Componentes.DarkButton("Eliminar", Tema.ACCENT_RED);
        Componentes.DarkButton btnInscribir = new Componentes.DarkButton("Inscribir estudiante", Tema.ACCENT_BLUE);
        Componentes.DarkButton btnEspera    = new Componentes.DarkButton("Lista de espera", Tema.ACCENT_ORANGE);
        Componentes.DarkButton btnRol       = new Componentes.DarkButton("Rotar rol", new Color(0x7C3AED));

        btnAgregar.addActionListener(e -> dialogoNuevoCurso());
        btnEliminar.addActionListener(e -> eliminarCursoSeleccionado());
        btnInscribir.addActionListener(e -> dialogoInscribir());
        btnEspera.addActionListener(e -> dialogoListaEspera());
        btnRol.addActionListener(e -> rotarRol());

        btns.add(btnEspera);
        btns.add(btnRol);
        btns.add(btnEliminar);
        btns.add(btnInscribir);
        btns.add(btnAgregar);

        top.add(title, BorderLayout.WEST);
        top.add(btns, BorderLayout.EAST);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(420);
        split.setDividerSize(6);
        split.setBorder(null);
        split.setBackground(Tema.BG_MAIN);

        String[] colsCurso = {"ID", "Nombre", "Inscritos", "En espera"};
        modelCursos = new DefaultTableModel(colsCurso, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableCursos = new JTable(modelCursos);
        styleTable(tableCursos);
        tableCursos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) mostrarInscritos();
        });

        Componentes.CardPanel leftCard = new Componentes.CardPanel();
        leftCard.setLayout(new BorderLayout(0, 8));
        leftCard.add(Componentes.sectionTitle("Lista de cursos"), BorderLayout.NORTH);
        leftCard.add(Componentes.darkScroll(tableCursos), BorderLayout.CENTER);

        String[] colsIns = {"Matricula", "Nombre", "Promedio"};
        modelInscritos = new DefaultTableModel(colsIns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tableIns = new JTable(modelInscritos);
        styleTable(tableIns);

        lblRolInfo = new JLabel(" ");
        lblRolInfo.setFont(Tema.FONT_BODY);
        lblRolInfo.setForeground(new Color(0xA78BFA));
        lblRolInfo.setBorder(new EmptyBorder(4, 0, 0, 0));

        Componentes.CardPanel rightCard = new Componentes.CardPanel();
        rightCard.setLayout(new BorderLayout(0, 8));
        rightCard.add(Componentes.sectionTitle("Estudiantes inscritos"), BorderLayout.NORTH);
        rightCard.add(Componentes.darkScroll(tableIns), BorderLayout.CENTER);
        rightCard.add(lblRolInfo, BorderLayout.SOUTH);

        split.setLeftComponent(leftCard);
        split.setRightComponent(rightCard);

        add(top, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);

        refresh();
    }

    private void styleTable(JTable t) {
        t.setBackground(Tema.BG_CARD);
        t.setForeground(Tema.TEXT_PRIMARY);
        t.setFont(Tema.FONT_BODY);
        t.setRowHeight(34);
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
            @Override public Component getTableCellRendererComponent(JTable tbl, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                if (!sel) {
                    setBackground(row % 2 == 0 ? Tema.BG_CARD : new Color(0x2F2F2F));
                    setForeground(Tema.TEXT_PRIMARY);
                }
                if (col == 3 && val != null) {
                    try {
                        int n = Integer.parseInt(val.toString());
                        if (n > 0) setForeground(Tema.ACCENT_ORANGE);
                    } catch (NumberFormatException ignore) {}
                }
                return this;
            }
        };
        for (int i = 0; i < t.getColumnCount(); i++)
            t.getColumnModel().getColumn(i).setCellRenderer(r);
    }

    public void refresh() {
        modelCursos.setRowCount(0);
        NodoLista<Curso> nodo = ctx.getCursos().getCabeza();
        while (nodo != null) {
            Curso c = nodo.dato;
            modelCursos.addRow(new Object[]{
                c.getIdCurso(),
                c.getNombreCurso(),
                c.getInscritos().getTamaño() + "/30",
                c.getListaEspera().getTamaño()
            });
            nodo = nodo.getSiguiente();
        }
        mostrarInscritos();
    }

    private void mostrarInscritos() {
        modelInscritos.setRowCount(0);
        Curso c = getCursoSeleccionado();
        if (c == null) return;

        NodoLista<Estudiante> nodo = c.getInscritos().getCabeza();
        while (nodo != null) {
            Estudiante e = nodo.dato;
            double p = e.calcularPromedio();
            modelInscritos.addRow(new Object[]{
                e.getMatricula(), e.getNomCompleto(),
                p > 0 ? String.format("%.1f", p) : "sin califs."
            });
            nodo = nodo.getSiguiente();
        }
    }

    private Curso getCursoSeleccionado() {
        int row = tableCursos.getSelectedRow();
        if (row < 0) return null;
        String id = (String) modelCursos.getValueAt(row, 0);
        return ctx.getCursosDict().obtener(id);
    }

    // ── Nuevo curso ────────────────────────────────────────────────────────────
    private void dialogoNuevoCurso() {
        JDialog d = crearDialog("Nuevo Curso");
        JPanel form = formPanel();

        JTextField fId  = styledField("Ej: MAT101");
        JTextField fNom = styledField("Ej: Calculo Diferencial");
        addFormRow(form, 0, "ID del curso *", fId);
        addFormRow(form, 1, "Nombre del curso *", fNom);

        JPanel btns = botonesDialog(d);
        Componentes.DarkButton btnS = new Componentes.DarkButton("Crear");
        btnS.addActionListener(e -> {
            String id  = fId.getText().trim();
            String nom = fNom.getText().trim();
            if (id.isEmpty() || nom.isEmpty()) { error(d, "Todos los campos son obligatorios."); return; }
            if (ctx.getCursosDict().obtener(id) != null) { error(d, "Ya existe un curso con ese ID."); return; }
            Curso c = new Curso(id, nom);
            ctx.getCursos().agregarAlFinal(c);
            ctx.getCursosDict().insertar(id, c);
            ctx.registrarActividad("Curso creado: " + nom);
            refresh();
            d.dispose();
        });
        btns.add(btnS);
        addFooter(form, btns, 2);
        showDialog(d, form);
    }

    // ── Eliminar curso ─────────────────────────────────────────────────────────
    private void eliminarCursoSeleccionado() {
        Curso c = getCursoSeleccionado();
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un curso de la lista.", "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Eliminar el curso \"" + c.getNombreCurso() + "\"?",
            "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            ctx.eliminarCurso(c.getIdCurso());
            refresh();
        }
    }

    // ── Inscribir ──────────────────────────────────────────────────────────────
    private void dialogoInscribir() {
        if (ctx.getCursos().getTamaño() == 0) {
            JOptionPane.showMessageDialog(this, "No hay cursos disponibles.", "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JDialog d = crearDialog("Inscribir Estudiante");
        JPanel form = formPanel();

        JTextField fMat = styledField("Ej: A12345");
        JComboBox<String> combo = new JComboBox<>();
        combo.setBackground(Tema.BG_INPUT);
        combo.setForeground(Tema.TEXT_PRIMARY);
        combo.setFont(Tema.FONT_BODY);
        NodoLista<Curso> n = ctx.getCursos().getCabeza();
        while (n != null) {
            combo.addItem(n.dato.getIdCurso() + " - " + n.dato.getNombreCurso());
            n = n.getSiguiente();
        }

        addFormRow(form, 0, "Matricula del estudiante", fMat);
        addFormRow(form, 1, "Curso", combo);

        JPanel btns = botonesDialog(d);
        Componentes.DarkButton btnS = new Componentes.DarkButton("Inscribir", Tema.ACCENT_BLUE);
        btnS.addActionListener(e -> {
            String mat = fMat.getText().trim();
            Estudiante est = ctx.getBst().buscar(mat);
            if (est == null) { error(d, "Estudiante no encontrado."); return; }
            String sel = (String) combo.getSelectedItem();
            if (sel == null) return;
            String idCurso = sel.split(" - ")[0];
            Curso c = ctx.getCursosDict().obtener(idCurso);
            if (c == null) return;
            String msg = ctx.inscribirEstudiante(est, c);
            refresh();
            JOptionPane.showMessageDialog(d, msg, "Resultado", JOptionPane.INFORMATION_MESSAGE);
            d.dispose();
        });
        btns.add(btnS);
        addFooter(form, btns, 2);
        showDialog(d, form);
    }

    // ── Lista de espera (Lista Doble Circular) ─────────────────────────────────
    private void dialogoListaEspera() {
        Curso c = getCursoSeleccionado();
        if (c == null) {
            JOptionPane.showMessageDialog(this,
                "Selecciona un curso para ver su lista de espera.",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog d = new JDialog(SwingUtilities.getWindowAncestor(this),
            "Lista de espera - " + c.getNombreCurso(), Dialog.ModalityType.APPLICATION_MODAL);
        d.setSize(520, 400);
        d.setLocationRelativeTo(this);
        d.getContentPane().setBackground(Tema.BG_CARD);
        d.setLayout(new BorderLayout());

        JPanel topP = new JPanel(new BorderLayout(12, 0));
        topP.setOpaque(false);
        topP.setBorder(new EmptyBorder(14, 16, 8, 16));
        topP.add(Componentes.sectionTitle("Lista Doble Circular de Espera"), BorderLayout.WEST);
        JLabel cnt = new JLabel(c.getListaEspera().getTamaño() + " en espera");
        cnt.setFont(Tema.FONT_BODY);
        cnt.setForeground(Tema.ACCENT_ORANGE);
        topP.add(cnt, BorderLayout.EAST);

        String[] cols = {"#", "Matricula", "Nombre"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c2) { return false; }
        };
        JTable tabla = new JTable(model);
        styleTable(tabla);

        // Rellenar con recorrido hacia adelante por defecto
        llenarTablaEspera(model, c.getListaEspera().recorrerAdelante());

        JLabel dirLbl = new JLabel("Recorrido: adelante");
        dirLbl.setFont(Tema.FONT_SMALL);
        dirLbl.setForeground(Tema.TEXT_MUTED);

        JPanel navBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        navBtns.setOpaque(false);
        Componentes.DarkButton btnAd = new Componentes.DarkButton("Hacia adelante", Tema.ACCENT_BLUE);
        Componentes.DarkButton btnAt = new Componentes.DarkButton("Hacia atras", Tema.ACCENT_ORANGE);

        btnAd.addActionListener(e -> {
            llenarTablaEspera(model, c.getListaEspera().recorrerAdelante());
            dirLbl.setText("Recorrido: adelante");
        });
        btnAt.addActionListener(e -> {
            llenarTablaEspera(model, c.getListaEspera().recorrerAtras());
            dirLbl.setText("Recorrido: atras");
        });

        navBtns.add(btnAt);
        navBtns.add(dirLbl);
        navBtns.add(btnAd);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(0, 16, 0, 16));
        center.add(Componentes.darkScroll(tabla), BorderLayout.CENTER);
        center.add(navBtns, BorderLayout.SOUTH);

        d.add(topP, BorderLayout.NORTH);
        d.add(center, BorderLayout.CENTER);
        d.setVisible(true);
    }

    private void llenarTablaEspera(DefaultTableModel model, Arreglo_Dinamico datos) {
        model.setRowCount(0);
        for (int i = 0; i < datos.obtenerTamaño(); i++) {
            Estudiante e = (Estudiante) datos.obtener(i);
            model.addRow(new Object[]{i + 1, e.getMatricula(), e.getNomCompleto()});
        }
    }

    // ── Rotar rol (Lista Circular Simple) ─────────────────────────────────────
    private void rotarRol() {
        Curso c = getCursoSeleccionado();
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un curso para rotar el rol.",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String resultado = c.rotarRol();
        lblRolInfo.setText("  " + resultado);
        ctx.registrarActividad(resultado);
        JOptionPane.showMessageDialog(this, resultado, "Rotacion de rol",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // ── Helpers UI ─────────────────────────────────────────────────────────────
    private JDialog crearDialog(String titulo) {
        JDialog d = new JDialog(SwingUtilities.getWindowAncestor(this), titulo,
                Dialog.ModalityType.APPLICATION_MODAL);
        d.getContentPane().setBackground(Tema.BG_CARD);
        d.setResizable(false);
        return d;
    }

    private JPanel formPanel() {
        JPanel f = new JPanel(new GridBagLayout());
        f.setBackground(Tema.BG_CARD);
        f.setBorder(new EmptyBorder(20, 20, 20, 20));
        return f;
    }

    private void addFormRow(JPanel form, int row, String label, JComponent field) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0; gc.gridwidth = 1;
        JLabel lbl = new JLabel(label);
        lbl.setFont(Tema.FONT_BODY);
        lbl.setForeground(Tema.TEXT_SECONDARY);
        form.add(lbl, gc);
        gc.gridx = 1; gc.weightx = 1;
        form.add(field, gc);
    }

    private JPanel botonesDialog(JDialog d) {
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setBackground(Tema.BG_CARD);
        Componentes.DarkButton btnC = new Componentes.DarkButton("Cancelar", new Color(0x4B4B4B));
        btnC.addActionListener(e -> d.dispose());
        btns.add(btnC);
        return btns;
    }

    private void addFooter(JPanel form, JPanel btns, int row) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(12, 6, 0, 6);
        gc.gridx = 0; gc.gridy = row; gc.gridwidth = 2;
        gc.anchor = GridBagConstraints.EAST;
        form.add(btns, gc);
    }

    private void showDialog(JDialog d, JPanel form) {
        d.add(form);
        d.pack();
        d.setLocationRelativeTo(this);
        d.setVisible(true);
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
            new EmptyBorder(7, 10, 7, 10)));
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

    private void error(JDialog d, String msg) {
        JOptionPane.showMessageDialog(d, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
