package com.mycompany.gestion_academica_ed.ui;

import com.mycompany.gestion_academica_ed.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {

    private final AppContext ctx;
    private JPanel contentArea;
    private CardLayout cardLayout;

    private PanelPrincipal panelDashboard;
    private PanelEstudiantes panelEstudiantes;
    private PanelCursos panelCursos;
    private PanelCalificaciones panelCalificaciones;
    private PanelCola panelCola;

    private Componentes.NavItem navPanel, navEst, navCur, navCal, navCola;
    private Componentes.NavItem activeNav;

    private JLabel lblTopTitle;
    private JTextField txtSearchTop;

    public VentanaPrincipal() {
        ctx = new AppContext();
        Tema.applyGlobalDefaults();
        initUI();
    }

    private void initUI() {
        setTitle("GestiónAcadémica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 680));
        setSize(1200, 750);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Tema.BG_MAIN);
        setLayout(new BorderLayout(0, 0));

        add(buildSidebar(), BorderLayout.WEST);
        add(buildTopBar(),  BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);

        // Seleccionar Panel por defecto
        selectNav(navPanel, "Panel principal");
        setVisible(true);
    }

    // ── Sidebar ───────────────────────────────────────────────────────────────
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Tema.BG_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setLayout(new BorderLayout());

        // Logo
        JPanel logo = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        logo.setBackground(Tema.BG_SIDEBAR);
        logo.setPreferredSize(new Dimension(230, 64));

        JLabel logoIcon = new JLabel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Tema.ACCENT_GREEN);
                g2.fillRoundRect(0, 0, 34, 34, 8, 8);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
                g2.drawString("G", 10, 24);
                g2.dispose();
            }
        };
        logoIcon.setPreferredSize(new Dimension(34, 34));

        JLabel logoText = new JLabel("GestiónAcadémica");
        logoText.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoText.setForeground(Tema.TEXT_PRIMARY);

        logo.add(logoIcon);
        logo.add(logoText);

        // Nav items
        JPanel nav = new JPanel();
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBackground(Tema.BG_SIDEBAR);
        nav.setBorder(new EmptyBorder(8, 0, 0, 0));

        navPanel = new Componentes.NavItem("Panel", "□");
        navEst   = new Componentes.NavItem("Estudiantes", "□");
        navCur   = new Componentes.NavItem("Cursos", "□");
        navCal   = new Componentes.NavItem("Calificaciones", "□");
        navCola  = new Componentes.NavItem("Cola de solicitudes", "□");

        navPanel.setOnClick(() -> selectNav(navPanel, "Panel principal"));
        navEst.setOnClick(()   -> selectNav(navEst, "Estudiantes"));
        navCur.setOnClick(()   -> selectNav(navCur, "Cursos"));
        navCal.setOnClick(()   -> selectNav(navCal, "Calificaciones"));
        navCola.setOnClick(()  -> selectNav(navCola, "Cola de solicitudes"));

        nav.add(navPanel);
        nav.add(navEst);
        nav.add(navCur);
        nav.add(navCal);
        nav.add(navCola);
        nav.add(Box.createVerticalGlue());

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 12));
        footer.setBackground(Tema.BG_SIDEBAR);
        footer.setBorder(new MatteBorder(1, 0, 0, 0, Tema.BORDER));

        JLabel ayuda = new JLabel("欄  Ayuda ↗");
        ayuda.setFont(Tema.FONT_SMALL);
        ayuda.setForeground(Tema.TEXT_MUTED);
        footer.add(ayuda);

        sidebar.add(logo, BorderLayout.NORTH);
        sidebar.add(nav,  BorderLayout.CENTER);
        sidebar.add(footer, BorderLayout.SOUTH);

        // Borde derecho
        sidebar.setBorder(new MatteBorder(0, 0, 0, 1, Tema.BORDER));

        return sidebar;
    }

    // ── Top bar ───────────────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel top = new JPanel(new BorderLayout(0, 0));
        top.setBackground(Tema.BG_TOPBAR);
        top.setPreferredSize(new Dimension(0, 58));
        top.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, Tema.BORDER),
            new EmptyBorder(0, 20, 0, 20)
        ));

        lblTopTitle = new JLabel("Panel principal");
        lblTopTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblTopTitle.setForeground(Tema.TEXT_PRIMARY);

        // Search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false);

        txtSearchTop = new JTextField(18);
        txtSearchTop.setBackground(Tema.BG_INPUT);
        txtSearchTop.setForeground(Tema.TEXT_MUTED);
        txtSearchTop.setCaretColor(Tema.TEXT_PRIMARY);
        txtSearchTop.setFont(Tema.FONT_BODY);
        txtSearchTop.setText("Buscar estudiante...");
        txtSearchTop.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Tema.BORDER, 1, true),
            new EmptyBorder(6, 12, 6, 12)
        ));
        txtSearchTop.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (txtSearchTop.getText().equals("Buscar estudiante...")) {
                    txtSearchTop.setText("");
                    txtSearchTop.setForeground(Tema.TEXT_PRIMARY);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (txtSearchTop.getText().isEmpty()) {
                    txtSearchTop.setForeground(Tema.TEXT_MUTED);
                    txtSearchTop.setText("Buscar estudiante...");
                }
            }
        });
        txtSearchTop.addActionListener(e -> buscarGlobal());

        Componentes.AvatarLabel avatar = new Componentes.AvatarLabel("AD", Tema.ACCENT_GREEN);

        searchPanel.add(txtSearchTop);
        searchPanel.add(avatar);

        top.add(lblTopTitle,  BorderLayout.WEST);
        top.add(searchPanel,  BorderLayout.EAST);

        return top;
    }

    // ── Content ───────────────────────────────────────────────────────────────
    private JPanel buildContent() {
        cardLayout = new CardLayout();
        contentArea = new JPanel(cardLayout);
        contentArea.setBackground(Tema.BG_MAIN);

        panelDashboard    = new PanelPrincipal(ctx);
        panelEstudiantes  = new PanelEstudiantes(ctx);
        panelCursos       = new PanelCursos(ctx);
        panelCalificaciones = new PanelCalificaciones(ctx);
        panelCola         = new PanelCola(ctx);

        contentArea.add(panelDashboard,     "panel");
        contentArea.add(panelEstudiantes,   "estudiantes");
        contentArea.add(panelCursos,        "cursos");
        contentArea.add(panelCalificaciones,"calificaciones");
        contentArea.add(panelCola,          "cola");

        // Cuando se procesa la cola, refrescar calificaciones y dashboard
        panelCola.setOnProcessed(() -> {
            panelCalificaciones.refresh();
            panelDashboard.refresh();
        });

        return contentArea;
    }

    private void selectNav(Componentes.NavItem item, String title) {
        if (activeNav != null) activeNav.setSelected(false);
        activeNav = item;
        item.setSelected(true);
        lblTopTitle.setText(title);

        if (item == navPanel) {
            cardLayout.show(contentArea, "panel");
            panelDashboard.refresh();
        } else if (item == navEst) {
            cardLayout.show(contentArea, "estudiantes");
            panelEstudiantes.refresh();
        } else if (item == navCur) {
            cardLayout.show(contentArea, "cursos");
            panelCursos.refresh();
        } else if (item == navCal) {
            cardLayout.show(contentArea, "calificaciones");
            panelCalificaciones.refresh();
        } else if (item == navCola) {
            cardLayout.show(contentArea, "cola");
            panelCola.refresh();
        }
    }

    private void buscarGlobal() {
        String q = txtSearchTop.getText().trim();
        if (q.isEmpty() || q.equals("Buscar estudiante...")) return;
        Estudiante e = ctx.getBst().buscar(q);
        if (e != null) {
            JOptionPane.showMessageDialog(this,
                "<html><b>" + e.getNomCompleto() + "</b><br>" +
                "Matrícula: " + e.getMatricula() + "<br>" +
                "Correo: " + e.getCorreo() + "<br>" +
                "Promedio: " + (e.calcularPromedio() > 0 ? String.format("%.1f", e.calcularPromedio()) : "Sin califs.") + "</html>",
                "Estudiante encontrado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "No se encontró estudiante con matrícula: " + q,
                "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
